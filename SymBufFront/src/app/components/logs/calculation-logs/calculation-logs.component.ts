import {Component, OnInit} from '@angular/core';
import {LogsService} from "../../../services/logs.service";
import {MatTableDataSource} from "@angular/material/table";

export interface CalculationLogRow {
  ID: number;
  startDate: Date;
  totalFiles: number;
  totalRows: number;
  totalTime: Date;
  loadTime: Date;
  recalculateTime: Date;
  quarantineRowsTotal: number;
  warningRowsTotal: number;
  isLoadSuccessful: boolean;

}
@Component({
  selector: 'app-calculation-logs',
  templateUrl: './calculation-logs.component.html',
  styleUrls: ['./calculation-logs.component.css']
})
export class CalculationLogsComponent implements OnInit{
    displayedColumns: string[] = ['id','startDate','totalFiles','totalRows', 'loadTime','recalculateTime', 'totalTime','quarantineRowsTotal','warningRowsTotal','isLoadSuccessful'];

    dataSource: MatTableDataSource<CalculationLogRow> = new MatTableDataSource<CalculationLogRow>();
    constructor(private logService: LogsService) {
    }

  ngOnInit(): void {
      this.logService.getCalculationLogs().subscribe(data => {
        this.dataSource = data;
      })
  }


}
