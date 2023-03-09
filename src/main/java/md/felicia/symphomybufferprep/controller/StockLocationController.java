package md.felicia.symphomybufferprep.controller;

import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.entity.StockLocation;
import md.felicia.symphomybufferprep.service.StockLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api")
@Slf4j
public class StockLocationController {

    private final StockLocationService stockLocationService;

    @Autowired
    public StockLocationController(StockLocationService stockLocationService) {
        this.stockLocationService = stockLocationService;
    }

    @RequestMapping(value = "/allsl", method = RequestMethod.GET)
    public List<StockLocation> getAllSkus() {
        return this.stockLocationService.getAll();
    }
}
