package kr.ac.hansung;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class Xml_Parser_Juso_Search implements Xml_Parser {
	public Xml_Parser_Juso_Search() {
		try {
			System.out.println("Xml_Parser_Juso_Search is running Now...");
			apiParserSearch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void apiParserSearch() throws Exception {

		FileInputStream fis = new FileInputStream("/Users/jieun/IdeaProjects/JavaCrawler/data/Food_Region_Apt.xls");
		Workbook wbk = new HSSFWorkbook(fis);
		Sheet sheet = wbk.getSheetAt(0);
		int rowNum = sheet.getPhysicalNumberOfRows();
		ArrayList<DTO> list = new ArrayList<DTO>();

		for (int i = 1; i < rowNum; i++) {
			Row row = sheet.getRow(i);

			URL url = new URL(getURLParam(row.getCell(3).getStringCellValue()));
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();

			BufferedInputStream bis = new BufferedInputStream(url.openStream());
			xpp.setInput(bis, "utf-8");

			String tag = null;
			int event_type = xpp.getEventType();

			String jibunAddr = null, siNm = null, sggNm = null, emdNm = null;

			while (event_type != xpp.END_DOCUMENT) {

				if (event_type == xpp.START_TAG) {
					tag = xpp.getName();

				} else if (event_type == xpp.TEXT) {

					if (tag.equals("jibunAddr")) {
						jibunAddr = xpp.getText();
					} else if (tag.equals("siNm")) {
						siNm = xpp.getText();
					} else if (tag.equals("sggNm")) {
						sggNm = xpp.getText();
					} else if (tag.equals("emdNm")) {
						emdNm = xpp.getText();
					}

				} else if (event_type == xpp.END_TAG) {
					tag = xpp.getName();

					if (tag.equals("juso")) {
						
						if (sggNm.equals(row.getCell(2).getStringCellValue())) {
							// DTO 객체를 만들어 여기에 데이터를 집어넣어준다.
							DTO entity = new DTO();

							entity.setJibunAddr(jibunAddr);
							entity.setSiNm(siNm);
							entity.setSggNm(sggNm);
							entity.setEmdNm(emdNm);

							list.add(entity);
						}
					}

				}
				event_type = xpp.next();
			}
		}
		// 엑셀파일을 쓰는 부분
		new Excel_Writer_Xml_Juso_Search().makeExcelFileParmList(list);
		fis.close();
	}
	
	public String getURLParam(String data) throws IOException {
		String url = PARAM_URL + ROF + APT_NAME + URLEncoder.encode(data, "UTF-8") + KEY;
		return url;
	}

	public void printList(ArrayList<DTO> list) { }

}
