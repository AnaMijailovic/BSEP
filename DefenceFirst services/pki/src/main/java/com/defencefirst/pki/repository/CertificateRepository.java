package com.defencefirst.pki.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.defencefirst.pki.dto.IssuerBasicInfoDTO;
import com.defencefirst.pki.model.Certificate;
import com.defencefirst.pki.model.CertificateType;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, String> {
	
	Optional<Certificate> findBySerialNumber(String serialNumber);
	
	List<Certificate> findByType(CertificateType type);
	
	List<Certificate> findByParentCertSerialNumber(String serialNumber);
	
	// All CAs (type is ROOT or INTERMEDIATE), not revoked
	@Query("SELECT NEW com.defencefirst.pki.dto.IssuerBasicInfoDTO(cert.serialNumber, cert.issuerCommonName) "
			+ "FROM Certificate AS cert "
			+ "WHERE cert.type != com.defencefirst.pki.model.CertificateType.END_USER "
			+ "AND cert.revoked = false ")
	List<IssuerBasicInfoDTO> getValidIssuersBasicInfo();
	
	@Query("SELECT child  "
			+ "FROM Certificate AS child, Certificate AS parent "
			+ "WHERE child.parentCertSerialNumber = parent.serialNumber "
			+ "AND parent.serialNumber = :serialNumber")
	List<Certificate> findChildren(String serialNumber);
	
    @Query("SELECT certificate from Certificate certificate WHERE certificate.revoked = true")
	List<Certificate> findAllIsRevoke();
    
    @Query("SELECT certificate from Certificate certificate WHERE certificate.revoked = false")
	List<Certificate> findAllIsNotRevoke();

    @Query("SELECT certificate from Certificate certificate WHERE certificate.revoked = true AND certificate.serialNumber = serialNumber")
	boolean findRevoked(String serialNumber);
	
	

}
