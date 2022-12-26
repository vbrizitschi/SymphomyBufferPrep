package md.felicia.symphomybufferprep.controller;

import lombok.Data;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;

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


    public BufferRow (XSSFRow row){
        DataFormatter dataFormatter  = new DataFormatter();

        this.name = dataFormatter.formatCellValue(row.getCell(0));
        this.stockLocation = dataFormatter.formatCellValue(row.getCell(1));
        this.skuName = dataFormatter.formatCellValue(row.getCell(2));

        this.assortmentGroup = dataFormatter.formatCellValue(row.getCell(3));
        this.eventSym  = dataFormatter.formatCellValue(row.getCell(4));

        this.startYear = row.getCell(5).getNumericCellValue();
        this.startMonth = row.getCell(6).getNumericCellValue();
        this.startDay  = row.getCell(7).getNumericCellValue();

        this.endYear = row.getCell(8).getNumericCellValue();
        this.endMonth = row.getCell(9).getNumericCellValue();
        this.endDay = row.getCell(10).getNumericCellValue();

        this.updateSteps = row.getCell(11).getNumericCellValue();
        this.resizeValue = row.getCell(12).getNumericCellValue();
        this.resizeMethod = row.getCell(13).getStringCellValue();
        this.autoAccept = row.getCell(14).getNumericCellValue();
        this.status  = dataFormatter.formatCellValue(row.getCell(15));

        this.reserved1 = dataFormatter.formatCellValue(row.getCell(16));
        this.reserved2 = dataFormatter.formatCellValue(row.getCell(17));
        this.reserved3 = dataFormatter.formatCellValue(row.getCell(18));
        this.reserved4 = dataFormatter.formatCellValue(row.getCell(19));
        this.reserved5 = dataFormatter.formatCellValue(row.getCell(20));
        this.reserved6 = dataFormatter.formatCellValue(row.getCell(21));
        this.reserved7 = dataFormatter.formatCellValue(row.getCell(22));
        this.reserved8 = dataFormatter.formatCellValue(row.getCell(23));
        this.reserved9 = dataFormatter.formatCellValue(row.getCell(24));
        this.reserved10 = dataFormatter.formatCellValue(row.getCell(25));

        this.reportedYear = row.getCell(26).getNumericCellValue();
        this.reportedMonth = row.getCell(27).getNumericCellValue();
        this.reportedDay = row.getCell(28).getNumericCellValue();
    }

    @Override
    public String toString() {
        String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

        return "Buffer_" + date +
                "|" + name +
                "|" + stockLocation +
                "|" + skuName +
                "|" + assortmentGroup +
                "|" + eventSym +
                "|" + startYear.intValue() +
                "|" + startMonth.intValue() +
                "|" + startDay.intValue() +
                "|" + endYear.intValue() +
                "|" + endMonth.intValue() +
                "|" + endDay.intValue() +
                "|" + updateSteps.intValue() +
                "|" + resizeValue.intValue() +
                "|" + resizeMethod  +
                "|" + autoAccept.intValue() +
                "|" + status  +
                "|" + reserved1 +
                "|" + reserved2 +
                "|" + reserved3 +
                "|" + reserved4 +
                "|" + reserved5 +
                "|" + reserved6 +
                "|" + reserved7 +
                "|" + reserved8 +
                "|" + reserved9 +
                "|" + reserved10 +
                "|" + reportedYear.intValue() +
                "|" + reportedMonth.intValue() +
                "|" + reportedDay.intValue() ;
    }
}
