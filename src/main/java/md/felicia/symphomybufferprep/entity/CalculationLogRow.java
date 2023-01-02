package md.felicia.symphomybufferprep.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "calculation_logs")
@Data
public class CalculationLogRow {
    @Id
    private Integer ID;

    @Column(name = "startdate")
    private Date startDate;

    @Column(name = "totalfiles")
    private int totalFiles;

    @Column(name = "totalrows")
    private int totalRows;

    @Column(name = "totaltime")
    private Date totalTime;

    @Column(name = "loadtime")
    private Date loadTime;

    @Column(name = "recalculatetime")
    private Date recalculateTime;

    @Column(name = "quarantinerowstotal")
    private int quarantineRowsTotal;

    @Column(name = "warningrowstotal")
    private int warningRowsTotal;

    @Column(name = "isloadsuccessful")
    private int isLoadSuccessful;
}
