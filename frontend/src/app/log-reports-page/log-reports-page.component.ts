import { Component, OnInit } from '@angular/core';
import { LogsService } from '../services/logs.service';
import { MatSnackBar } from '@angular/material';
import { Label } from 'ng2-charts';

@Component({
  selector: 'app-log-reports-page',
  templateUrl: './log-reports-page.component.html',
  styleUrls: ['./log-reports-page.component.scss']
})
export class LogReportsPageComponent implements OnInit {

  report: any;

  severityLevelLabels: Label[];
  eventIdLabels: Label[];
  severityLevelData: number[];
  eventIdData: number[];
  severityLevelLabel = 'Severity level';
  eventIdLabel = 'Event id';

  constructor(private logsService: LogsService,
              private snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  getReports(params: string) {

    this.logsService.getReports(params).subscribe(
      (response => {

        this.report = response;
        this.severityLevelLabels = response.reportSeverityLevel.map(a => a.label);
        this.severityLevelData = response.reportSeverityLevel.map(a => a.numberOfLogs);

        this.eventIdLabels = response.reportEventId.map(a => a.label);
        this.eventIdData = response.reportEventId.map(a => a.numberOfLogs);

        console.log(response);
      }), (error => {
        console.log(error);
        this.snackBar.open('Error while getting report data');
      })
    );
  }

  sendReportParams(params: string) {
    this.getReports(params);
  }
}
