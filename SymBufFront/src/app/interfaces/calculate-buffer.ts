import {StockLocation} from "./stockLocation";
import {Ctxt2} from "./ctxt2";

export interface CalculateBuffer {
  stockLocations: StockLocation[] | any,
   ctxt2  : Ctxt2 []| any,
   rt: number,
   minBuffer:number,
   maxBuffer:number,
   onlyOverstock: number,
   periodForAverage: number,
   periodForRec: number,
   spikeFactor : number
   increase : number,
   moreBuffer: number,
   decrease : number,
   lessBuffer : number,
   automatic: number,
   bufferMulti: number,
   useAvailability: boolean | any,
  isInitialCalculation: boolean | any,
  setAnalogsChildsBufferZero: boolean | any,
  setAnalogsGroupBufferZero : boolean | any,
  addDaysFromToday: number

}
