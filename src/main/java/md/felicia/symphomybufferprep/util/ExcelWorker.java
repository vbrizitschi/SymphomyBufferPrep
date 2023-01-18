package md.felicia.symphomybufferprep.util;

import lombok.extern.slf4j.Slf4j;

import md.felicia.symphomybufferprep.DTO.MinBufferDTO;
import md.felicia.symphomybufferprep.entity.BufferRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class ExcelWorker {

    public static List<BufferRow> excelToBufferList(InputStream inputStream){

        List<BufferRow> bufferRowList = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();

        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            log.info("Try to read first sheet from workbook");
            XSSFSheet sheet = workbook.getSheetAt(0);  //only one worksheet

            log.info("Processing lines in sheet");
            for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
                BufferRow bufferRow = new BufferRow();

                XSSFRow row = sheet.getRow(i);

                for (Cell currentCell : row) {

                    switch (currentCell.getAddress().getColumn()) {
                        case 0 -> bufferRow.setName(dataFormatter.formatCellValue(currentCell));
                        case 1 -> bufferRow.setStockLocation(dataFormatter.formatCellValue(currentCell));
                        case 2 -> bufferRow.setSkuName(dataFormatter.formatCellValue(currentCell));
                        case 3 -> bufferRow.setAssortmentGroup(dataFormatter.formatCellValue(currentCell));
                        case 4 -> bufferRow.setEventSym(dataFormatter.formatCellValue(currentCell));
                        case 5 -> bufferRow.setStartYear(currentCell.getNumericCellValue());
                        case 6 -> bufferRow.setStartMonth(currentCell.getNumericCellValue());
                        case 7 -> bufferRow.setStartDay(currentCell.getNumericCellValue());
                        case 8 -> bufferRow.setEndYear(currentCell.getNumericCellValue());
                        case 9 -> bufferRow.setEndMonth(currentCell.getNumericCellValue());
                        case 10 -> bufferRow.setEndDay(currentCell.getNumericCellValue());
                        case 11 -> bufferRow.setUpdateSteps(currentCell.getNumericCellValue());
                        case 12 -> bufferRow.setResizeValue(currentCell.getNumericCellValue());
                        case 13 -> bufferRow.setResizeMethod(dataFormatter.formatCellValue(currentCell));
                        case 14 -> bufferRow.setAutoAccept(currentCell.getNumericCellValue());
                        case 15 -> bufferRow.setStatus(dataFormatter.formatCellValue(currentCell));
                        case 16 -> bufferRow.setReserved1(dataFormatter.formatCellValue(currentCell));
                        case 17 -> bufferRow.setReserved2(dataFormatter.formatCellValue(currentCell));
                        case 18 -> bufferRow.setReserved3(dataFormatter.formatCellValue(currentCell));
                        case 19 -> bufferRow.setReserved4(dataFormatter.formatCellValue(currentCell));
                        case 20 -> bufferRow.setReserved5(dataFormatter.formatCellValue(currentCell));
                        case 21 -> bufferRow.setReserved6(dataFormatter.formatCellValue(currentCell));
                        case 22 -> bufferRow.setReserved7(dataFormatter.formatCellValue(currentCell));
                        case 23 -> bufferRow.setReserved8(dataFormatter.formatCellValue(currentCell));
                        case 24 -> bufferRow.setReserved9(dataFormatter.formatCellValue(currentCell));
                        case 25 -> bufferRow.setReserved10(dataFormatter.formatCellValue(currentCell));
                        case 26 -> bufferRow.setReportedYear(currentCell.getNumericCellValue());
                        case 27 -> bufferRow.setReportedMonth(currentCell.getNumericCellValue());
                        case 28 -> bufferRow.setReportedDay(currentCell.getNumericCellValue());
                        default -> {
                        }
                    }

                }

                bufferRowList.add(bufferRow);

            }
            workbook.close();

            log.info("Excel file processed successful");
            return bufferRowList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Set<MinBufferDTO> excelToMinBufferSet(InputStream inputStream){
        Set<MinBufferDTO> minBufferDTOs = new HashSet<>();

        DataFormatter dataFormatter = new DataFormatter();
        try{
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            log.info("Try to read first sheet from workbook");
            XSSFSheet sheet = workbook.getSheetAt(0);  //only one worksheet

            log.info("Processing lines in sheet");

            for(int i = 1; i < sheet.getPhysicalNumberOfRows(); i++){
                MinBufferDTO  minBufferDTO = new MinBufferDTO();
                XSSFRow row = sheet.getRow(i);

                for (Cell currentCell : row) {
                    switch (currentCell.getAddress().getColumn()) {
                        case 0 -> minBufferDTO.setStockLocation(dataFormatter.formatCellValue(currentCell));
                        case 1 -> minBufferDTO.setSKUName(dataFormatter.formatCellValue(currentCell));
                        case 2 -> minBufferDTO.setMinBufferSize((int) currentCell.getNumericCellValue());
                        default -> {
                        }
                    }
                }

                minBufferDTOs.add(minBufferDTO);
            }
            workbook.close();

            log.info("Excel file processed successful");
           return minBufferDTOs;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
