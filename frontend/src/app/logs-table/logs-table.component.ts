import { Component, OnInit, Input } from '@angular/core';
import { Log } from '../model/log.model';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-logs-table',
  templateUrl: './logs-table.component.html',
  styleUrls: ['./logs-table.component.scss']
})
export class LogsTableComponent implements OnInit {

  private _logs: Log[] = [];

  get logs(): Log[] {
    return this._logs;
  }

  @Input('logs')
  set value(logs: Log[]) {
    this._logs = logs;
    this.dataSource = new MatTableDataSource(this.logs);
  }

  dataSource: MatTableDataSource<Log>;
  displayedColumns = ['log_id', 'datetime', 'mac', 'source-name', 'severity-level', 'event-id', 'message'];

  constructor() { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource(this.logs);
  }

}
