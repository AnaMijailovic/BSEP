package com.defencefirst.pki.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyUsageExtensionDTO {
	
	private boolean isCritical;
    private boolean digitalSignature;
    private boolean nonRepudiation;
    private boolean keyEncipherment;
    private boolean dataEncipherment;
    private boolean keyAgreement;
    private boolean keyCertSign;
    private boolean crlSign;
    private boolean encipherOnly;
    private boolean decipherOnly;

}
