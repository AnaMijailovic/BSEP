import { Injectable } from '@angular/core';

@Injectable()
export class ConstantsService {
    readonly certificatesPath = 'https://localhost:8765/pki/certificates';
    readonly authPath = 'https://localhost:8765/pki/auth';
    readonly logsPath = 'https://localhost:8765/siem/logs';
    readonly alarmsPath = 'https://localhost:8765/siem/alarms';


}
