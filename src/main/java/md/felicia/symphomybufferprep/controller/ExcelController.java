package md.felicia.symphomybufferprep.controller;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/documents")
public class ExcelController {
    @Autowired
    private Environment env;

    @RequestMapping(value = "/re-build-file", method = RequestMethod.POST)
    public String excelReader(@RequestParam("file")MultipartFile file) throws IOException {



        List<BufferRow> bufferRowList = new ArrayList<>();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);


            for(int i = 1; i<sheet.getPhysicalNumberOfRows(); i++){
                XSSFRow row = sheet.getRow(i);

                BufferRow bufferRow = new BufferRow(row);

                bufferRowList.add(bufferRow);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String currentDate = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());

        String fileName = env.getProperty("bufferPrep.output-folder") + "/Seasonality_CalcBuffer_"+ currentDate +".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));


        for (BufferRow bufferRow: bufferRowList){
           // System.out.println(bufferRow);
            writer.append(bufferRow.toString());
            writer.append("\n");
       }

        writer.close();

        Runtime runtime = Runtime.getRuntime();

        try {
            runtime.exec("cmd /c start "+  env.getProperty("bufferPrep.bat-file-name"));
        }catch (IOException ioException){
            throw new RuntimeException(ioException);
        }

        return "Success";
    }
}
