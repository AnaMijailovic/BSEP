package com.defencefirst.pki.service;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.defencefirst.pki.config.KeyStoreConfig;
import com.defencefirst.pki.config.RecommendedValidityConfig;
import com.defencefirst.pki.converters.CertificateConverter;
import com.defencefirst.pki.converters.x500NameConverter;
import com.defencefirst.pki.dto.CertificateDTO;
import com.defencefirst.pki.dto.IssuerBasicInfoDTO;
import com.defencefirst.pki.dto.RecommendedVadilityDTO;
import com.defencefirst.pki.dto.SubjectIssuerDTO;
import com.defencefirst.pki.dto.TreeNodeDTO;
import com.defencefirst.pki.exceptions.CertificateAlreadyExistsException;
import com.defencefirst.pki.exceptions.CertificateNotFoundException;
import com.defencefirst.pki.exceptions.InvalidCAException;
import com.defencefirst.pki.exceptions.MissingExtensionValue;
import com.defencefirst.pki.extensions.KeyUsageExtensionHolder;
import com.defencefirst.pki.keystore.KeyStoreReader;
import com.defencefirst.pki.keystore.KeyStoreWriter;
import com.defencefirst.pki.model.Certificate;
import com.defencefirst.pki.model.CertificateType;
import com.defencefirst.pki.model.Issuer;
import com.defencefirst.pki.model.RevokeReason;
import com.defencefirst.pki.repository.CertificateRepository;

@Service
public class CertificateService implements CertificateServiceInterface {

	private static final Logger LOGGER = LoggerFactory.getLogger(CertificateService.class);

	@Autowired
	private KeyStoreConfig keyStoreConfig;
	
	@Autowired
	private RecommendedValidityConfig recommendedValidityConfig;

	@Autowired
	private CertificateRepository certificateRepository;

	@Autowired
	private KeyStoreWriter keyStoreWriter;

	@Autowired
	private KeyStoreReader keyStoreReader;

	public RecommendedVadilityDTO getRecommendedValidity() {

		return new RecommendedVadilityDTO(recommendedValidityConfig.getRootValidityYrs(),
				recommendedValidityConfig.getIntermediateValidityYrs(),
				recommendedValidityConfig.getEndUserValidityYrs());
	}

	// Find certificate by serial number
	public Certificate getOne(String serialNumber) throws CertificateNotFoundException {

		Optional<Certificate> optCertificate = certificateRepository.findBySerialNumber(serialNumber);
		if (!optCertificate.isPresent()) {
			throw new CertificateNotFoundException("");
		}

		return optCertificate.get();
	}

	// Find certificate by serial number from KeyStore
	public java.security.cert.Certificate getOneFromKeyStore(String serialNumber) throws CertificateNotFoundException {

		return keyStoreReader.readCertificate(keyStoreConfig.getPath(), keyStoreConfig.getPassword(), serialNumber);
	}

	// Get all certificates
	// ROOT, INTERMEDIATE and END_USER
	// active and revoked
	public List<Certificate> getAll() throws CertificateNotFoundException {
		return certificateRepository.findAll();

	}

	// Get all active CAs
	public List<IssuerBasicInfoDTO> getValidIssuers() {

		return certificateRepository.getValidIssuersBasicInfo();
	}

	// TODO Break this method into smaller ones
	// Generate certificate -> ROOT, INTERMEDIATE and END_USER
	public X509Certificate generateCertificate(CertificateDTO certificateDto) throws CertificateNotFoundException,
			InvalidCAException, MissingExtensionValue, CertIOException, CertificateAlreadyExistsException {
		try {

			// generate private and public keys
			KeyPair keyPair = generateKeyPair();

			SubjectIssuerDTO subjectData = certificateDto.getSubject();
			X500Name issuerName;
			PrivateKey privateKey;
			String parentSerialNumber;

			String serialNumber = new Timestamp(System.currentTimeMillis()).getTime() + "";

			// check certificate type
			if (certificateDto.getType().equals("root")) {
				// check if root already exists
				if (activeRootExists()) {
					throw new CertificateAlreadyExistsException("Active root CA already exists!");
				}
				issuerName = x500NameConverter.generateX500Name(certificateDto.getIssuer());
				privateKey = keyPair.getPrivate();
				parentSerialNumber = serialNumber;
				// Subject is issuer for root
				subjectData = certificateDto.getIssuer();
			} else { // INTERMEDIATE OR END_USER
				Issuer issuer = keyStoreReader.readIssuerFromStore(keyStoreConfig.getPath(),
						certificateDto.getIssuerSerialNumber(), keyStoreConfig.getPassword().toCharArray(),
						keyStoreConfig.getPassword().toCharArray());

				// validate issuer certificate
				Certificate issuerCertificate = getOne(certificateDto.getIssuerSerialNumber());
				LOGGER.info("Issuer serialNumber: " + certificateDto.getIssuerSerialNumber());
				if (!validateCACertificate(issuerCertificate)) {
					throw new InvalidCAException("");
				}

				issuerName = issuer.getName();
				privateKey = issuer.getPrivateKey();
				parentSerialNumber = certificateDto.getIssuerSerialNumber();
			}

			JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			builder = builder.setProvider("BC");

			ContentSigner contentSigner = builder.build(privateKey);

			Certificate certificate = CertificateConverter.convertFromDTO(certificateDto, false, serialNumber,
					parentSerialNumber);

			// Save to mySql database
			certificateRepository.save(certificate);

			// Set certificate data
			X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerName, new BigInteger(serialNumber),
					certificateDto.getValidity().getNotBefore(), certificateDto.getValidity().getNotAfter(),
					x500NameConverter.generateX500Name(subjectData), keyPair.getPublic());

			// Set extensions
			KeyUsageExtensionHolder keyUsageHolder = new KeyUsageExtensionHolder(certificateDto.getKeyUsageExtention());

			certGen.addExtension(Extension.keyUsage, keyUsageHolder.isCritical(),
					new KeyUsage(keyUsageHolder.getKeyUsageExtensionBits()));

			// Generate certificate
			X509CertificateHolder certHolder = certGen.build(contentSigner);

			// Convert from X509CertificateHolder to X509Certificate
			JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
			certConverter = certConverter.setProvider("BC");

			X509Certificate x509Cert = certConverter.getCertificate(certHolder);

			// save to keystore
			keyStoreWriter.loadOrCreateKeyStore(keyStoreConfig.getPath(), keyStoreConfig.getPassword().toCharArray());
			keyStoreWriter.write(certificate.getSerialNumber(), keyPair.getPrivate(),
					keyStoreConfig.getPassword().toCharArray(), x509Cert);
			keyStoreWriter.saveKeyStore(keyStoreConfig.getPath(), keyStoreConfig.getPassword().toCharArray());
			
			// save to different keystore
			String path = certificate.getIssuerCommonName() +"_" + certificate.getSerialNumber() + ".p12";
			System.out.println("\n PATH: " + path);
			keyStoreWriter.loadOrCreateKeyStore(path, keyStoreConfig.getPassword().toCharArray());
			keyStoreWriter.write(certificate.getSerialNumber(), keyPair.getPrivate(),
					keyStoreConfig.getPassword().toCharArray(), x509Cert);
			keyStoreWriter.saveKeyStore(path, keyStoreConfig.getPassword().toCharArray());
			
			return x509Cert;
		} catch (IllegalArgumentException | IllegalStateException | OperatorCreationException
				| CertificateException e) {
			LOGGER.error("exception", e);
		}
		return null;
	}

	// Check if there is active root CA in the system
	public boolean activeRootExists() {

		List<Certificate> roots = certificateRepository.findByType(CertificateType.ROOT);
		for (Certificate c : roots) {
			if (!c.isRevoked()) {
				return true;
			}
		}

		return false;
	}

	// Generate private and public keys
	public KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(2048, random);
			return keyGen.generateKeyPair();
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			LOGGER.error("exception", e);
		}
		return null;
	}

	public List<Certificate> getCertificateChain(String endCerSerialNumber) throws CertificateNotFoundException {

		List<Certificate> chain = new ArrayList<>();

		String currentSerialNumber = "";
		String nextSerialNumber = endCerSerialNumber;

		// repeat until we reach the root certificate
		while (!currentSerialNumber.equals(nextSerialNumber)) {
			currentSerialNumber = nextSerialNumber;
			Certificate certificate = getOne(currentSerialNumber);
			chain.add(certificate);
			nextSerialNumber = certificate.getParentCertSerialNumber();
		}

		return chain;
	}

	public boolean validateCACertificate(Certificate certificate) throws CertificateNotFoundException {

		// ROOT and INTERMEDIATE certificates are CAs
		if (certificate.getType() == CertificateType.END_USER) {
			LOGGER.info("Not a CA!!!");
			return false;
		}

		// Validate certificate chain
		return validateCertificateChain(getCertificateChain(certificate.getSerialNumber()));
	}

	public boolean validateCertificateChain(List<Certificate> chain) throws CertificateNotFoundException {

		int chainSize = chain.size();

		// if chain size is 1 then only the root certificate is in the chain
		if (chainSize > 1) {
			for (int i = 0; i < chainSize - 2; i++) {
				if (!validateCertificate(chain.get(i), chain.get(i + 1))) {
					return false;
				}
			}
		}

		// validate root certificate
		return validateCertificate(chain.get(chainSize - 1), chain.get(chainSize - 1));

	}

	public boolean validateCertificate(Certificate certificate, Certificate parentCertificate)
			throws CertificateNotFoundException {
		LOGGER.info("Validating certificate with serial number: " + certificate.getSerialNumber());

		// get today's date for comparison
		Date now = new Date();

		// check if certificate has been revoked
		// check dates
		if (certificate.isRevoked() || certificate.getNotBefore().after(now) || certificate.getNotAfter().before(now)) {
			LOGGER.info("Certificate revoked or expired!!! ");
			return false;
		}

		// check if parent is CA
		if (parentCertificate.getType() == CertificateType.END_USER) {
			LOGGER.info("Type is end user!!!");
			return false;
		}

		// check digital signature

		// get the certificate to be verified from the KeyStore
		java.security.cert.Certificate certificateFromKS = keyStoreReader.readCertificate(keyStoreConfig.getPath(),
				keyStoreConfig.getPassword(), certificate.getSerialNumber());

		// get parent certificate from the KeyStore
		java.security.cert.Certificate parentFromKS = keyStoreReader.readCertificate(keyStoreConfig.getPath(),
				keyStoreConfig.getPassword(), parentCertificate.getSerialNumber());

		// verify
		try {
			certificateFromKS.verify(parentFromKS.getPublicKey());
			return true;
		} catch (InvalidKeyException | CertificateException | NoSuchAlgorithmException | NoSuchProviderException
				| SignatureException e) {
			LOGGER.info("Invalid signature!!!");
			return false;
		}

	}

	// For certificates tree view
	public TreeNodeDTO getTreeData() throws CertificateNotFoundException {

		TreeNodeDTO tree = new TreeNodeDTO();
		List<Certificate> root = certificateRepository.findByType(CertificateType.ROOT);
		root.removeIf(r -> (r.isRevoked()));
		if (root.isEmpty()) {
			tree.setCommonName("No certificates were found");
			return tree;
		}

		tree.setSerialNumber(root.get(0).getSerialNumber());
		tree.setCommonName(root.get(0).getIssuerCommonName());
		tree.setRevoked(root.get(0).isRevoked());
		tree.setType(CertificateType.ROOT);
		tree.setChildren(getChildren(root.get(0).getSerialNumber()));

		return tree;
	}

	public List<TreeNodeDTO> getChildren(String serialNumber) {

		List<TreeNodeDTO> children = new ArrayList<>();
		List<Certificate> certChildren = certificateRepository.findChildren(serialNumber);

		for (Certificate c : certChildren) {
			if (c.getType() == CertificateType.ROOT)
				continue;
			TreeNodeDTO treeNode = new TreeNodeDTO();

			treeNode.setSerialNumber(c.getSerialNumber());
			treeNode.setCommonName(c.getIssuerCommonName());
			treeNode.setRevoked(c.isRevoked());
			treeNode.setType(c.getType());
			if (treeNode.getType() == CertificateType.END_USER) {
				treeNode.setChildren(new ArrayList<>());
			} else {
				treeNode.setChildren(getChildren(c.getSerialNumber()));
			}

			children.add(treeNode);
		}

		return children;
		
	}
	
	@Override
	public List<Certificate> getAllRevoked() throws CertificateNotFoundException {
		return certificateRepository.findAllIsRevoke();
	}
	
	@Override
	public void revokeCertificate(String serialNumber) throws CertificateNotFoundException {
		
		Optional<Certificate> certificate = certificateRepository.findBySerialNumber(serialNumber);

		if(!certificate.isPresent()) {
			throw new CertificateNotFoundException("");
		}
				
		certificate.get().setRevoked(true);
        certificate.get().setRevocationDate(new Date());
        certificate.get().setRevokeReason(RevokeReason.UNSPECIFIED);
        certificateRepository.save(certificate.get());
        
        revokeChildren(serialNumber);
	
	}
	
	// recursive function to revoke whole subtree
	public void revokeChildren(String serialNumber) {
		
		List<Certificate> certificates = certificateRepository.findChildren(serialNumber);
		
		   for(Certificate c : certificates) {
	        	c.setRevoked(true);
	        	c.setRevocationDate(new Date());
	        	c.setRevokeReason(RevokeReason.UNSPECIFIED);
	        	certificateRepository.save(c);
	        	
	        	if(c.getType() != CertificateType.END_USER) {
	        		revokeChildren(c.getSerialNumber());
	        	}
	        }
	}
	
	
	// periodically check if there are expired certificates
	@Override
	@Scheduled(cron = "0 0 12 * * ?")
	public void revokeCertificates() throws CertificateNotFoundException{
		List<Certificate> certificates = certificateRepository.findAllIsNotRevoke();
		Date now = new Date();
		
		
		for(Certificate certificate : certificates) {
			if(certificate.getType() == CertificateType.END_USER) {
				if (certificate.getNotBefore().after(now) || certificate.getNotAfter().before(now)) {
					revokeCertificateNew(certificate.getSerialNumber());
				}
			}
		}
		
		for(Certificate certificate : certificates) {
			if (certificate.getType() != CertificateType.END_USER) {
				if(validateCACertificate(certificate)==false) {
					revokeCertificateNew(certificate.getSerialNumber());
				}
			}
		}
	}

	@Override
	public void revokeCertificateNew(String serialNumber) throws CertificateNotFoundException {
		
		Optional<Certificate> certificate = certificateRepository.findBySerialNumber(serialNumber);

		if(!certificate.isPresent()) {
			throw new CertificateNotFoundException("");
		}
				
		certificate.get().setRevoked(true);
        certificate.get().setRevocationDate(new Date());
        certificate.get().setRevokeReason(RevokeReason.CESSATION_OF_OPERATION);
        certificateRepository.save(certificate.get());
        
        revokeChildren(serialNumber);
	
	}
	@Override
    public boolean isRevoked(String serialNumber) throws Exception {
		return certificateRepository.findRevoked(serialNumber);
    }
	
	
	public boolean revokeCheck(String  serialNumber) throws CertificateNotFoundException {
		
		return validateCertificateChain(getCertificateChain(serialNumber));
		
	}

	
}
