package kr.ac.hansung;

import java.io.IOException;
import java.util.ArrayList;

public interface Xml_Parser {
	public final static String PARAM_URL="http://www.juso.go.kr/addrlink/addrLinkApi.do?";
    public final static String ROF="currentPage=1&countPerPage=10";
    public final static String APT_NAME="&keyword=";
    public final static String KEY="&confmKey=U01TX0FVVEgyMDE3MTEwODA5MzQzNTEwNzQ2NDc=";
    
	public String getURLParam(String data) throws IOException ;
    public void printList(ArrayList<DTO> list);
    public void apiParserSearch() throws Exception;
}
