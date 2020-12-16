import { Component, OnInit, Input } from '@angular/core';
import { Alarm } from '../model/alarm.model';
import { MatTableDataSource } from '@angular/material';

@Component({
  selector: 'app-alarms-table',
  templateUrl: './alarms-table.component.html',
  styleUrls: ['./alarms-table.component.scss']
})
export class AlarmsTableComponent implements OnInit {

  private _alarms: Alarm[] = [];

  get alarms(): Alarm[] {
    return this._alarms;
  }

  @Input('alarms')
  set value(alarms: Alarm[]) {
    this._alarms = alarms;
    this.dataSource = new MatTableDataSource(this.alarms);
  }

  dataSource: MatTableDataSource<Alarm>;
  displayedColumns = ['alarm_id', 'datetime', 'message'];

  constructor() { }

  ngOnInit() {
    this.dataSource = new MatTableDataSource(this.alarms);
  }

}
