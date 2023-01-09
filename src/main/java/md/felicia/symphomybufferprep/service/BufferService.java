package md.felicia.symphomybufferprep.service;

import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.entity.BufferRow;
import md.felicia.symphomybufferprep.util.ExcelWorker;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class BufferService {
    public List<BufferRow> getAllBuffers(MultipartFile file){
        try{
            log.info("Try to obtaining info from excel file: " + file.getOriginalFilename());
            return ExcelWorker.excelToBufferList(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException("Fail to parse excel file: " + e.getMessage());
        }
    }

}
