import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchLogsFormComponent } from './search-logs-form.component';

describe('SearchLogsFormComponent', () => {
  let component: SearchLogsFormComponent;
  let fixture: ComponentFixture<SearchLogsFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchLogsFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchLogsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
