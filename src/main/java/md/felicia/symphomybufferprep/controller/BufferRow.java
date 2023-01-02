package md.felicia.symphomybufferprep.controller;

import lombok.Data;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
public class BufferRow {
    private String name ; //Name(SYM) 0
    private String stockLocation; //Stock Location Name(SYM) 1

    private String skuName;
    private String assortmentGroup; //Assortment group(SYM) 2
    private String eventSym; //Type(Event) (SYM) 3

    private Double startYear ; //Start Year(SYM) 4
    private Double startMonth; //Start Mont(SYM) 5
    private Double startDay; // Start Date(SYM) 6

    private Double endYear; // End Year (SYM) 7
    private Double endMonth; // End Month(SYM) 8
    private Double endDay; //End Day (SYM) 9

    private Double updateSteps; //Update Steps(SYM) 10
    private Double resizeValue; // Resize Value(SYM) 11
    private String resizeMethod; //Resize Method(SYM) 12
    private Double autoAccept; //Auto Accept(SYM) 13
    private String status; //Status (SYM) 14

    private String reserved1; //15
    private String reserved2; //16
    private String reserved3; //17
    private String reserved4; //18
    private String reserved5; //19
    private String reserved6; //20
    private String reserved7; //21
    private String reserved8; //22
    private String reserved9; //23
    private String reserved10; //24
    private Double reportedYear; //Reported year(SYM) //25
    private Double reportedMonth; //Reported month(SYM) //26
    private Double reportedDay; //Reported day(SYM) //27

    BufferRow(){};

    @Override
    public String toString() {
        String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        return "Buffer_" + date +
                "|" + this.trim(stockLocation) +
                "|" + this.trim(skuName) +
                "|" + this.trim(assortmentGroup) +
                "|" + this.trim(eventSym) +
                "|" + startYear.intValue() +
                "|" + startMonth.intValue() +
                "|" + startDay.intValue() +
                "|" + endYear.intValue() +
                "|" + endMonth.intValue() +
                "|" + endDay.intValue() +
                "|" + updateSteps.intValue() +
                "|" + resizeValue.intValue() +
                "|" + this.trim(resizeMethod)  +
                "|" + autoAccept.intValue() +
                "|" + this.trim(status)  +
                "|" + this.trim(reserved1) +
                "|" + this.trim(reserved2) +
                "|" + this.trim(reserved3) +
                "|" + this.trim(reserved4) +
                "|" + this.trim(reserved5) +
                "|" + this.trim(reserved6) +
                "|" + this.trim(reserved7) +
                "|" + this.trim(reserved8) +
                "|" + this.trim(reserved9) +
                "|" + this.trim(reserved10) +
                "|" + reportedYear.intValue() +
                "|" + reportedMonth.intValue() +
                "|" + reportedDay.intValue() ;
    }

    private String trim(final String str) {
        return str == null ? "" : str.trim();
    }
}
