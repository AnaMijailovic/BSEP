import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarOperatorComponent } from './navbar-operator.component';

describe('NavbarOperatorComponent', () => {
  let component: NavbarOperatorComponent;
  let fixture: ComponentFixture<NavbarOperatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarOperatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarOperatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
