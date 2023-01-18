package md.felicia.symphomybufferprep.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Symphony_FileStructure", catalog ="SymRun_Felicia", schema = "dbo")
@Getter
@Setter
public class SymphonyFileStructure {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "avoidWhenUpdate")
    private int avoidWhenUpdate;

    @Column(name="file_name")
    private String fileName;

    @Column(name="field_name")
    private String fieldName;

}
