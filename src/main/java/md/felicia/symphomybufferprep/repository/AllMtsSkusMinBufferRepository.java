package md.felicia.symphomybufferprep.repository;

import md.felicia.symphomybufferprep.DTO.MinOutputBufferDTO;
import md.felicia.symphomybufferprep.entity.AllMtsSkus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AllMtsSkusMinBufferRepository extends JpaRepository<AllMtsSkus, Long> {
    Optional<AllMtsSkus> findByStockLocationNameIgnoreCaseAndLocationSkuNameIgnoreCase(String stockLocationName , String locationSkuName);
}
