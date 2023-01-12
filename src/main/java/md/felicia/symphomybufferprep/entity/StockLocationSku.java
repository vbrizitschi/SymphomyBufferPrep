package md.felicia.symphomybufferprep.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "Symphony_StockLocationSkus", catalog = "SymRun_Felicia", schema = "dbo")
@Data
public class StockLocationSku {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private int stockLocationID;

    private int skuID;

    private String locationSkuName;

    private float bufferSize;

    private float inventoryAtSite;

    private float inventoryAtTransit;

    private boolean isDeleted;


    /* SELECT TOP (1000) [id]
      ,[stockLocationID]
      ,[skuID]
      ,[skuDescription]
      ,[locationSkuName]
      ,[bufferSize]
      ,[inventoryAtSite]
      ,[inventoryAtTransit]
      ,[lastConsumptionDate]
      ,[updateDate]
      ,[replenishmentTime]
      ,[unitPrice]
      ,[minimumBufferSize]
      ,[saftyStock]
      ,[minimumReplenishment]
      ,[multiplications]
      ,[isDeleted]
      ,[custom_txt2]
    FROM [SymRun_Felicia].[dbo].[Symphony_StockLocationSkus]
  where isDeleted=0 and inventoryAtSite>0*/
}
