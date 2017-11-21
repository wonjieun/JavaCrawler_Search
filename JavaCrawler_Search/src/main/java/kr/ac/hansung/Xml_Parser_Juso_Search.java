package kr.ac.hansung;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

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
		URL url = new URL(getURLParam("청구빌라"));
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();
		
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		xpp.setInput(bis, "utf-8");

		String tag = null;
		int event_type = xpp.getEventType();

		ArrayList<DTO> list = new ArrayList<DTO>();

		String jibunAddr = null, siNm = null, sggNm = null, emdNm = null;

		while (event_type != xpp.END_DOCUMENT) {
			
			if (event_type == xpp.START_TAG) {
				tag = xpp.getName();
				System.out.println("Start tag = " + tag);
				
			} else if (event_type == xpp.TEXT) {
				
				if (tag.equals("totalCount")) {
					System.out.println("totalCount=" + xpp.getText());
				}
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
				System.out.println("End tag = " + tag);

				if (tag.equals("juso")) {
					// DTO 객체를 만들어 여기에 데이터를 집어넣어준다.
					DTO entity = new DTO();
					
					entity.setJibunAddr(jibunAddr);
					entity.setSiNm(siNm);
					entity.setSggNm(sggNm);
					entity.setEmdNm(emdNm);
					
					list.add(entity);
				}
				
			}
			event_type = xpp.next();
		}

		// 엑셀파일을 쓰는 부분
		new Excel_Writer_Xml_Juso_Search().makeExcelFileParmList(list);

	}

	/*
	 * public ArrayList<DTO> returnList(int year) throws Exception {
	 * FileInputStream fis = new FileInputStream(
	 * "C:\\Users\\admin\\IdeaProjects\\JavaCrawler\\data\\Food_Region_Apt.xls")
	 * ; Workbook wbk = new HSSFWorkbook(fis); Sheet sheet = wbk.getSheetAt(0);
	 * int rowNum = sheet.getPhysicalNumberOfRows();
	 * 
	 * Row row = sheet.getRow(i); ArrayList<DTO> list = new ArrayList<DTO>();
	 * 
	 * String jibunAddr = null, siNm = null, sggNm = null, emdNm = null; }
	 */
	public String getURLParam(String data) throws IOException {
		String url = PARAM_URL + ROF + APT_NAME + URLEncoder.encode(data,"UTF-8") + KEY;
		/*
		 * if(search != null){ url = url+"&yadmNm"+search; }
		 */
		return url;
	}

	public void printList(ArrayList<DTO> list) { }

}
