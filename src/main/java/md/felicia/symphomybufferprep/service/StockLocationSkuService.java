package md.felicia.symphomybufferprep.service;

import md.felicia.symphomybufferprep.entity.StockLocationSku;
import md.felicia.symphomybufferprep.repository.StockLocationSkuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockLocationSkuService {


    private final StockLocationSkuRepository stockLocationSkuRepository;

    @Autowired
    public StockLocationSkuService(StockLocationSkuRepository stockLocationSkuRepository) {
        this.stockLocationSkuRepository = stockLocationSkuRepository;
    }

    private static final int LIMIT = 100;

    public List<StockLocationSku> getAllWithLimit() {
        Page<StockLocationSku> page = stockLocationSkuRepository.findAll(PageRequest.of(0,LIMIT));
        return page.getContent();
    }
}
