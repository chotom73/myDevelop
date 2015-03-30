package com.ksnet.net;

/**
 *<ul> 
 *  <li>(주)KSNET의 PG서비스 시스템으로부터 결과데이타(문자열) 수신할 때 사용하는 API 샘플 프로그램입니다.
 *  <li>msgDownload보다는 fileDownload 메소드 사용을 권장합니다. 
 * 	<li>실행 환경은 jre 1.5.x 이상입니다.  </li>
 * 	<li>ksnetjapp.jar 를 classpath에 추가하여 com.ksnet.net.KsnetMsgDownloader 를 실행하시면 됩니다.</li>
 * 		ex) java com/ksnet/net/KsnetFileDownloader  2999199999 20070112
 *</ul>
 * @author 이훈구(foxworld@ksnet.co.kr)
 * @version 1.0
 * @since 2012.04.12.
 * @see SocketFileHandler
 */
public class KsnetMsgDownloader {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		String rtn = SocketFileHandler.msgDownload("210.181.28.116", 9800,  "EDI","0",args[0], args[1]);
		if(rtn == null ) System.out.println("fail!!");
		else             System.out.println("success!![" + rtn + "]");			
	}
}
