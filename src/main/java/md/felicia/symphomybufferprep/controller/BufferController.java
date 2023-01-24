package md.felicia.symphomybufferprep.controller;

import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.DTO.MinBufferDTO;
import md.felicia.symphomybufferprep.DTO.MinOutputBufferDTO;
import md.felicia.symphomybufferprep.entity.AllMtsSkus;
import md.felicia.symphomybufferprep.entity.BufferRow;
import md.felicia.symphomybufferprep.entity.SymphonyFileStructure;
import md.felicia.symphomybufferprep.repository.AllMtsSkusMinBufferRepository;
import md.felicia.symphomybufferprep.service.AllMtsSkusMinBufferService;
import md.felicia.symphomybufferprep.service.BufferService;
import md.felicia.symphomybufferprep.service.SymphonyFileStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/documents")
@Slf4j
public class BufferController {
    private final AllMtsSkusMinBufferRepository allMtsSkusMinBufferRepository;
    private final Environment env;
    private final BufferService bufferService;
    private final AllMtsSkusMinBufferService allMtsSkusMinBufferService;
    private final SymphonyFileStructureService symphonyFileStructureService;

    @Autowired
    public BufferController(Environment env, BufferService bufferService, AllMtsSkusMinBufferService allMtsSkusMinBufferService,
                            AllMtsSkusMinBufferRepository allMtsSkusMinBufferRepository, SymphonyFileStructureService symphonyFileStructureService) {
        this.env = env;
        this.bufferService = bufferService;
        this.allMtsSkusMinBufferService = allMtsSkusMinBufferService;
        this.allMtsSkusMinBufferRepository = allMtsSkusMinBufferRepository;
        this.symphonyFileStructureService = symphonyFileStructureService;
    }

    @RequestMapping(value = "/loadBuffer", method = RequestMethod.POST)
    public ResponseEntity<?> excelReader(@RequestParam("file")MultipartFile file) throws IOException {

            List<BufferRow> bufferRowList = bufferService.getAllBuffers(file);

            String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
            String fileName = env.getProperty("bufferPrep.output-folder") + "/Seasonality_CalcBuffer_"+ currentDate +".txt";

            BufferedWriter writer= new BufferedWriter(new FileWriter(fileName));
            log.info("Try to write in Symphony file: " + fileName);

            for (BufferRow bufferRow: bufferRowList){
                    writer.append(bufferRow.toString());
                    writer.append("\n");
            }
            writer.close();

            log.info("Writing to Symphony file finished ");

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            doSymphonyProcess(env);

            date = new Date();
            log.info("End running Symphony part - " + dateFormat.format(date));

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @RequestMapping(value = "/loadMinBuffer", method = RequestMethod.POST)
    public ResponseEntity<?> rebuildMinBuffer(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        final String catalog = "CATALOG";
        final String symphonyFileName = "MTSSKUS";
        final String symphonyFieldName = "MINIMUMBUFFERSIZE";

        Set<MinBufferDTO>  minBuffers = bufferService.getAllMinBuffers(file);

        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        String fileName = env.getProperty("bufferPrep.output-folder") + "/MTSSKUS_SymFelMan_"+ currentDate +".txt";
        BufferedWriter writer= new BufferedWriter(new FileWriter(fileName));
        log.info("Try to write in Symphony file: " + fileName);

        for(MinBufferDTO minBuffer : minBuffers){
            Optional<AllMtsSkus> optMtsSkus = allMtsSkusMinBufferService.findByStockLocationNameAndLocationSkuName(minBuffer.getStockLocation(), minBuffer.getSKUName());
            Optional<AllMtsSkus> optMtsSkusCatalog = allMtsSkusMinBufferRepository.findByStockLocationNameIgnoreCaseAndLocationSkuNameIgnoreCase(catalog, minBuffer.getSKUName());

            if  (optMtsSkus.isPresent() & optMtsSkusCatalog.isPresent()) {

                AllMtsSkus mtsSkus = optMtsSkus.get();
                AllMtsSkus mtsSkusCatalog = optMtsSkusCatalog.get();

                MinOutputBufferDTO minOutputBuffer = new MinOutputBufferDTO();

                minOutputBuffer.setStockLocationName(minBuffer.getStockLocation()); //1
                minOutputBuffer.setSkuName(minBuffer.getSKUName()); //2
                minOutputBuffer.setSkuDescription(mtsSkusCatalog.getSkuDescription()); //3
                minOutputBuffer.setBufferSize((mtsSkus.getBufferSize()<minBuffer.getMinBufferSize())?minBuffer.getMinBufferSize():mtsSkus.getBufferSize());
                minOutputBuffer.setSafetyStock(mtsSkus.getSaftyStock()); //5
                minOutputBuffer.setOriginStockLocation(mtsSkusCatalog.getOriginStockLocation()); //6
                minOutputBuffer.setReplenishmentTime(mtsSkusCatalog.getReplenishmentTime()); //7
               // minOutputBuffer.setUnitPrice(mtsSkus.getUnitPrice()); //8
                minOutputBuffer.setTvc(mtsSkus.getTvc()); //9
                minOutputBuffer.setMinReplenishment(mtsSkusCatalog.getMinimumReplenishment()); //11
                minOutputBuffer.setReplenishmentMultip(mtsSkusCatalog.getMultiplications()); //12
                minOutputBuffer.setMinimumBufferSize(minBuffer.getMinBufferSize()); //48

                writer.append(minOutputBuffer.toString());
                writer.append("\n");
            }

        }
        writer.close();
        log.info("Output file  successfully created");

        ////############

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        log.info("Start running Symphony part - " + dateFormat.format(date));
        SymphonyFileStructure symphonyFileStructure = symphonyFileStructureService.getSymphonyFileStructure(symphonyFileName, symphonyFieldName);

        try {
            symphonyFileStructure.setAvoidWhenUpdate(0);
            symphonyFileStructureService.updateSymphonyFileStructure(symphonyFileStructure);

            doSymphonyProcess(env);

            symphonyFileStructure.setAvoidWhenUpdate(1);
            symphonyFileStructureService.updateSymphonyFileStructure(symphonyFileStructure);

            date = new Date();
            log.info("End running Symphony part - " + dateFormat.format(date));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            symphonyFileStructure.setAvoidWhenUpdate(1);
            symphonyFileStructureService.updateSymphonyFileStructure(symphonyFileStructure);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/download-template", method = RequestMethod.GET)
    public ResponseEntity<?> downloadTemplate(@RequestParam("doc_name") String doc_name) throws IOException {

        File file =  new ClassPathResource("templates/"+ doc_name).getFile();

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+doc_name);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/excel"))
                .body(resource);
    }

    private static void doSymphonyProcess(Environment env) throws IOException, InterruptedException {
        String batFile = env.getProperty("bufferPrep.bat-file-name");
        Process process = Runtime.getRuntime().exec(batFile);
        process.waitFor();

        InputStream inputStream = process.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int c = -1;
        while ((c = inputStream.read()) != -1) {
            byteArrayOutputStream.write(c);
        }

        String response = byteArrayOutputStream.toString();
        log.info("Response from Symphony: " + response);
    }

}
