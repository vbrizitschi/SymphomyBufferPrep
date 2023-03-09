package md.felicia.symphomybufferprep.repository;

import md.felicia.symphomybufferprep.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ctxt2Repository extends JpaRepository<Policy, String> {

}
