package com.defencefirst.pki.extensions;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bouncycastle.asn1.x509.KeyUsage;

import com.defencefirst.pki.dto.KeyUsageExtensionDTO;
import com.defencefirst.pki.exceptions.MissingExtensionValue;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KeyUsageExtensionHolder {

	private String name;
	private String aSN1ObjectIdentifier;
	private boolean isCritical;
	private boolean digitalSignature;
	private boolean nonRepudiation;
	private boolean keyEncipherment;
	private boolean dataEncipherment;
	private boolean keyAgreement;
	private boolean keyCertSign;
	private boolean cRLSign;
	private boolean encipherOnly;
	private boolean decipherOnly;

	private static final Map<String, Integer> KEY_USAGE_MAPPER = new HashMap<String, Integer>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = -3248964951117565451L;

		{
			put("digitalSignature", KeyUsage.digitalSignature);
			put("nonRepudiation", KeyUsage.nonRepudiation);
			put("keyEncipherment", KeyUsage.keyEncipherment);
			put("dataEncipherment", KeyUsage.dataEncipherment);
			put("keyAgreement", KeyUsage.keyAgreement);
			put("keyCertSign", KeyUsage.keyCertSign);
			put("cRLSign", KeyUsage.cRLSign);
			put("encipherOnly", KeyUsage.encipherOnly);
			put("decipherOnly", KeyUsage.decipherOnly);
		}
	};

	public KeyUsageExtensionHolder(KeyUsageExtensionDTO dto) {
		this.name = "KeyUsage";
		this.aSN1ObjectIdentifier = "2.5.29.15";
		this.isCritical = dto.isCritical();
		this.digitalSignature = dto.isDigitalSignature();
		this.nonRepudiation = dto.isNonRepudiation();
		this.keyEncipherment = dto.isKeyEncipherment();
		this.dataEncipherment = dto.isDataEncipherment();
		this.keyAgreement = dto.isKeyAgreement();
		this.keyCertSign = dto.isKeyCertSign();
		this.cRLSign = dto.isCrlSign();
		this.encipherOnly = dto.isEncipherOnly();
		this.decipherOnly = dto.isDecipherOnly();
	}

	public int getKeyUsageExtensionBits() throws MissingExtensionValue {
		List<String> choosenFields = Arrays.stream(this.getClass().getDeclaredFields()).filter(field -> {
			try {
				return field.getBoolean(this);
			} catch (IllegalAccessException | IllegalArgumentException e) {
				return false;
			}
		}).map(Field::getName).collect(Collectors.toList());

		if (choosenFields.isEmpty()) {
			throw new MissingExtensionValue("You must choose key usage!");
		} else if (choosenFields.size() == 1) {
			return KEY_USAGE_MAPPER.get(choosenFields.get(0));
		} else {
			int result = KEY_USAGE_MAPPER.get(choosenFields.get(0));
			for (int i = 1; i < choosenFields.size(); i++) {
				result |= KEY_USAGE_MAPPER.get(choosenFields.get(i));
			}
			return result;
		}
	}
}
