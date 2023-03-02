package md.felicia.symphomybufferprep.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "bufferstemp", catalog = "SymRun_Felicia", schema = "dbo")
public class Bufferstemp {
    @Id
    private String name ;
    private String sl ;
    private String sku ;
    private String ag ;
    private String type1 ;
    private Integer year1 ;
    private Integer month1 ;
    private Integer day1 ;
    private Integer year2 ;
    private Integer month2 ;
    private Integer day2 ;
    private Integer updatesteps ;
    private Integer buffer ;
    private String method ;
    private Integer autom ;
    private String status1 ;
    private String res1 ;
    private String res2 ;
    private String res3 ;
    private String res4 ;
    private String res5 ;
    private String res6 ;
    private String res7 ;
    private String res8 ;
    private String res9 ;
    private String res10 ;
    private Integer year3 ;
    private Integer month3 ;
    private Integer day3 ;

    @Override
    public String toString() {
        return        name +
                "|" + sl  +
                "|" + sku  +
                "|" + ag  +
                "|" + type1  +
                "|" + year1 +
                "|" + month1 +
                "|" + day1 +
                "|" + year2 +
                "|" + month2 +
                "|" + day2 +
                "|" + updatesteps +
                "|" + buffer +
                "|" + method +
                "|" + autom +
                "|" + status1 +
                "|" + res1 +
                "|" + res2 +
                "|" + res3 +
                "|" + res4 +
                "|" + res5 +
                "|" + res6 +
                "|" + res7 +
                "|" + res8 +
                "|" + res9 +
                "|" + res10 +
                "|" + year3 +
                "|" + month3 +
                "|" + day3 ;
    }
}
