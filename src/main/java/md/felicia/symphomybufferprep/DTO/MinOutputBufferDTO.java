package md.felicia.symphomybufferprep.DTO;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class MinOutputBufferDTO {
    private String stockLocationName;           //1
    private String skuName;                     //2
    private String skuDescription;              //3
    private Integer bufferSize;                  //4
    private Integer safetyStock;                 //5
    private String originStockLocation;         //6
    private Integer replenishmentTime;           //7
    private Double unitPrice;                   //8
    private Integer tvc;                         //9
    private Integer minReplenishment;            //11
    private Integer replenishmentMultip;         //12
    private Integer minimumBufferSize;           //48
    private Integer reportYear;                     //72
    private Integer reportMonth;                //73
    private Integer reportDay;                  //74;

    @Override
    public String toString(){
        LocalDateTime       localDateTime = LocalDateTime.now();

        String              currentYear = String.valueOf(localDateTime.getYear());
        String              currentMonth = String.valueOf(localDateTime.getMonthValue());
        String              currentDay  = String.valueOf(localDateTime.getDayOfMonth());

        return
                                stockLocationName +     //1
                        "|" +   skuName +               //2
                        "|" +   skuDescription +        //3
                        "|" +   bufferSize +            //4
                        "|" +   safetyStock +           //5
                        "|" +   originStockLocation +   //6
                        "|" +   replenishmentTime   +   //7
                        "|" +   unitPrice   +           //8
                        "|" +   tvc +                   //9
                        "|" +   "0" +                   //10
                        "|" +   minReplenishment +      //11
                        "|" +   replenishmentMultip +   //12
                        "|" +   "0" +                   //13
                        "|" +   "0" +                   //14
                        "|" +   "0" +                   //15
                        "|" +                           //16
                        "|" +   "Empty"        +        //17
                        "|" +                           //18
                        "|" +                           //19
                        "|" +                           //20
                        "|" +                           //21
                        "|" +                           //22
                        "|" + "0"   +                   //23
                        "|" + "0"   +                   //24
                        "|" + "0"   +                   //25
                        "|" + "3"   +                   //26
                        "|" + "4"   +                   //27
                        "|" +                           //28
                        "|" +                           //29
                        "|" +                           //30
                        "|" +                           //31
                        "|" +                           //32
                        "|" +                           //33
                        "|" +                           //34
                        "|" +                           //35
                        "|" +                           //36
                        "|" +                           //37
                        "|" +                           //38
                        "|" +                           //39
                        "|" +                           //40
                        "|" +                           //41
                        "|" +                           //42
                        "|" +                           //43
                        "|" +                           //44
                        "|" +                           //45
                        "|" +                           //46
                        "|" +                           //47
                        "|" +   minimumBufferSize   +   //48
                        "|" +                           //49
                        "|" +                           //50
                        "|" +                           //51
                        "|" +                           //52
                        "|" +                           //53
                        "|" +                           //54
                        "|" +                           //55
                        "|" +                           //56
                        "|" +                           //57
                        "|" +                           //58
                        "|" +   "Unit"  +               //59
                        "|" +                           //60
                        "|" +                           //61
                        "|" +                           //62
                        "|" +                           //63
                        "|" +                           //64
                        "|" +                           //65
                        "|" +                           //66
                        "|" +                           //67
                        "|" +                           //68
                        "|" +                           //69
                        "|" +                           //70
                        "|" +                           //71
                        "|" + currentYear +             //72
                        "|" + currentMonth +            //73
                        "|" + currentDay  ;

    }
}
