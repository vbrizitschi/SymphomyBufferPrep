package md.felicia.symphomybufferprep.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.DTO.*;
import md.felicia.symphomybufferprep.entity.*;
import md.felicia.symphomybufferprep.repository.AllMtsSkusMinBufferRepository;
import md.felicia.symphomybufferprep.repository.BuffersTempRepository;
import md.felicia.symphomybufferprep.repository.Ctxt2Repository;
import md.felicia.symphomybufferprep.service.AllMtsSkusMinBufferService;
import md.felicia.symphomybufferprep.service.BufferService;
import md.felicia.symphomybufferprep.service.StockLocationService;
import md.felicia.symphomybufferprep.service.SymphonyFileStructureService;
//import org.jetbrains.annotations.NotNull;
import org.apache.tomcat.util.json.JSONParser;
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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private final BuffersTempRepository buffersTempRepository;
    private final StockLocationService stockLocationService;
    private final Ctxt2Repository  ctxt2Repository;
    final String catalog = "CATALOG";

    final String COMPLETED_EVENT = "complete";

    @Autowired
    public BufferController(Environment env, BufferService bufferService, AllMtsSkusMinBufferService allMtsSkusMinBufferService,
                            AllMtsSkusMinBufferRepository allMtsSkusMinBufferRepository, SymphonyFileStructureService symphonyFileStructureService, BuffersTempRepository buffersTempRepository, StockLocationService stockLocationService, Ctxt2Repository ctxt2Repository) {
        this.env = env;
        this.bufferService = bufferService;
        this.allMtsSkusMinBufferService = allMtsSkusMinBufferService;
        this.allMtsSkusMinBufferRepository = allMtsSkusMinBufferRepository;
        this.symphonyFileStructureService = symphonyFileStructureService;
        this.buffersTempRepository = buffersTempRepository;
        this.stockLocationService = stockLocationService;
        this.ctxt2Repository = ctxt2Repository;
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
    public ResponseEntity<SseEmitter> rebuildMinBuffer(@RequestParam("file") MultipartFile file
                                                     , @RequestParam(value = "changeMinBuffer", defaultValue = "0") String changeMinBuffer) throws IOException, InterruptedException {

        final SseEmitter sseEmitter = new SseEmitter(0L);
        ExecutorService service  = Executors.newSingleThreadExecutor();

        service.execute(() -> {
            try {
                sseEmitter.send("Read data from excel file");
                    Set<MinBufferDTO> minBuffers = bufferService.getAllMinBuffers(file);
                sseEmitter.send("Excel data received successfully");
                sseEmitter.send("Start rebuilding Symphony data");
                    doBuildMinBufferFile(minBuffers, changeMinBuffer);
                sseEmitter.send("Finished rebuilding, txt file was created");

                log.info("Output file  successfully created");
                ////############
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                log.info("Start running Symphony part - " + dateFormat.format(date));

                    updateSymphonyTechTable(0);

                sseEmitter.send("Symphony data loading starts: " + dateFormat.format(date));

                    doSymphonyProcess(env);

                    updateSymphonyTechTable(1);

                date = new Date();
                log.info("End running Symphony part - " + dateFormat.format(date));
                sseEmitter.send("Symphony loading is complete " + dateFormat.format(date));

                sseEmitter.send(SseEmitter
                        .event()
                        .id(String.valueOf(System.currentTimeMillis()))
                        .name(COMPLETED_EVENT)
                        .data(""));

                sseEmitter.complete();
            } catch (Exception e) {
                e.printStackTrace();
                sseEmitter.completeWithError(e);
            } finally {
                updateSymphonyTechTable(1);
            }
        });

        return new ResponseEntity<>(sseEmitter, HttpStatus.OK);
    }

    @RequestMapping(value = "/runCalculateBuffer", method = RequestMethod.POST,consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SseEmitter> calcBuffer(@RequestParam("runCalculateBufferDTO") String calculateBufferDTO) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        RunCalculateBufferDTO runCalculateBufferDTO = mapper.readValue(calculateBufferDTO,RunCalculateBufferDTO.class);

        final SseEmitter sseEmitter = new SseEmitter(0L);
        ExecutorService service  = Executors.newSingleThreadExecutor();

        service.execute(() -> {
            try{

                String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
                String fileName = env.getProperty("bufferPrep.output-folder") + "/Seasonality_CalcBuffer_"+ currentDate + ".txt";

                sendMessage("Read input data, and reformat for symphony proc", sseEmitter);

                readInputParams(runCalculateBufferDTO, sseEmitter);

                RunBufferDTO runBufferDTO = new RunBufferDTO(runCalculateBufferDTO);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonStr;

                sendMessage("Create JSON from input data and write value as string ", sseEmitter);

                jsonStr = objectMapper.writeValueAsString(runBufferDTO);

                sendMessage("Call runner calculate buffer proc from Symphony database", sseEmitter);

                buffersTempRepository.Runner_CALCULATE_BUFFER_JSON(jsonStr);

                sendMessage("Try to obtain result from Symphony database", sseEmitter);

                List<Bufferstemp> buffersTempList  = buffersTempRepository.findAll();

                BufferedWriter writer= new BufferedWriter(new FileWriter(fileName));

                sendMessage("Write result in Symphony seasonality output file", sseEmitter);

                for (Bufferstemp bufferstemp: buffersTempList){
                        writer.append(bufferstemp.toString());
                        writer.append("\n");
                }
                writer.close();

                sendMessage("Write file finished with success", sseEmitter);
                sendMessage("Symphony data loading starts...", sseEmitter);

//                doSymphonyProcess(env);

                sendMessage("End running Symphony part with success...", sseEmitter);

                sseEmitter.send(SseEmitter
                        .event()
                        .id(String.valueOf(System.currentTimeMillis()))
                        .name(COMPLETED_EVENT)
                        .data(""));


                sseEmitter.complete();

            } catch (Exception e){
                e.printStackTrace();
                sseEmitter.completeWithError(e);
            }

        });
        return  new ResponseEntity<>(sseEmitter, HttpStatus.OK);
    }

    @RequestMapping(value = "/get-sl", method = RequestMethod.GET)
    public ResponseEntity<?> getStockLocationsDTO(){
        List<StockLocation> stockLocations = stockLocationService.getAllByDeleted(false);
        List<StockLocationDTO> stockLocationsDTO = new ArrayList<>();

        for (StockLocation stockLocation: stockLocations){
            StockLocationDTO stockLocationDTO = new StockLocationDTO();
            stockLocationDTO.setStockLocationDescription(stockLocation.getStockLocationDescription());
            stockLocationDTO.setStockLocationName(stockLocation.getStockLocationName());
            stockLocationDTO.setDeleted(stockLocation.isDeleted());

            stockLocationsDTO.add(stockLocationDTO);
        }
        return ResponseEntity.ok(stockLocationsDTO);
    }

    @RequestMapping(value = "/get-ctxt2", method = RequestMethod.GET)
    public ResponseEntity<?> getPolicies(){
        List<Policy> policies = ctxt2Repository.findAll();
        List<PolicyDTO> policyDTOs = new ArrayList<>();

        for(Policy policy : policies ){
            PolicyDTO policyDTO = new PolicyDTO();
            policyDTO.setPolicy(policy.getPolicy());
            policyDTOs.add(policyDTO);
        }

        return ResponseEntity.ok(policyDTOs);

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


    private void doBuildMinBufferFile(@NotNull Set<MinBufferDTO> minBuffers, String changeMinBuffer) throws IOException {
        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
        String fileName = env.getProperty("bufferPrep.output-folder") + "/MTSSKUS_SymFelMan_"+ currentDate +".txt";

        log.info("Try to write in Symphony file: " + fileName);

        BufferedWriter writer= new BufferedWriter(new FileWriter(fileName));

        for(MinBufferDTO minBuffer : minBuffers){
            Optional<AllMtsSkus> optMtsSkus
                    = allMtsSkusMinBufferService.findByStockLocationNameAndLocationSkuName(minBuffer.getStockLocation(), minBuffer.getSKUName());
            Optional<AllMtsSkus> optMtsSkusCatalog
                    = allMtsSkusMinBufferRepository.findByStockLocationNameIgnoreCaseAndLocationSkuNameIgnoreCase(this.catalog, minBuffer.getSKUName());

            if  (optMtsSkusCatalog.isPresent()) {


                AllMtsSkus mtsSkusCatalog = optMtsSkusCatalog.get();
                int bufferSize;
                String tvc;
                int safetyStock;

                if(optMtsSkus.isEmpty()) {
                    bufferSize = minBuffer.getMinBufferSize();
                    safetyStock = 0;
                } else {
                    AllMtsSkus mtsSkus = optMtsSkus.get();
                    bufferSize = (mtsSkus.getBufferSize()<minBuffer.getMinBufferSize())?minBuffer.getMinBufferSize():mtsSkus.getBufferSize();
                    safetyStock = mtsSkus.getSaftyStock();
                }

                MinOutputBufferDTO minOutputBuffer = new MinOutputBufferDTO();

                minOutputBuffer.setStockLocationName(minBuffer.getStockLocation()); //1
                minOutputBuffer.setSkuName(minBuffer.getSKUName()); //2
                minOutputBuffer.setSkuDescription(mtsSkusCatalog.getSkuDescription()); //3
                minOutputBuffer.setBufferSize(bufferSize);
                minOutputBuffer.setSafetyStock(safetyStock); //5
                minOutputBuffer.setOriginStockLocation(mtsSkusCatalog.getOriginStockLocation()); //6
                minOutputBuffer.setReplenishmentTime(mtsSkusCatalog.getReplenishmentTime()); //7
                // minOutputBuffer.setUnitPrice(mtsSkus.getUnitPrice()); //8
                // minOutputBuffer.setTvc(tvc); //9
                minOutputBuffer.setMinReplenishment(mtsSkusCatalog.getMinimumReplenishment()); //11
                minOutputBuffer.setReplenishmentMultip(mtsSkusCatalog.getMultiplications()); //12
                if (Objects.equals(changeMinBuffer, "1")) {
                    minOutputBuffer.setMinimumBufferSize(minBuffer.getMinBufferSize()); //48
                }

                writer.append(minOutputBuffer.toString());
                writer.append("\n");

            }

        }
        writer.close();
    }

    private void updateSymphonyTechTable(int flag){
        String symphonyFileName = "MTSSKUS";
        String symphonyFieldName = "MINIMUMBUFFERSIZE";

        SymphonyFileStructure  symFileStructureMinBuffSize
                    = symphonyFileStructureService.getSymphonyFileStructure(symphonyFileName, symphonyFieldName);
        symphonyFieldName = "BUFFERSIZE";
        SymphonyFileStructure  symFileStructureBuffSize
                    = symphonyFileStructureService.getSymphonyFileStructure(symphonyFileName, symphonyFieldName);

        symFileStructureMinBuffSize.setAvoidWhenUpdate(flag);
        symFileStructureBuffSize.setAvoidWhenUpdate(flag);

        symphonyFileStructureService.updateSymphonyFileStructure(symFileStructureMinBuffSize);
        symphonyFileStructureService.updateSymphonyFileStructure(symFileStructureBuffSize);

    }

    private void randomDelay() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sendMessage(String message, SseEmitter sseEmitter) throws IOException {
        AtomicReference<String> lvMessage = new AtomicReference<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        lvMessage.set(dateFormat.format(new Date()) + ": " + message);

        randomDelay();
        log.info(lvMessage.get());
        sseEmitter.send(lvMessage.get());
    }

    private void readInputParams(RunCalculateBufferDTO runCalculateBufferDTO, SseEmitter sseEmitter) throws IOException {
        List<String> listOfMessage = new ArrayList<>();

        listOfMessage.add(" - \t stockLocationName: " + runCalculateBufferDTO.getStockLocations().stream().map(StockLocationDTO::getStockLocationName).collect(Collectors.joining(",")));
        listOfMessage.add(" - \t policy: " + runCalculateBufferDTO.getCtxt2().stream().map(PolicyDTO::getPolicy).collect(Collectors.joining(",")));
        listOfMessage.add(" - \t rt: "+ runCalculateBufferDTO.getRt());
        listOfMessage.add(" - \t minBuffer: "+ runCalculateBufferDTO.getMinBuffer());
        listOfMessage.add(" - \t maxBuffer: " + runCalculateBufferDTO.getMaxBuffer());
        listOfMessage.add(" - \t onlyOverstock: "+ runCalculateBufferDTO.getOnlyOverstock());
        listOfMessage.add(" - \t periodForAverage: "+ runCalculateBufferDTO.getPeriodForAverage());
        listOfMessage.add(" - \t periodForRec: "+ runCalculateBufferDTO.getPeriodForRec());
        listOfMessage.add(" - \t spikeFactor: " + runCalculateBufferDTO.getSpikeFactor());
        listOfMessage.add(" - \t increase: " + runCalculateBufferDTO.getIncrease());
        listOfMessage.add(" - \t moreBuffer: " + runCalculateBufferDTO.getMoreBuffer());
        listOfMessage.add(" - \t decrease: "+ runCalculateBufferDTO.getDecrease());
        listOfMessage.add(" - \t lessBuffer: " + runCalculateBufferDTO.getLessBuffer());
        listOfMessage.add(" - \t automatic: " + runCalculateBufferDTO.getAutomatic());
        listOfMessage.add(" - \t bufferMulti: " + runCalculateBufferDTO.getBufferMulti());
        listOfMessage.add(" - \t useAvailability: " +runCalculateBufferDTO.getUseAvailability());
        listOfMessage.add(" - \t isInitialCalculation: " + runCalculateBufferDTO.getIsInitialCalculation());
        listOfMessage.add(" - \t setAnalogsChildsBufferZero: " + runCalculateBufferDTO.getSetAnalogsChildsBufferZero());
        listOfMessage.add(" - \t setAnalogsGroupBufferZero: " + runCalculateBufferDTO.getSetAnalogsGroupBufferZero());
        listOfMessage.add(" - \t addDaysFromToday: " + runCalculateBufferDTO.getAddDaysFromToday());
        for (String messages: listOfMessage){
            sendMessage(messages, sseEmitter);
        }
    }
}
