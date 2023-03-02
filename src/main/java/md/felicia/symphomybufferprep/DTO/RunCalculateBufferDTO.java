package md.felicia.symphomybufferprep.DTO;

import lombok.Data;

import java.util.List;

@Data
public class RunCalculateBufferDTO {
    private List<StockLocationDTO> stockLocations;
    private List<PolicyDTO> ctxt2;
    private Integer rt;
    private Integer minBuffer;
    private Integer maxBuffer;
    private Integer onlyOverstock;
    private Integer periodForAverage;
    private Integer periodForRec;
    private Double spikeFactor;
    private Integer increase;
    private Double  moreBuffer;
    private Integer decrease;
    private Double lessBuffer;
    private Integer automatic;
    private Double bufferMulti;
    private Boolean useAvailability;
    private Boolean isInitialCalculation;
    private Boolean setAnalogsChildsBufferZero;
    private Boolean setAnalogsGroupBufferZero;
    private Integer addDaysFromToday;


}
