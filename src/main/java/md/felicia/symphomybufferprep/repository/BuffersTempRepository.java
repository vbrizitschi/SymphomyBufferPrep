package md.felicia.symphomybufferprep.repository;

import md.felicia.symphomybufferprep.entity.Bufferstemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuffersTempRepository extends JpaRepository<Bufferstemp, String> {

  @Procedure(procedureName = "SymRun_Felicia.dbo.Runner_CALCULATE_BUFFER_JSON", outputParameterName = "json")
  List<Bufferstemp> Runner_CALCULATE_BUFFER_JSON(String json);
}
