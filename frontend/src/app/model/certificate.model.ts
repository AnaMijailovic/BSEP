import { IssuerSubjectData } from './issuer-subject-data.model';
import { Validity } from './validity.model';
import { KeyUsage} from './key-usage.model';

export interface Certificate {

    type: string;
    issuer: IssuerSubjectData;
    subject: IssuerSubjectData;
    issuerSerialNumber: string;
    validity: Validity;
    keyUsageExtention: KeyUsage;
}
