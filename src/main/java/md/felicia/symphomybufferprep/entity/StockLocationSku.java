package md.felicia.symphomybufferprep.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Symphony_StockLocationSkus", catalog = "SymRun_Felicia", schema = "dbo")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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

    private Date lastConsumptionDate;

    private Date updateDate;

    private int replenishmentTime;

    private float unitPrice;

    private int minimumBufferSize;

    private int saftyStock;

    private int minimumReplenishment;

    private int multiplications;

    private String custom_txt2;

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
