export class KeyUsage {

    isCritical: boolean;
    digitalSignature: boolean;
    nonRepudiation: boolean;
    keyEncipherment: boolean;
    dataEncipherment: boolean;
    keyAgreement: boolean;
    keyCertSign: boolean;
    crlSign: boolean;
    encipherOnly: boolean;
    decipherOnly: boolean;

    constructor(obj?: any) {
        this.isCritical = obj && obj.isCritical || false;
        this.digitalSignature = obj && obj.digitalSignature || false;
        this.nonRepudiation = obj && obj.nonRepudiation || false;
        this.keyEncipherment = obj && obj.keyEncipherment || false;
        this.dataEncipherment = obj && obj.dataEncipherment || false;
        this.keyAgreement = obj && obj.keyAgreement || false;
        this.keyCertSign = obj && obj.keyCertSign || false;
        this.crlSign = obj && obj.crlSign || false;
        this.encipherOnly = obj && obj.encipherOnly || false;
        this.decipherOnly = obj && obj.decipherOnly || false;
    }
}
