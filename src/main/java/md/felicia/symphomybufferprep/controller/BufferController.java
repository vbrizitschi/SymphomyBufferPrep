package md.felicia.symphomybufferprep.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@CrossOrigin
@RequestMapping("/api/documents")
@Slf4j
public class BufferController {
    @Autowired
    private Environment env;
    @Autowired
    private BufferService bufferService;

    @RequestMapping(value = "/re-build-file", method = RequestMethod.POST)
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

            Runtime runtime = Runtime.getRuntime();

            String batFile = env.getProperty("bufferPrep.bat-file-name");

            try {
                log.info("Try to call bat file: " + batFile);
                runtime.exec("cmd /c start "+ batFile);
            }catch (IOException ioException){
                throw new RuntimeException(ioException);
            }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


}
