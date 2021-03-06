package com.ksnet.net;


/**
 *<ul> 
 *  <li>(주)KSNET의 PG서비스 시스템에 파일을 전송할 때 사용하는 샘플 프로그램입니다.
 * 	<li>실행 환경은 jre 1.5.x 이상입니다.  </li>
 * 	<li>ksnetjapp.jar 를 classpath에 추가하여 com.ksnet.net.KsnetFileUploader 를 실행하시면 됩니다.</li>
 * 		ex) java -classpath ./ksnetjapp.jar com.ksnet.net.KsnetFileUploader "c:/My documents/files/ksnet.20070113.txt" 2999199991 20070112
 *  <li>BCD:배치승인,EDI:매입요청결과,CBI:현금영수증,LST:거래내역,TDL:주문상세내역.</li>
 *</ul>
 * @author 이훈구(foxworld@ksnet.co.kr)
 * @version 1.0
 * @since 2007.01.12.
 * @see SocketFileHandler
 */

public class KsnetFileUploader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int rtn = SocketFileHandler.fileUpload("210.181.28.116", 9800, args[0], "EDI",args[1], args[2]);
		if(rtn == 0) System.out.println("success!!");
		else         System.out.println("fail!!");

		
	}

}
