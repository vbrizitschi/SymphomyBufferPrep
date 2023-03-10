package md.felicia.symphomybufferprep.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "calculation_logs", catalog ="Farma",schema = "dbo")
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
