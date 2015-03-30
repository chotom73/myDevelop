package com.ksnet.net;


/**
 *<ul> 
 *  <li>(��)KSNET�� PG���� �ý��ۿ� ��ġ������ ������ �� ����ϴ� ���� ���α׷��Դϴ�.
 *  <li>msgUpload���ٴ� fileUpload �޼ҵ� ����� �����մϴ�. 
 * 	<li>���� ȯ���� jre 1.5.x �̻��Դϴ�.  </li>
 * 	<li>ksnetjapp.jar �� classpath�� �߰��Ͽ� com.ksnet.net.KsnetFileUploader �� �����Ͻø� �˴ϴ�.</li>
 * 		ex) java  com/ksnet/net/KsnetMsgUploader  2999199991 20070112
 *  <li>BCD:��ġ����,EDI:���Կ�û���,CBI:���ݿ�����,LST:�ŷ�����,TDL:�ֹ��󼼳���.</li>
 *</ul>
 * @author ���Ʊ�(foxworld@ksnet.co.kr)
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
		
		// msg : EDI �������� ��û ���� 
		String msg = "1430121212271                                              \n1430121268441                                              \n";
		
		int rtn = SocketFileHandler.msgUpload("210.181.28.116", 9800,msg,  "EDI",args[0], args[1]);
		if(rtn == 0) System.out.println("success!!");
		else         System.out.println("fail!!");		
	}
}
