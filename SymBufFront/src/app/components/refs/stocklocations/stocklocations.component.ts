import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {SymphonyDataService} from "../../../services/symphony-data.service";

export interface StockLocation{
  stockLocationID: number;
  stockLocationName: string;
  stockLocationDescription: string;
  deleted: boolean;
}

@Component({
  selector: 'app-stocklocations',
  templateUrl: './stocklocations.component.html',
  styleUrls: ['./stocklocations.component.css']
})
export class StocklocationsComponent implements OnInit{
  displayedColumns: string[] = ['stockLocationID','stockLocationName','stockLocationDescription','deleted'];

  dataSource: MatTableDataSource<StockLocation> = new MatTableDataSource<StockLocation>();

  hideDeleted: boolean = false;

  constructor(private symphonyService: SymphonyDataService) {
  }
  ngOnInit(): void {

    this.initData();

  }

  private initData() {
    this.symphonyService.getStockLocations().subscribe(data=>{
      this.dataSource.data = data;
      if(this.hideDeleted) {
        console.log('data', data)
        this.dataSource.data = data.filter((el: StockLocation) => {return !el.deleted})
      }
    })
  }

  applyFilter($event: Event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  doHideDeleted() {
    console.log('before', this.hideDeleted)
    this.hideDeleted = !this.hideDeleted;
    console.log('after', this.hideDeleted)
    this.initData();

  }
}
