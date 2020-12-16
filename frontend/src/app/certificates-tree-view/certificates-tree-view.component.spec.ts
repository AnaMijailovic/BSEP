import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CertificatesTreeViewComponent } from './certificates-tree-view.component';

describe('CertificatesTreeViewComponent', () => {
  let component: CertificatesTreeViewComponent;
  let fixture: ComponentFixture<CertificatesTreeViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CertificatesTreeViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CertificatesTreeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
