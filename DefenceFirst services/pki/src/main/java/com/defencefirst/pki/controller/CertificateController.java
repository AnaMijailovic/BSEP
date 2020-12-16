package com.defencefirst.pki.controller;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.validation.Valid;

import org.bouncycastle.cert.CertIOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.defencefirst.pki.converters.CertificateConverter;
import com.defencefirst.pki.dto.CertificateDTO;
import com.defencefirst.pki.dto.IssuerBasicInfoDTO;
import com.defencefirst.pki.dto.RecommendedVadilityDTO;
import com.defencefirst.pki.dto.TreeNodeDTO;
import com.defencefirst.pki.exceptions.CertificateAlreadyExistsException;
import com.defencefirst.pki.exceptions.CertificateNotFoundException;
import com.defencefirst.pki.exceptions.InvalidCAException;
import com.defencefirst.pki.exceptions.MissingExtensionValue;
import com.defencefirst.pki.model.Certificate;
import com.defencefirst.pki.service.CertificateService;

@RestController
@CrossOrigin
@RequestMapping(value = "/pki/certificates", produces = MediaType.APPLICATION_JSON_VALUE)
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	// Find all certificates
	@GetMapping()
	@PreAuthorize("hasAuthority('PERMISSION_FIND_ALL_CERTIFICATES')")
	public ResponseEntity<List<Certificate>> getAll() throws CertificateNotFoundException {

		return new ResponseEntity<>(certificateService.getAll(), HttpStatus.OK);
	}

	// Get recommended validity periods
	@GetMapping(value = "/validity")
	@PreAuthorize("hasAuthority('PERMISSION_GET_RECOMMENDED_VALIDITY_PERIODS')")
	public ResponseEntity<RecommendedVadilityDTO> getRecommendedValidty() {

		return new ResponseEntity<>(certificateService.getRecommendedValidity(), HttpStatus.OK);
	}

	// Find certificate by serial number
	// Serial number is unique in the whole system, not just for one CA
	@GetMapping(value = "/{serialNumber}")
	@PreAuthorize("hasAuthority('PERMISSION_FIND_CERTIFICATE_BY_SERIAL_NUMBER')")
	public ResponseEntity<CertificateDTO> getOne(@PathVariable @Valid String serialNumber)
			throws CertificateNotFoundException, CertificateEncodingException {

		java.security.cert.Certificate cert = certificateService.getOneFromKeyStore(serialNumber);
		CertificateDTO dto = CertificateConverter.javaCertificateToDto(cert);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	// All CAs which are not revoked
	// IssuerBasicInfoDTO -> certificate serialNumber and issuer common name
	@GetMapping(value = "/issuers/valid")
	@PreAuthorize("hasAuthority('PERMISSION_GET_ALL_CAS_WHICH_ARE_NOT_REVOKED')")
	public ResponseEntity<List<IssuerBasicInfoDTO>> getValidIssuers() throws CertificateNotFoundException {

		return new ResponseEntity<>(certificateService.getValidIssuers(), HttpStatus.OK);
	}

	// Get all certificates as tree data
	@GetMapping(value = "/tree")
	@PreAuthorize("hasAuthority('PERMISSION_GET_ALL_CERTIFICATES_AS_TREE_DATA')")
	public ResponseEntity<TreeNodeDTO[]> getAllAsTree() throws CertificateNotFoundException {

		TreeNodeDTO[] dto = { certificateService.getTreeData() };

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	// Create new certificate -> root, intermediate or for end user
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('PERMISSION_CREATE_NEW_CERTIFICATE')")
	public ResponseEntity<String> createCertificate(@RequestBody @Valid CertificateDTO certificateDto)
			throws CertificateNotFoundException, InvalidCAException, CertIOException, MissingExtensionValue,
			CertificateAlreadyExistsException {

		System.out.println("\nCertificate DTO:\n" + certificateDto);
		X509Certificate cert = certificateService.generateCertificate(certificateDto);
		System.out.println("\nCertificate:\n" + cert);
		return new ResponseEntity<>("Certificate Created :)", HttpStatus.OK);
	}
	// Revoke certificate
	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, value = "/revoke/{serialNumber}")
	@PreAuthorize("hasAuthority('PERMISSION_REVOKE_CERTIFICATE')")
	public ResponseEntity<String> revokeCertificate(@PathVariable @Valid String serialNumber)
			throws CertificateNotFoundException, InvalidCAException, CertificateEncodingException {
		certificateService.revokeCertificate(serialNumber);
		return new ResponseEntity<>("Revoked!", HttpStatus.OK);
	}

	// Check if certificate with the given serialNumber is revoked
	@GetMapping(value = "/{serialNumber}/isRevoked")
	public ResponseEntity<Boolean> revokeCheck(@PathVariable @Valid String serialNumber)
			throws CertificateNotFoundException {

		return new ResponseEntity<>(!certificateService.revokeCheck(serialNumber), HttpStatus.OK);
	}

}
