import {Component, OnInit} from '@angular/core';
import {MatTableDataSource} from "@angular/material/table";
import {SymphonyDataService} from "../../../services/symphony-data.service";
import {StockLocation} from "../../../interfaces/stockLocation";


@Component({
  selector: 'app-stocklocations',
  templateUrl: './stocklocations.component.html',
  styleUrls: ['./stocklocations.component.css']
})
export class StocklocationsComponent implements OnInit{
  displayedColumns: string[] = ['stockLocationName','stockLocationDescription','deleted'];

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
        this.dataSource.data = data.filter((el: StockLocation) => {return !el.deleted})
      }
    })
  }

  applyFilter($event: Event) {
    const filterValue = ($event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  doHideDeleted() {
    this.hideDeleted = !this.hideDeleted;
    this.initData();

  }
}
