package md.felicia.symphomybufferprep.controller;

import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.entity.CalculationLogRow;
import md.felicia.symphomybufferprep.repository.CalculationLogRowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/logs")
@Slf4j
public class CalculationLogController {

    private final CalculationLogRowRepository calculationLogRowRepository;

    @Autowired
    public CalculationLogController(CalculationLogRowRepository calculationLogRowRepository) {
        this.calculationLogRowRepository = calculationLogRowRepository;
    }
    @RequestMapping(value = "/calculations", method = RequestMethod.GET)
    public List<CalculationLogRow> getAllLogs(){
        return calculationLogRowRepository.findAll();
    }
}
