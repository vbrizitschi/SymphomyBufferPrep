package md.felicia.symphomybufferprep.DTO;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RunBufferDTO  {
    private String SL_Name ;
    private String slp1 ="ALL";
    private String slp2 ="ALL";
    private String slp3 ="ALL";
    private String slp4 ="ALL";
    private String slp5 ="ALL";
    private String slp6 ="ALL";
    private String slp7 ="ALL";
    private String skup1 ="ALL";
    private String skup2 ="ALL";
    private String skup3 ="ALL";
    private String skup4 ="ALL";
    private String skup5 ="ALL";
    private String skup6 ="ALL";
    private String skup7 ="ALL";
    private String ctxt1 ="ALL";
    private String ctxt2 ;
    private String ctxt3 ="ALL";
    private String ctxt4 ="ALL";
    private String ctxt5 ="ALL";
    private String ctxt6 ="ALL";
    private String ctxt7 ="ALL";
    private String ctxt8 ="ALL";
    private String ctxt9 ="ALL";
    private String ctxt10 ="ALL";
    private Integer cnum1 = -99999;
    private Integer cnum2 = -99999;
    private Integer cnum3 = -99999;
    private Integer cnum4 = -99999;
    private Integer cnum5 = -99999;
    private Integer cnum6 = -99999;
    private Integer cnum7 = -99999;
    private Integer cnum8 = -99999;
    private Integer cnum9 = -99999;
    private Integer cnum10 = -99999;
    private Integer cslnum1 = -99999;
    private Integer cslnum2 = -99999;
    private Integer cslnum3 = -99999;
    private Integer cslnum4 = -99999;
    private Integer cslnum5 = -99999;
    private Integer cslnum6 = -99999;
    private Integer cslnum7 = -99999;
    private Integer cslnum8 = -99999;
    private Integer cslnum9 = -99999;
    private Integer cslnum10 = -99999;
    private Integer rt ;
    private Integer buffMin ;
    private Integer buffMax ;
    private Integer overstock ;
    private Integer periodForAverage ;
    private Integer periodForRec ;
    private Double spikeFactor ;
    private Integer increase ;
    private Double moreBuffer ;
    private Integer decrease ;
    private Double lessBuffer ;
    private Integer automatic ;
    private Double bufferMulti ;
    private Integer useAvailability ;
    private Integer isInitialCalculation ;
    private Integer setAnalogsChildsBufferZero ;
    private Integer setAnalogsGroupBufferZero ;
    private Integer addDaysFromToday ;

    public RunBufferDTO(RunCalculateBufferDTO runCalculateBufferDTO){
        this.SL_Name  = runCalculateBufferDTO.getStockLocations()
                    .stream().map(StockLocationDTO::getStockLocationName)
                        .collect(Collectors.joining(","));
        this.ctxt2 = runCalculateBufferDTO.getCtxt2()
                .stream().map(PolicyDTO::getPolicy)
                        .collect(Collectors.joining(","));
        this.rt = runCalculateBufferDTO.getRt();
        this.buffMin = runCalculateBufferDTO.getMinBuffer();
        this.buffMax = runCalculateBufferDTO.getMaxBuffer();
        this.overstock = runCalculateBufferDTO.getOnlyOverstock();
        this.periodForAverage = runCalculateBufferDTO.getPeriodForAverage();
        this.periodForRec = runCalculateBufferDTO.getPeriodForRec();
        this.spikeFactor = runCalculateBufferDTO.getSpikeFactor();
        this.increase = runCalculateBufferDTO.getIncrease();
        this.moreBuffer = runCalculateBufferDTO.getMoreBuffer();
        this.decrease = runCalculateBufferDTO.getDecrease();
        this.lessBuffer = runCalculateBufferDTO.getLessBuffer();
        this.automatic = runCalculateBufferDTO.getAutomatic();
        this.bufferMulti = runCalculateBufferDTO.getBufferMulti();
        this.useAvailability = runCalculateBufferDTO.getUseAvailability() ? 1:0;
        this.isInitialCalculation = runCalculateBufferDTO.getIsInitialCalculation()? 1:0;
        this.setAnalogsChildsBufferZero = runCalculateBufferDTO.getSetAnalogsChildsBufferZero()? 1:0;
        this.setAnalogsGroupBufferZero = runCalculateBufferDTO.getSetAnalogsGroupBufferZero()? 1:0;
        this.addDaysFromToday = runCalculateBufferDTO.getAddDaysFromToday();


    }

}
