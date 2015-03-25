package com.ksnet.net;

/**
 *<ul> 
 *  <li>(��)KSNET�� PG���� �ý������κ��� �������Ÿ(���ڿ�) ������ �� ����ϴ� API ���� ���α׷��Դϴ�.
 *  <li>msgDownload���ٴ� fileDownload �޼ҵ� ����� �����մϴ�. 
 * 	<li>���� ȯ���� jre 1.5.x �̻��Դϴ�.  </li>
 * 	<li>ksnetjapp.jar �� classpath�� �߰��Ͽ� com.ksnet.net.KsnetMsgDownloader �� �����Ͻø� �˴ϴ�.</li>
 * 		ex) java com/ksnet/net/KsnetFileDownloader  2999199999 20070112
 *</ul>
 * @author ���Ʊ�(foxworld@ksnet.co.kr)
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
