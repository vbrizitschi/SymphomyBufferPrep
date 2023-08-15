import {Component, NgZone, OnChanges, OnInit, SimpleChange, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CalculateBuffer} from "../../../interfaces/calculate-buffer";
import {FileService} from "../../../services/file.service";
import {StockLocation} from "../../../interfaces/stockLocation";
import {Ctxt2} from "../../../interfaces/ctxt2";
import {ThemePalette} from "@angular/material/core";
import {BehaviorSubject, Observable} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {Dialog} from "@angular/cdk/dialog";
import {CalcBufferDialogComponent} from "../calc-buffer-dialog/calc-buffer-dialog.component";
@Component({
  selector: 'app-calc-buffer',
  templateUrl: './calc-buffer.component.html',
  styleUrls: ['./calc-buffer.component.css']
})
export class CalcBufferComponent implements OnInit{

  event: Observable<any>;
  private _events: BehaviorSubject<any> = new BehaviorSubject<any>(null);

  stockLocationsList : StockLocation[] | undefined;
  ctxt2sList : Ctxt2[]| undefined;
  color: ThemePalette = 'accent';
  checked = false;
  disabled = false;
  numberRegEx = /^-?(0|[1-9]\d*)?$/;
  decimalRegEx = /^[0-9]{0,4}(\.[0-9]{0,2})?$/;
  allowOnlyInteger = 'Разрешены только целые цифры!'
  allowOnlyReal = 'Разрещены только целые и дробные цифры!'

  status: string[] = [];

  isLoading: boolean = false;

  constructor(private fileService: FileService, private _zone: NgZone, public dialog: MatDialog){
    this.event = this.createEventObservable();
  }

  calcBufferForm : FormGroup = new FormGroup({
    addDaysFromToday: new FormControl(0, [Validators.pattern(this.numberRegEx), Validators.required]),
    automatic: new FormControl(0, [Validators.pattern(this.numberRegEx), Validators.required]),
    bufferMulti: new FormControl(1, [Validators.pattern(this.decimalRegEx), Validators.required]),
    decrease : new FormControl(1, [Validators.pattern(this.numberRegEx), Validators.required]),
    increase : new FormControl(1, [Validators.pattern(this.numberRegEx), Validators.required]),
    isInitialCalculation: new FormControl('', Validators.pattern(this.numberRegEx)),
    lessBuffer: new FormControl(0.8, [Validators.pattern(this.decimalRegEx), Validators.required]),
    maxBuffer: new FormControl(10000, [Validators.pattern(this.numberRegEx), Validators.required]),
    minBuffer: new FormControl(1, [Validators.pattern(this.numberRegEx), Validators.required]),
    moreBuffer: new FormControl(1.4, [Validators.pattern(this.decimalRegEx), Validators.required]),
    onlyOverstock: new FormControl(0, [Validators.pattern(this.numberRegEx), Validators.required]),
    periodForAverage: new FormControl(30, [Validators.pattern(this.numberRegEx), Validators.required]),
    periodForRec: new FormControl(30, [Validators.pattern(this.numberRegEx), Validators.required]),
    RT : new FormControl(0, [Validators.pattern(this.numberRegEx), Validators.required]),
    setAnalogsChildsBufferZero: new FormControl('', Validators.pattern(this.numberRegEx)),
    setAnalogsGroupBufferZero: new FormControl('', Validators.pattern(this.numberRegEx)),
    spikeFactor: new FormControl(2.3, [Validators.pattern(this.decimalRegEx), Validators.required]),
    useAvailability: new FormControl('', Validators.pattern(this.numberRegEx))
  })
  stockLocations = new FormControl('');
  ctxt2s = new FormControl('');
  useAvailability = new FormControl(false);
  isInitialCalculation = new FormControl(false);
  setAnalogsChildsBufferZero = new FormControl(false);
  setAnalogsGroupBufferZero = new FormControl(false);


  private createEventObservable(): Observable<any> {
    return this._events.asObservable();
  }


  loadSL(){
      this.fileService.getAllSL().subscribe(data =>{
        this.stockLocationsList = data;
      })
  }

  loadCtxt2(){
    this.fileService.getAllCtxt().subscribe(data =>{
      this.ctxt2sList = data;
    })
  }


  ngOnInit(): void {
    this.loadSL()
    this.loadCtxt2();
  }


  paramCalcBuffer : CalculateBuffer |any;
  initParam(){
    this.paramCalcBuffer = {
      addDaysFromToday: this.calcBufferForm.get('addDaysFromToday')?.value,
      automatic: this.calcBufferForm.get('automatic')?.value,
      bufferMulti: this.calcBufferForm.get('bufferMulti')?.value,
      ctxt2: this.ctxt2s.value?.length !== 0 ? this.ctxt2s.value : [{policy: 'ALL'}],
      decrease: this.calcBufferForm.get('decrease')?.value,
      increase: this.calcBufferForm.get('increase')?.value,
      isInitialCalculation: this.isInitialCalculation.value,
      lessBuffer: this.calcBufferForm.get('lessBuffer')?.value,
      maxBuffer: this.calcBufferForm.get('maxBuffer')?.value,
      minBuffer: this.calcBufferForm.get('minBuffer')?.value,
      moreBuffer: this.calcBufferForm.get('moreBuffer')?.value,
      onlyOverstock: this.calcBufferForm.get('onlyOverstock')?.value,
      periodForAverage: this.calcBufferForm.get('periodForAverage')?.value,
      periodForRec: this.calcBufferForm.get('periodForRec')?.value,
      rt: this.calcBufferForm.get('RT')?.value,
      setAnalogsChildsBufferZero: this.setAnalogsChildsBufferZero.value,
      setAnalogsGroupBufferZero: this.setAnalogsGroupBufferZero.value,
      spikeFactor: this.calcBufferForm.get('spikeFactor')?.value,
      stockLocations: this.stockLocations.value?.length !== 0 ? this.stockLocations.value : [{
        stockLocationName: 'ALL',
        stockLocationDescription: 'ALL'
      }],
      useAvailability: this.useAvailability.value
    }
    // this.isLoading = true;
    // this.status = []
  //  console.log(newCalcBuffer)
  //   this.fileService.calculateBuffer(newCalcBuffer).subscribe(data =>{
  //      console.log('data ', data);
  //       this.status.push(data)
  //
  //   }, error => {
  //     console.error('error',error);
  //     this.isLoading = false;
  //     }, ()=>{
  //     this.isLoading = false;
  //   })

   }


  doSLToString(formControl: FormControl): String{
    let newObj: [] | any;
    let strObj: []  | any = [];
    let retStr : String = 'ALL';
    newObj = formControl.value;
    if (newObj.length !== 0){
       newObj.forEach((element: any) => strObj.push(element.stockLocationDescription));
        retStr = strObj[0].toString();
    }
    return retStr;
  }

  doCtxt2ToString(formControl: FormControl): String{
    let newObj: [] | any;
    let strObj: []  | any = [];
    let retStr : String = 'ALL';
    newObj = formControl.value;
    if (newObj.length !== 0){
      newObj.forEach((element: any) => strObj.push(element.policy));
      retStr = strObj[0].toString();
    }
    return retStr;
  }

  doCalc() {
  this.initParam();

      this.dialog.open(CalcBufferDialogComponent, { data: {'calcParam': this.paramCalcBuffer}, disableClose:true, height: "800px", width: "700px"});

    // this.calculateBuffer()
  }

}




