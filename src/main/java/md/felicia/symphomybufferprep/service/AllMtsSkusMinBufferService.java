package md.felicia.symphomybufferprep.service;

import md.felicia.symphomybufferprep.entity.AllMtsSkus;
import md.felicia.symphomybufferprep.repository.AllMtsSkusMinBufferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.Optional;

@Service
public class AllMtsSkusMinBufferService {
    private final AllMtsSkusMinBufferRepository allMtsSkusMinBufferRepository;

    @Autowired
    public AllMtsSkusMinBufferService(AllMtsSkusMinBufferRepository allMtsSkusMinBufferRepository) {
        this.allMtsSkusMinBufferRepository = allMtsSkusMinBufferRepository;
    }

    public Optional<AllMtsSkus> findByStockLocationNameAndLocationSkuName(String stockLocationName, String locationSkuName){
        return allMtsSkusMinBufferRepository.findByStockLocationNameIgnoreCaseAndLocationSkuNameIgnoreCase(stockLocationName, locationSkuName);
    }
}
