package com.ksnet.net;

/**
 *<ul> 
 *  <li>(��)KSNET�� PG���� �ý������κ��� ������ ������ �� ����ϴ� ���� ���α׷��Դϴ�.
 * 	<li>���� ȯ���� jre 1.5.x �̻��Դϴ�.  </li>
 * 	<li>ksnetjapp.jar �� classpath�� �߰��Ͽ� com.ksnet.net.KsnetFileDownloader �� �����Ͻø� �˴ϴ�.</li>
 * 		ex) java -classpath ./ksnetjapp.jar com.ksnet.net.KsnetFileDownloader "c:/My documents/files/ksnet.20070113.txt" 2999199991 20070112
 *</ul>
 * @author ���Ʊ�(foxworld@ksnet.co.kr)
 * @version 1.0
 * @since 2007.01.12.
 * @see SocketFileHandler
 */
public class KsnetFileDownloader {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//String host, int port, String path,
		//String busDf, String allOrNotRcv,String shopId, String date) {
		
		String path = "/app/ksnet.output.txt";
		String shopId = "2001105370";
		String date = "20141107";
		
		
		int rtn = SocketFileHandler.fileDownload("210.181.28.137", 9800, path, "EDI","0",shopId, date);
		if(rtn == 0) System.out.println("success!!");
		else         System.out.println("fail!!");		
	}
}
