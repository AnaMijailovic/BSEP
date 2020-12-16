import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarAdministratorComponent } from './navbar-administrator.component';

describe('NavbarAdministratorComponent', () => {
  let component: NavbarAdministratorComponent;
  let fixture: ComponentFixture<NavbarAdministratorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NavbarAdministratorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarAdministratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
