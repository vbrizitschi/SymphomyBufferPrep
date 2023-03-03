package md.felicia.symphomybufferprep.DTO;


import lombok.Data;

@Data
public class MinBufferDTO {
   private String StockLocation;
   private String SKUName;
   private Integer minBufferSize;
}
