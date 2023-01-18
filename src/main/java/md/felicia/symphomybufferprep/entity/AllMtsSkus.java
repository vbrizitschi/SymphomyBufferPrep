package md.felicia.symphomybufferprep.entity;

import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Entity
@Getter
@Table(name="AllMtsSkus", catalog ="SymRun_Felicia", schema = "dbo")
public class AllMtsSkus {
    @NotNull
    @Id
    @Column(name = "uniqueId", nullable = false)
    private Long uniqueId;

    @Column(name = "slPropertyID1")
    private Integer slPropertyID1;

    @Column(name = "slPropertyID2")
    private Integer slPropertyID2;

    @Column(name = "slPropertyID3")
    private Integer slPropertyID3;

    @Column(name = "slPropertyID4")
    private Integer slPropertyID4;

    @Column(name = "slPropertyID5")
    private Integer slPropertyID5;

    @Column(name = "slPropertyID6")
    private Integer slPropertyID6;

    @Column(name = "slPropertyID7")
    private Integer slPropertyID7;

    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "stockLocationName", nullable = false, length = 100)
    private String stockLocationName;

    @Size(max = 1000)
    @Nationalized
    @Column(name = "stockLocationDescription", length = 1000)
    private String stockLocationDescription;

    @Size(max = 100)
    @NotNull
    @Nationalized
    @Column(name = "skuName", nullable = false, length = 100)
    private String skuName;

    @NotNull
    @Column(name = "stockLocationID", nullable = false)
    private Integer stockLocationID;

    @NotNull
    @Column(name = "skuID", nullable = false)
    private Integer skuID;

    @Size(max = 1000)
    @Nationalized
    @Column(name = "skuDescription", length = 1000)
    private String skuDescription;

    @Size(max = 100)
    @Nationalized
    @Column(name = "locationSkuName", length = 100)
    private String locationSkuName;

    @NotNull
    @Column(name = "bufferSize")
    private Integer bufferSize;

    @NotNull
    @Column(name = "inventoryAtSite", nullable = false, precision = 18, scale = 5)
    private Double inventoryAtSite;

    @NotNull
    @Column(name = "inventoryAtTransit", nullable = false, precision = 18, scale = 5)
    private Double inventoryAtTransit;

    @NotNull
    @Column(name = "inventoryAtProduction", nullable = false, precision = 18, scale = 5)
    private Double inventoryAtProduction;

    @NotNull
    @Column(name = "noConsumptionDays", nullable = false)
    private Short noConsumptionDays;

    @NotNull
    @Column(name = "inventoryNeeded", nullable = false, precision = 18, scale = 5)
    private Double inventoryNeeded;

    @Column(name = "updateDate")
    private Instant updateDate;

    @Column(name = "replenishmentTime")
    private Integer replenishmentTime;

    @NotNull
    @Column(name = "bpSite", nullable = false, precision = 18, scale = 5)
    private Double bpSite;

    @NotNull
    @Column(name = "bpTransit", nullable = false, precision = 18, scale = 5)
    private Double bpTransit;

    @NotNull
    @Column(name = "bpProduction", nullable = false, precision = 18, scale = 5)
    private Double bpProduction;

    @Column(name = "siteColor", columnDefinition = "tinyint not null")
    private Short siteColor;

    @Column(name = "transitColor", columnDefinition = "tinyint not null")
    private Short transitColor;

    @Column(name = "productionColor", columnDefinition = "tinyint not null")
    private Short productionColor;

    @NotNull
    @Column(name = "unitPrice", nullable = false, precision = 18, scale = 5)
    private Double unitPrice;

    @NotNull
    @Column(name = "avoidReplenishment", nullable = false)
    private Boolean avoidReplenishment = false;

    @Size(max = 100)
    @Nationalized
    @Column(name = "blackReason", length = 100)
    private String blackReason;

    @Size(max = 100)
    @Nationalized
    @Column(name = "redReason", length = 100)
    private String redReason;

    @Column(name = "skuPropertyID1")
    private Integer skuPropertyID1;

    @Column(name = "skuPropertyID2")
    private Integer skuPropertyID2;

    @Column(name = "skuPropertyID3")
    private Integer skuPropertyID3;

    @Column(name = "skuPropertyID4")
    private Integer skuPropertyID4;

    @Column(name = "skuPropertyID5")
    private Integer skuPropertyID5;

    @Column(name = "skuPropertyID6")
    private Integer skuPropertyID6;

    @Column(name = "skuPropertyID7")
    private Integer skuPropertyID7;

    @NotNull
    @Column(name = "minimumBufferSize")
    private Integer minimumBufferSize;

    @Column(name = "originStockLocation")
    private String originStockLocation;

    @Size(max = 100)
    @Nationalized
    @Column(name = "originStockLocationName", length = 100)
    private String originStockLocationName;

    @Size(max = 1000)
    @Nationalized
    @Column(name = "originStockLocationDescription", length = 1000)
    private String originStockLocationDescription;

    @Size(max = 100)
    @Nationalized
    @Column(name = "originSKU", length = 100)
    private String originSKU;

    @NotNull
    @Column(name = "saftyStock")
    private Integer saftyStock;

    @Column(name = "minimumRequiredBP", precision = 22, scale = 9)
    private Double minimumRequiredBP;

    @NotNull
    @Column(name = "minimumReplenishment")
    private Integer minimumReplenishment;

    @NotNull
    @Column(name = "multiplications")
    private Integer multiplications;

    @NotNull
    @Column(name = "avoidSeasonality", nullable = false)
    private Boolean avoidSeasonality = false;

    @NotNull
    @Column(name = "autoReplenishment", nullable = false)
    private Boolean autoReplenishment = false;

    @NotNull
    @Column(name = "uomID", nullable = false)
    private Integer uomID;

    @Column(name = "Throughput", precision = 18, scale = 5)
    private Double throughput;

    @Column(name = "TVC")
    private Integer tvc;

    @NotNull
    @Column(name = "isDeleted", nullable = false)
    private Boolean isDeleted = false;

    @Size(max = 4000)
    @NotNull
    @Nationalized
    @Column(name = "notes", nullable = false, length = 4000)
    private String notes;

    @NotNull
    @Column(name = "bufferManagementPolicy", nullable = false)
    private Short bufferManagementPolicy;

    @NotNull
    @Column(name = "inSeasonality", nullable = false)
    private Boolean inSeasonality = false;

    @NotNull
    @Column(name = "irrInvAtSite", nullable = false, precision = 18, scale = 5)
    private Double irrInvAtSite;

    @NotNull
    @Column(name = "irrInvAtTransit", nullable = false, precision = 18, scale = 5)
    private Double irrInvAtTransit;

    @NotNull
    @Column(name = "irrInvAtProduction", nullable = false, precision = 18, scale = 5)
    private Double irrInvAtProduction;

    @Column(name = "custom_num1", precision = 18, scale = 5)
    private Double customNum1;

    @Column(name = "custom_num2", precision = 18, scale = 5)
    private Double customNum2;

    @Column(name = "custom_num3", precision = 18, scale = 5)
    private Double customNum3;

    @Column(name = "custom_num4", precision = 18, scale = 5)
    private Double customNum4;

    @Column(name = "custom_num5", precision = 18, scale = 5)
    private Double customNum5;

    @Column(name = "custom_num6", precision = 18, scale = 5)
    private Double customNum6;

    @Column(name = "custom_num7", precision = 18, scale = 5)
    private Double customNum7;

    @Column(name = "custom_num8", precision = 18, scale = 5)
    private Double customNum8;

    @Column(name = "custom_num9", precision = 18, scale = 5)
    private Double customNum9;

    @Column(name = "custom_num10", precision = 18, scale = 5)
    private Double customNum10;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt1", length = 100)
    private String customTxt1;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt2", length = 100)
    private String customTxt2;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt3", length = 100)
    private String customTxt3;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt4", length = 100)
    private String customTxt4;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt5", length = 100)
    private String customTxt5;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt6", length = 100)
    private String customTxt6;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt7", length = 100)
    private String customTxt7;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt8", length = 100)
    private String customTxt8;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt9", length = 100)
    private String customTxt9;

    @Size(max = 100)
    @Nationalized
    @Column(name = "custom_txt10", length = 100)
    private String customTxt10;

    @NotNull
    @Column(name = "greenPipeDate", nullable = false)
    private Instant greenPipeDate;

    @Column(name = "shipmentMeasure", precision = 18, scale = 5)
    private Double shipmentMeasure;

    @Column(name = "endOfLifePolicy")
    private Short endOfLifePolicy;

    @NotNull
    @Column(name = "endOfLifeStatus", nullable = false)
    private Boolean endOfLifeStatus = false;

    @Column(name = "inventoryAllotment")
    private Integer inventoryAllotment;

    @Column(name = "clusterID")
    private Integer clusterID;

    @Column(name = "ISTState")
    private Integer iSTState;

    @Column(name = "coverage", precision = 18, scale = 5)
    private Double coverage;

    @Column(name = "saleRate", precision = 18, scale = 5)
    private Double saleRate;



}