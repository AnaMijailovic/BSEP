package com.defencefirst.pki.keystore;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.annotation.PostConstruct;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.defencefirst.pki.config.KeyStoreConfig;
import com.defencefirst.pki.exceptions.CertificateNotFoundException;
import com.defencefirst.pki.model.Issuer;

@Service
public class KeyStoreReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(KeyStoreReader.class);
	private static final String LOG_EXCEPTION_MSG = "exception";

	@Autowired
	private KeyStoreConfig keyStoreConfig;

	private KeyStore keyStore;

	@PostConstruct
	public void init() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			keyStore = KeyStore.getInstance(keyStoreConfig.getType(), BouncyCastleProvider.PROVIDER_NAME);
		} catch (KeyStoreException | NoSuchProviderException e) {
			LOGGER.error(LOG_EXCEPTION_MSG, e);
		}
	}

	/**
	 * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci
	 * privatni kljuc. Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
	 * 
	 * @param keyStoreFile - datoteka odakle se citaju podaci
	 * @param alias        - alias putem kog se identifikuje sertifikat izdavaoca
	 * @param password     - lozinka koja je neophodna da se otvori key store
	 * @param keyPass      - lozinka koja je neophodna da se izvuce privatni kljuc
	 * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
	 * @throws CertificateNotFoundException 
	 */
	public Issuer readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) throws CertificateNotFoundException {
		try {
			// Datoteka se ucitava
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			keyStore.load(in, password);
			// Iscitava se sertifikat koji ima dati alias
			Certificate cert;
			if (keyStore.isKeyEntry(alias)) {
				cert = keyStore.getCertificate(alias);
			}else {
				throw new CertificateNotFoundException("");
			}
			// Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu
			// sa datim aliasom
			PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);
			X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
			
			return new Issuer(issuerName, privKey);
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException
				| IOException e) {
			LOGGER.error(LOG_EXCEPTION_MSG, e);
		}
		return null;
	}

	/**
	 * Ucitava sertifikat is KS fajla
	 * @throws CertificateNotFoundException 
	 */
	public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) throws CertificateNotFoundException {
		try {
			// kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance(keyStoreConfig.getType(), BouncyCastleProvider.PROVIDER_NAME);
			// ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());

			if (ks.isKeyEntry(alias)) {
				return ks.getCertificate(alias);
			}else {
				throw new CertificateNotFoundException("");
			}
			
		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException
				| IOException e) {
			LOGGER.error(LOG_EXCEPTION_MSG, e);
		}
		return null;
	}

	/**
	 * Ucitava privatni kljuc is KS fajla
	 */
	public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
		try {
			// kreiramo instancu KeyStore
			KeyStore ks = KeyStore.getInstance(keyStoreConfig.getType(), BouncyCastleProvider.PROVIDER_NAME);
			// ucitavamo podatke
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
			ks.load(in, keyStorePass.toCharArray());

			if (ks.isKeyEntry(alias)) {
				PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
				return pk;
			}
		} catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException
				| IOException | UnrecoverableKeyException e) {
			LOGGER.error(LOG_EXCEPTION_MSG, e);
		}
		return null;
	}

}
