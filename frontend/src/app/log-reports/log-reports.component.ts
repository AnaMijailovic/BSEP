import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UtilService } from '../services/util.service';

@Component({
  selector: 'app-log-reports',
  templateUrl: './log-reports.component.html',
  styleUrls: ['./log-reports.component.scss']
})
export class LogReportsComponent implements OnInit {

  @Output()
  sendReportParams = new EventEmitter<string>();
  searchForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private utilService: UtilService) { }

  ngOnInit() {
    this.createForm();
  }

  createForm() {
    this.searchForm = this.formBuilder.group({
      mac: ['', []],
      fromDate: ['', []],
      toDate: ['', []]
    });
  }

  onSearchSubmit() {
    const params: string = this.utilService.generateParams(this.searchForm.value);
    // alert('Params: ' + params);
    this.sendReportParams.emit(params);
  }

}
