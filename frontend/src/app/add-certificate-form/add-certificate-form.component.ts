import { Component, OnInit } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { validateDate, ValidateDate } from '../validators/date-validator';
import { IssuerBasicInfo } from '../model/issuer-basic-info.model';
import { Observable } from 'rxjs/internal/Observable';
import { map, startWith } from 'rxjs/operators';
import { CertificateService } from '../services/certificate.service';
import { IssuerSubjectData } from '../model/issuer-subject-data.model';
import { Certificate } from '../model/certificate.model';
import { Validity } from '../model/validity.model';
import { MatSnackBar } from '@angular/material';
import { KeyUsage } from '../model/key-usage.model';
import { RecommendedVadility } from '../model/recommended-vadility.model';

@Component({
  selector: 'app-add-certificate-form',
  templateUrl: './add-certificate-form.component.html',
  styleUrls: ['./add-certificate-form.component.scss']
})
export class AddCertificateFormComponent implements OnInit {

  generalInformationFormGroup: FormGroup;
  issuerFormGroup: FormGroup;
  subjectFormGroup: FormGroup;
  extentionsFormGroup: FormGroup;

  certificateTypes: string[] = ['root', 'intermediate', 'end user'];

  choosenIssuer: string;
  fullIssuerData: IssuerSubjectData = {} as IssuerSubjectData;
  issuerBasicInfo: IssuerBasicInfo[] = [];
  filteredIssuers: Observable<IssuerBasicInfo[]>;

  keyUsageExtention: KeyUsage;

  recommendedValidity: RecommendedVadility;

  constructor(private formBuilder: FormBuilder,
              private certificateService: CertificateService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {

    this.keyUsageExtention = new KeyUsage();
    this.createForm();
    this.createFilter();
    this.getIssuerBasicInfo();
    this.getRecommendedValidity();
  }

  createForm() {
    this.generalInformationFormGroup = this.formBuilder.group({
      certificateType: ['', Validators.required],
      notBefore: [new Date(), [Validators.required]],
      notAfter: ['', [Validators.required, validateDate(new Date(), ValidateDate.AFTER)]]
    });
    this.issuerFormGroup = this.formBuilder.group({
      issuer: [''],
      commonName: [this.fullIssuerData.commonName, Validators.compose([
        Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      givenName: [this.fullIssuerData.givenName, Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      surname: [this.fullIssuerData.surname, Validators.compose([
      //  Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      email: [this.fullIssuerData.email, Validators.compose([
       //   Validators.required,
          Validators.pattern('[a-zA-Z0-9._]+[@][a-zA-Z.]+')])],
      organization: [this.fullIssuerData.organization, Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      organizationalUnit: [this.fullIssuerData.organizationalUnit, Validators.compose([
      //  Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      stateOrProvinceName: [this.fullIssuerData.stateOrProvinceName, Validators.compose([
      //  Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      country: [this.fullIssuerData.country, Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      locality: [this.fullIssuerData.locality, Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])]
    });
    this.subjectFormGroup = this.formBuilder.group({
      commonName: ['', Validators.compose([
        Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      givenName: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      surname: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      email: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9._]+[@][a-zA-Z.]+')])],
      organization: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      organizationalUnit: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      stateOrProvinceName: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      country: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])],
      locality: ['', Validators.compose([
       // Validators.required,
        Validators.pattern('[a-zA-Z0-9.,!? ]*')])]
    });

    this.extentionsFormGroup = this.formBuilder.group({
      isCritical: [''],
      digitalSignature: [''],
      nonRepudiation: [''],
      keyEncipherment: [''],
      dataEncipherment: [''],
      keyAgreement: [''],
      keyCertSign: [''],
      crlSign: [''],
      encipherOnly: [''],
      decipherOnly: ['']
    });
  }

  // ******************* GET AND SET METHODS ****************************

  get certificateType() { return this.generalInformationFormGroup.controls.certificateType.value as string; }
  get notBefore() { return this.generalInformationFormGroup.controls.notBefore.value as Date; }
  get notAfter() { return this.generalInformationFormGroup.controls.notAfter.value as Date; }
  set setNotAfter(notAfter: Date) { this.generalInformationFormGroup.get('notAfter').setValue(notAfter); }

  get selectedIssuer() { return this.issuerFormGroup.controls.issuer.value as string; }

  get issuerCommonName() { return this.issuerFormGroup.controls.commonName.value as string; }
  get issuerGivenname() { return this.issuerFormGroup.controls.givenName.value as string; }
  get issuerSurname() { return this.issuerFormGroup.controls.surname.value as string; }
  get issuerEmail() { return this.issuerFormGroup.controls.email.value as string; }
  get issuerOrganization() { return this.issuerFormGroup.controls.organization.value as string; }
  get issuerOrganizationalUnit() { return this.issuerFormGroup.controls.organizationalUnit.value as string; }
  get issuerStateOrProvinceName() { return this.issuerFormGroup.controls.stateOrProvinceName.value as string; }
  get issuerCountry() { return this.issuerFormGroup.controls.country.value as string; }
  get issuerLocality() { return this.issuerFormGroup.controls.locality.value as string; }

  set setIssuerCommonName(commonName: string) { this.issuerFormGroup.get('commonName').setValue(commonName); }
  set setIssuerGivenName(givenname: string) { this.issuerFormGroup.get('givenName').setValue(givenname); }
  set setIssuerSurname(surname: string) { this.issuerFormGroup.get('surname').setValue(surname); }
  set setIssuerEmail(email: string) { this.issuerFormGroup.get('email').setValue(email); }
  set setIssuerOrganization(organization: string) { this.issuerFormGroup.get('organization').setValue(organization); }
  set setIssuerOrganizationalUnit(organizationUnit: string) {
    this.issuerFormGroup.get('organizationalUnit')
      .setValue(organizationUnit);
  }
  set setIssuerStateOrProvinceName(state: string) { this.issuerFormGroup.get('stateOrProvinceName').setValue(state); }
  set setIssuerCountry(country: string) { this.issuerFormGroup.get('country').setValue(country); }
  set setIssuerLocality(localty: string) { this.issuerFormGroup.get('locality').setValue(localty); }

  // ********************* ISSUERS ******************************

  createFilter() {

    this.filteredIssuers = this.issuerFormGroup.controls.issuer.valueChanges
      .pipe(
        startWith(''),
        map(iss => iss ? this._filterIssuers(iss) : this.issuerBasicInfo.slice())
      );
  }

  private _filterIssuers(value: string): IssuerBasicInfo[] {
    const filterValue = value.toLowerCase();
    return this.issuerBasicInfo.filter(iss => iss.commonName.toLowerCase().indexOf(filterValue) === 0);
  }

  getIssuerBasicInfo() {
    this.certificateService.getValidIssuers().subscribe(
      (response: IssuerBasicInfo[]) => {
        this.issuerBasicInfo = response;
        console.log('Response issuer basic info: ' + JSON.stringify(this.issuerBasicInfo));
      }
    );
  }

  getChosenIssuer() {
    const serialNumber = this.selectedIssuer.slice(this.selectedIssuer.indexOf('num: ') + 'num: '.length);
    console.log('Get choosen issuer ' + serialNumber);

    this.certificateService.getFullIssuerData(serialNumber).subscribe(
      (response => {
        console.log('Issuer data: ' + JSON.stringify(response));
        this.fullIssuerData = response.subject;

        this.setIssuerData();

      }));
  }

  getRecommendedValidity() {

    this.certificateService.getValidity().subscribe(
      (response => {
        this.recommendedValidity = response;
        console.log('Recommended Validity: ' + JSON.stringify(this.recommendedValidity));
        this.setIssuerData();

      }));
  }

  setIssuerData() {

    this.setIssuerCommonName = this.fullIssuerData.commonName;
    this.setIssuerGivenName = this.fullIssuerData.givenName;
    this.setIssuerSurname = this.fullIssuerData.surname;
    this.setIssuerEmail = this.fullIssuerData.email;
    this.setIssuerCountry = this.fullIssuerData.country;
    this.setIssuerStateOrProvinceName = this.fullIssuerData.stateOrProvinceName;
    this.setIssuerOrganization = this.fullIssuerData.organization;
    this.setIssuerOrganizationalUnit = this.fullIssuerData.organizationalUnit;
    this.setIssuerLocality = this.fullIssuerData.locality;
  }

  // ******************* FORM SUBMISSION *****************************

  submit() {

    // validate forms

    if (this.certificateType === 'root') {
      if (!this.issuerFormGroup.valid ||
        !this.generalInformationFormGroup.valid) {
         this.snackBar.open('Form is not valid');
         return;
      }
    } else {
      if (!this.subjectFormGroup.valid || !this.issuerFormGroup.valid ||
        !this.generalInformationFormGroup.valid) {
         this.snackBar.open('Form is not valid');
         return;
      }
    }


    // get form data
    const certificate = this.getFormData();
    console.log(JSON.stringify(certificate));

    // send request
    this.certificateService.addCertificate(certificate).subscribe(
      response => {
        console.log('no error');
        console.log(response);
        this.snackBar.open('Certificate created!');
      }, error => {
        console.log('error');
        console.log(error);
        if (error.status === 409) {
          this.snackBar.open('Certificate already exists!');
        } else {
          this.snackBar.open('Certificate created!');
        }
      });
  }

  getFormData() {

    const issuer: IssuerSubjectData = this.issuerFormGroup.value;
    const subject: IssuerSubjectData = this.subjectFormGroup.value;
    delete issuer['issuer'];
    const keyUsageExtention: KeyUsage = new KeyUsage(this.extentionsFormGroup.value);

    const validity: Validity = {
      notBefore: this.notBefore,
      notAfter: this.notAfter
    };

    const certificate: Certificate = {
      type: this.certificateType,
      issuerSerialNumber: this.selectedIssuer
        .slice(this.selectedIssuer.indexOf('num: ') + 'num: '.length),
      issuer,
      subject,
      validity,
      keyUsageExtention
    };

    return certificate;

  }

  subjectClick() {
    // alert('Subject click');
    const flag: boolean = this.certificateType === 'root';
    if ( flag ) {
      this.subjectFormGroup.get('commonName').setValue(this.issuerCommonName);
    }
  }

  onCertTypeChange() {

    const now: Date = new Date();
    if (this.certificateType === 'root') {
      now.setFullYear(now.getFullYear() + this.recommendedValidity.rootValidityYrs);
    } else if (this.certificateType === 'intermediate') {
      now.setFullYear(now.getFullYear() + this.recommendedValidity.intermediateValidityYrs);
    } else {
      now.setFullYear(now.getFullYear() + this.recommendedValidity.endUserValidityYrs);
    }

    this.setNotAfter = now;

  }

}
