package md.felicia.symphomybufferprep.service;

import md.felicia.symphomybufferprep.entity.StockLocation;
import md.felicia.symphomybufferprep.repository.StockLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockLocationService {

    private StockLocationRepository stockLocationRepository;

    @Autowired
    public StockLocationService(StockLocationRepository stockLocationRepository) {
        this.stockLocationRepository = stockLocationRepository;
    }

    public List<StockLocation> getAll() {
        return stockLocationRepository.findAll();
    }
}
