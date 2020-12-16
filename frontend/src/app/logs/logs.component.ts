import { Component, OnInit, OnDestroy } from '@angular/core';
import { LogsService } from '../services/logs.service';
import { MatSnackBar } from '@angular/material';
import { Log } from '../model/log.model';
import { Observable } from 'rxjs';
import { interval } from 'rxjs';


@Component({
  selector: 'app-logs',
  templateUrl: './logs.component.html',
  styleUrls: ['./logs.component.scss']
})
export class LogsComponent implements OnInit, OnDestroy {

  logs: Log[] = [];
  periodicCheck: Observable<number> = interval(2000);
  subscription: any;
  realTime: boolean;

  constructor(private logsService: LogsService,
              private snackBar: MatSnackBar) { }

  ngOnInit() {

    this.realTime = true;
    this.getLogs('');
    this.periodicCheckSubscribe();
  }

  ngOnDestroy() {
    this.periodicCheckUnsubscribe();
  }

  periodicCheckSubscribe() {
    this.subscription = this.periodicCheck.subscribe(x =>  this.getLogs(''));
  }

  periodicCheckUnsubscribe() {
    this.subscription.unsubscribe();
  }

  startRealTime() {
    this.realTime = true;
    this.periodicCheckSubscribe();
  }

  stopRealTime() {
    this.realTime = false;
    this.periodicCheckUnsubscribe();

    this.getLogs('');
  }

  getLogs(params: string) {
    if (params !== '' && this.realTime) {
      this.realTime = false;
      this.periodicCheckUnsubscribe();
    }
    this.logsService.getLogs(params).subscribe(
      (response => {
       // response.sort((a: any, b: any) => a.datetime.rendered.localeCompare(b.datetime.rendered));
        this.logs = response;
        console.log(response);
      }), (error => {
        console.log(error);
        this.snackBar.open('Error while getting logs data');
      })
    );
  }

  sendSearchFormParams(params: string) {
    this.getLogs(params);
  }

}
