import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AlarmsTableComponent } from './alarms-table.component';

describe('AlarmsTableComponent', () => {
  let component: AlarmsTableComponent;
  let fixture: ComponentFixture<AlarmsTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AlarmsTableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AlarmsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
