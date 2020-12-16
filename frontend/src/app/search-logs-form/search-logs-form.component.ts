import { Component, OnInit, Output, EventEmitter, Input, OnChanges } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UtilService } from '../services/util.service';

@Component({
  selector: 'app-search-logs-form',
  templateUrl: './search-logs-form.component.html',
  styleUrls: ['./search-logs-form.component.scss']
})
export class SearchLogsFormComponent implements OnInit, OnChanges {

  @Output()
  sendSearchParams = new EventEmitter<string>();

  searchForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private utilService: UtilService) {
  }

  ngOnInit() {
    this.createForm();
  }

  ngOnChanges() {
    this.ngOnInit();
  }

  createForm() {
    this.searchForm = this.formBuilder.group({
      id: ['', []],
      mac: ['', []],
      eventId: ['', []],
      sourceName: ['', []],
      severityLevel: ['', []],
      message: ['', []],
      fromDate: ['', []],
      toDate: ['', []],
    }, { updateOn: 'submit'});

  }

  onSearchSubmit() {
    const params: string = this.utilService.generateParams(this.searchForm.value);
    // alert(JSON.stringify(params));
    this.sendSearchParams.emit(params);
  }

}
