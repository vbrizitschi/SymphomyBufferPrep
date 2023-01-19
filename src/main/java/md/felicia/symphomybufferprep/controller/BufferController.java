package md.felicia.symphomybufferprep.controller;

import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.DTO.MinBufferDTO;
import md.felicia.symphomybufferprep.DTO.MinOutputBufferDTO;
import md.felicia.symphomybufferprep.entity.AllMtsSkus;
import md.felicia.symphomybufferprep.entity.BufferRow;
import md.felicia.symphomybufferprep.repository.AllMtsSkusMinBufferRepository;
import md.felicia.symphomybufferprep.repository.SymphonyFileStructureRepository;
import md.felicia.symphomybufferprep.service.AllMtsSkusMinBufferService;
import md.felicia.symphomybufferprep.service.BufferService;
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


import javax.transaction.Transactional;
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

    private final SymphonyFileStructureRepository symphonyFileStructureRepository;

    @Autowired
    public BufferController(Environment env, BufferService bufferService, AllMtsSkusMinBufferService allMtsSkusMinBufferService,
                            AllMtsSkusMinBufferRepository allMtsSkusMinBufferRepository, SymphonyFileStructureRepository symphonyFileStructureRepository) {
        this.env = env;
        this.bufferService = bufferService;
        this.allMtsSkusMinBufferService = allMtsSkusMinBufferService;
        this.allMtsSkusMinBufferRepository = allMtsSkusMinBufferRepository;
        this.symphonyFileStructureRepository = symphonyFileStructureRepository;
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

//            Runtime runtime = Runtime.getRuntime();
//
//            String batFile = env.getProperty("bufferPrep.bat-file-name");
//
//            try {
//                log.info("Try to call bat file: " + batFile);
//                runtime.exec("cmd /c start "+ batFile);
//            }catch (IOException ioException){
//                throw new RuntimeException(ioException);
//            }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping(value = "/loadMinBuffer", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> rebuildMinBuffer(@RequestParam("file") MultipartFile file) throws IOException, InterruptedException {
        final String catalog = "CATALOG";
        final String symphonyFileName = "MTSSKUS";
        final String symphonyFieldName = "MINIMUMBUFFERSIZE";
        Set<MinBufferDTO>  minBuffers = bufferService.getAllMinBuffers(file);

        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        String fileName = env.getProperty("bufferPrep.output-folder") + "/Min_Buffer_"+ currentDate +".txt";

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

                minOutputBuffer.setBufferSize(minBuffer.getMinBufferSize() == 0 ? 0 : mtsSkus.getBufferSize()); //4 to do, int or double
                minOutputBuffer.setSafetyStock(mtsSkus.getSaftyStock()); //5
                minOutputBuffer.setOriginStockLocation(mtsSkusCatalog.getOriginStockLocation()); //6
                minOutputBuffer.setReplenishmentTime(mtsSkusCatalog.getReplenishmentTime()); //7
                minOutputBuffer.setUnitPrice(mtsSkus.getUnitPrice()); //8
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

        try {
            symphonyFileStructureRepository.setAvoidWhenUpdate(symphonyFileName, symphonyFieldName, 0);

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

            symphonyFileStructureRepository.setAvoidWhenUpdate(symphonyFileName, symphonyFieldName, 1);

            date = new Date();
            log.info("End running Symphony part - " + dateFormat.format(date));
        }catch (Exception e){
            symphonyFileStructureRepository.setAvoidWhenUpdate(symphonyFileName, symphonyFieldName, 1);
            e.printStackTrace();
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

}
