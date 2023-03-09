package md.felicia.symphomybufferprep.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class StockLocationDTO {
    private String stockLocationName;
    private String stockLocationDescription;
    private Boolean deleted;

}
