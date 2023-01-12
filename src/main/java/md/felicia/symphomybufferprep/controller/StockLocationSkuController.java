package md.felicia.symphomybufferprep.controller;

import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.entity.StockLocationSku;
import md.felicia.symphomybufferprep.service.StockLocationSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/")
@Slf4j
public class StockLocationSkuController {

    private final StockLocationSkuService stockLocationSkuService;

    @Autowired
    public StockLocationSkuController(StockLocationSkuService stockLocationSkuService) {
        this.stockLocationSkuService = stockLocationSkuService;
    }


    @RequestMapping(value = "/allslsku", method = RequestMethod.GET)
    public List<StockLocationSku> getAllSkus() {
        return this.stockLocationSkuService.getAllWithLimit();
    }

}
