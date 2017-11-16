package kr.ac.hansung;

import java.io.FileOutputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Excel_Writer_Xml_Juso_Search {
	
	public Excel_Writer_Xml_Juso_Search() {}

	public void makeExcelFileParmList(ArrayList<DTO> list) {
        Workbook workbook = new HSSFWorkbook();
 
        // 시트명 설정
        Sheet sheet = workbook.createSheet("sheet1");
        Row row;
 
        // 타이틀 생성부
        row = sheet.createRow(0);
 
        // 각 셀마다 타이틀을 적어준다.
        // Cell createCell(int column)
        row.createCell(0).setCellValue("jibunAddr");
        row.createCell(1).setCellValue("siNm");
        row.createCell(2).setCellValue("sggNm");
        row.createCell(3).setCellValue("emdNm");
        
        int count = 1;
        
        for (DTO entity : list) {
            row = sheet.createRow(count);
            count = count + 1;
 
            row.createCell(0).setCellValue(entity.getJibunAddr());
            row.createCell(1).setCellValue(entity.getSiNm());
            row.createCell(2).setCellValue(entity.getSggNm());
            row.createCell(3).setCellValue(entity.getEmdNm());
        }
 
        FileOutputStream fos;
        try {
            fos = new FileOutputStream("C:\\Users\\jieun\\IdeaProjects\\JavaCrawler\\data\\Juso_Search.xls");
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //workbook.close();
    }
	
}
