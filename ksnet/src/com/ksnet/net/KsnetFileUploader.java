package com.ksnet.net;


/**
 *<ul> 
 *  <li>(��)KSNET�� PG���� �ý��ۿ� ������ ������ �� ����ϴ� ���� ���α׷��Դϴ�.
 * 	<li>���� ȯ���� jre 1.5.x �̻��Դϴ�.  </li>
 * 	<li>ksnetjapp.jar �� classpath�� �߰��Ͽ� com.ksnet.net.KsnetFileUploader �� �����Ͻø� �˴ϴ�.</li>
 * 		ex) java -classpath ./ksnetjapp.jar com.ksnet.net.KsnetFileUploader "c:/My documents/files/ksnet.20070113.txt" 2999199991 20070112
 *  <li>BCD:��ġ����,EDI:���Կ�û���,CBI:���ݿ�����,LST:�ŷ�����,TDL:�ֹ��󼼳���.</li>
 *</ul>
 * @author ���Ʊ�(foxworld@ksnet.co.kr)
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