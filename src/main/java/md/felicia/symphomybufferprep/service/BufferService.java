package md.felicia.symphomybufferprep.service;

import lombok.extern.slf4j.Slf4j;
import md.felicia.symphomybufferprep.DTO.MinBufferDTO;
import md.felicia.symphomybufferprep.entity.BufferRow;
import md.felicia.symphomybufferprep.util.ExcelWorker;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class BufferService {
    public List<BufferRow> getAllBuffers(MultipartFile file){
        try{
            log.info("Try to obtaining info from excel file: " + file.getOriginalFilename());
            return ExcelWorker.excelToBufferList(file.getInputStream());
        } catch (IOException e) {
            throw getException(e);
        }
    }

    public Set<MinBufferDTO> getAllMinBuffers(MultipartFile file) {
            log.info("Try to obtaining info from excel file: " + file.getOriginalFilename());
        try {
            return ExcelWorker.excelToMinBufferSet(file.getInputStream());
        } catch (IOException e) {
            throw getException(e);
        }

    }
    private static RuntimeException  getException(IOException e){
        return new RuntimeException("Fail to parse excel file: " + e.getMessage());
    }
}
