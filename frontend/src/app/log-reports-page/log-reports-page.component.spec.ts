import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LogReportsPageComponent } from './log-reports-page.component';

describe('LogReportsPageComponent', () => {
  let component: LogReportsPageComponent;
  let fixture: ComponentFixture<LogReportsPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LogReportsPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LogReportsPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
