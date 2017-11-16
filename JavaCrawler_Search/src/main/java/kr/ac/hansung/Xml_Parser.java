package kr.ac.hansung;

import java.util.ArrayList;

public interface Xml_Parser {
	public final static String PARAM_URL="http://www.juso.go.kr/addrlink/addrLinkApi.do?";
    public final static String ROF="currentPage=1&countPerPage=10";
    public final static String APT_NAME="&keyword=강서로7길";
    public final static String KEY="&confmKey=U01TX0FVVEgyMDE3MTEwODA5MzQzNTEwNzQ2NDc=";
    
	public String getURLParam(String data);
    public void printList(ArrayList<DTO> list);
    public void apiParserSearch() throws Exception;
}
