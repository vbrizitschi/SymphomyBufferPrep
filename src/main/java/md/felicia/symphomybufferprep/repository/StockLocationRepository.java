package md.felicia.symphomybufferprep.repository;

import md.felicia.symphomybufferprep.entity.StockLocation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockLocationRepository extends JpaRepository<StockLocation, Long> {
    List<StockLocation> findAllByisDeletedAAndAndStockLocationNameNotIn(boolean deleted, List<String> stockLocationName, Sort sort);
}
