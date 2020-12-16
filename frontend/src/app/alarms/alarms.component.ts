import { Component, OnInit } from '@angular/core';
import { AlarmsService } from '../services/alarms.service';
import { MatSnackBar } from '@angular/material';
import { Alarm } from '../model/alarm.model';

@Component({
  selector: 'app-alarms',
  templateUrl: './alarms.component.html',
  styleUrls: ['./alarms.component.scss']
})
export class AlarmsComponent implements OnInit {

  alarms: Alarm[] = [];

  constructor(private alarmsService: AlarmsService,
              private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.getAlarms();
  }

  getAlarms() {
    this.alarmsService.getAlarms().subscribe(
      (response => {
        this.alarms = response;
        console.log(response);
      }), (error => {
        console.log(error);
        this.snackBar.open('Error while getting alarms data');
      })
    );
  }

}
