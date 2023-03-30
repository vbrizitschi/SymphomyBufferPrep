package md.felicia.symphomybufferprep.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="Symphony_Spring_Log", catalog = "SymRun_Felicia", schema = "dbo")
public class OperationLog {
    @Id
    @Column(name = "rowid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rowId;
    @CreationTimestamp
    @Column(name = "log_date")
    private LocalDateTime logDate;
    @Column(name = "log_user_name")
    private String logUserName;
    @Column(name = "log_operation")
    private String logOperation;
    @Column(name="log_message")
    private String logMessage;

    public OperationLog(String logOperation, String logMessage, String logUserName) {
        this.logOperation = logOperation;
        this.logMessage = logMessage;
        this.logUserName = logUserName;
    }

    public OperationLog() {

    }
}
