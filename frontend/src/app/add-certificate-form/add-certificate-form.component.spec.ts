import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCertificateFormComponent } from './add-certificate-form.component';

describe('AddCertificateFormComponent', () => {
  let component: AddCertificateFormComponent;
  let fixture: ComponentFixture<AddCertificateFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCertificateFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCertificateFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
