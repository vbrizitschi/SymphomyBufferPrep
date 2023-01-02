package md.felicia.symphomybufferprep.repository;

import md.felicia.symphomybufferprep.controller.CalculationLogController;
import md.felicia.symphomybufferprep.entity.CalculationLogRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationLogRowRepository extends JpaRepository<CalculationLogRow, Integer> {
}
