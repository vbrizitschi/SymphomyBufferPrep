package md.felicia.symphomybufferprep.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "bufferstemp", catalog = "SymRun_Felicia", schema = "dbo")
public class Bufferstemp {
    @Id
    @Column(name = "rowid")
    private Integer rowid;
    @Column(name = "name")
    private String name ;
    @Column(name = "sl")
    private String sl ;
    @Column(name = "sku")
    private String sku ;
    @Column(name ="ag")
    private String ag ;
    @Column(name = "type1")
    private String type1 ;
    @Column(name= "year1")
    private Integer year1 ;
    @Column(name = "month1")
    private Integer month1 ;
    @Column(name = "day1")
    private Integer day1 ;
    @Column(name = "year2")
    private Integer year2 ;
    @Column(name = "month2")
    private Integer month2 ;
    @Column(name = "day2")
    private Integer day2 ;
    @Column(name = "updatesteps")
    private Integer updatesteps ;
    @Column(name = "buffer")
    private Integer buffer ;
    @Column(name= "method")
    private String method ;
    @Column(name ="autom")
    private Integer autom ;
    @Column(name = "status1")
    private String status1 ;
    @Column(name = "res1")
    private String res1 ;
    @Column(name = "res2")
    private String res2 ;
    @Column(name = "res3")
    private String res3 ;
    @Column(name = "res4")
    private String res4 ;
    @Column(name = "res5")
    private String res5 ;
    @Column(name = "res6")
    private String res6 ;
    @Column(name = "res7")
    private String res7 ;
    @Column(name = "res8")
    private String res8 ;
    @Column(name = "res9")
    private String res9 ;
    @Column(name = "res10")
    private String res10 ;
    @Column(name = "year3")
    private Integer year3 ;
    @Column(name = "month3")
    private Integer month3 ;
    @Column(name = "day3")
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
