package md.felicia.symphomybufferprep.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Symphony_StockLocations", catalog = "SymRun_Felicia", schema = "dbo")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StockLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stockLocationID;

    private String stockLocationName;

    private String stockLocationDescription;

    private boolean isDeleted;

}
