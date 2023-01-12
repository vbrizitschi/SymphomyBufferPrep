package md.felicia.symphomybufferprep.repository;

import md.felicia.symphomybufferprep.entity.StockLocationSku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockLocationSkuRepository extends JpaRepository<StockLocationSku, Long> {

    Page<StockLocationSku> findAll(Pageable pageable);
}