package md.felicia.symphomybufferprep.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "policies", catalog ="Farma",schema = "dbo")
public class Policies {
    @Id
    @Column(name = "policy")
    private String policy;

    @Column(name = "priority")
    private int priority;
}
