package com.korea.dbapp.util;

public class Script {
	
	public static String href(String uri, String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("location.href='"+uri+"';");
		sb.append("</script>");
		
		return sb.toString();
	}
	
	
	// jsp의 location.href 함수 재활용하기
	public static String href(String uri) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("location.href='"+uri+"';");
		sb.append("</script>");
		
		return sb.toString();
	}

	// alert창을 띄우면서 뒤로가는 함수 
	public static String back(String msg) {
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('"+msg+"');");
		sb.append("history.back();");
		sb.append("</script>");
		
		return sb.toString();
	}
	
}
