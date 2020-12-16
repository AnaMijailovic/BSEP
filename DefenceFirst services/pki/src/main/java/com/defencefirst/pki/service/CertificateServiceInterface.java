package com.defencefirst.pki.service;

import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.cert.CertIOException;

import com.defencefirst.pki.dto.CertificateDTO;
import com.defencefirst.pki.exceptions.CertificateAlreadyExistsException;
import com.defencefirst.pki.exceptions.CertificateNotFoundException;
import com.defencefirst.pki.exceptions.InvalidCAException;
import com.defencefirst.pki.exceptions.MissingExtensionValue;
import com.defencefirst.pki.model.Certificate;

public interface CertificateServiceInterface {

	public Certificate getOne(String serialNumber) throws CertificateNotFoundException;

	public List<Certificate> getAll() throws CertificateNotFoundException;

	public X509Certificate generateCertificate(CertificateDTO certificateDto) throws CertificateNotFoundException,
			InvalidCAException, MissingExtensionValue, CertIOException, CertificateAlreadyExistsException;

	public KeyPair generateKeyPair();

	public List<Certificate> getCertificateChain(String endCerSerialNumber) throws CertificateNotFoundException;

	public boolean validateCACertificate(Certificate certificate) throws CertificateNotFoundException;

	public boolean validateCertificateChain(List<Certificate> chain) throws CertificateNotFoundException;

	public boolean validateCertificate(Certificate certificate, Certificate parentCertificate) throws CertificateNotFoundException;

	public List<Certificate> getAllRevoked() throws CertificateNotFoundException;

	public void revokeCertificate(String serialNumber) throws CertificateNotFoundException;

	public void revokeCertificates() throws CertificateNotFoundException;

	public boolean isRevoked(String serialNumber) throws Exception;

	void revokeCertificateNew(String serialNumber) throws CertificateNotFoundException;

}
