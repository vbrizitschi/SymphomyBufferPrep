package md.felicia.symphomybufferprep.service;

import md.felicia.symphomybufferprep.entity.StockLocation;
import md.felicia.symphomybufferprep.repository.StockLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Service
public class StockLocationService {

    private final StockLocationRepository stockLocationRepository;

    @Autowired
    public StockLocationService(StockLocationRepository stockLocationRepository) {
        this.stockLocationRepository = stockLocationRepository;
    }

    public List<StockLocation> getAll() {
        return stockLocationRepository.findAll(Sort.by(Sort.Order.by("stockLocationDescription")));
    }

    public List<StockLocation> getAllByDeleted(boolean isDeleted){
        return stockLocationRepository.findAllByisDeletedAAndAndStockLocationNameNotIn(   isDeleted
                                                                                        , List.of("1625")
                                                                                        , Sort.by(Sort.Order.by("stockLocationDescription")));
    }
}
