package com.ksnet.net;


/**
 *<ul> 
 *  <li>(주)KSNET의 PG서비스 시스템에 배치전문을 전송할 때 사용하는 샘플 프로그램입니다.
 *  <li>msgUpload보다는 fileUpload 메소드 사용을 권장합니다. 
 * 	<li>실행 환경은 jre 1.5.x 이상입니다.  </li>
 * 	<li>ksnetjapp.jar 를 classpath에 추가하여 com.ksnet.net.KsnetFileUploader 를 실행하시면 됩니다.</li>
 * 		ex) java  com/ksnet/net/KsnetMsgUploader  2999199991 20070112
 *  <li>BCD:배치승인,EDI:매입요청결과,CBI:현금영수증,LST:거래내역,TDL:주문상세내역.</li>
 *</ul>
 * @author 이훈구(foxworld@ksnet.co.kr)
 * @version 1.0
 * @since 2012.04.12.
 * @see SocketFileHandler
 */
public class KsnetMsgUploader {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		// msg : EDI 수동매입 요청 전문 
		String msg = "1430121212271                                              \n1430121268441                                              \n";
		
		int rtn = SocketFileHandler.msgUpload("210.181.28.116", 9800,msg,  "EDI",args[0], args[1]);
		if(rtn == 0) System.out.println("success!!");
		else         System.out.println("fail!!");		
	}
}

