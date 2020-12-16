import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { ChartOptions, ChartType, ChartDataSets } from 'chart.js';
import * as pluginDataLabels from 'chartjs-plugin-datalabels';
import { Label } from 'ng2-charts';

@Component({
  selector: 'app-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss']
})
export class BarChartComponent implements OnInit, OnChanges {

  public barChartOptions: ChartOptions = {
    responsive: true,
  };

  @Input()
  public barChartLabels: Label[];

  @Input()
  barChartDataArray: number[];

  @Input()
  dataLabel: string;

  public barChartType: ChartType = 'bar';
  public barChartLegend = true;
  public barChartPlugins = [pluginDataLabels];

  public barChartData: ChartDataSets[] = [];

  constructor() { }

  ngOnInit() {
    this.barChartData = [   { data: this.barChartDataArray, label: this.dataLabel }];
  }

  ngOnChanges() {
    this.ngOnInit();
  }

}
