package com.ksnet.net;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <ul>
 *  <li>(��)KSNET�� PG���� �ý��ۿ� ������ �����ϰų� ������ �� ����ϴ� API�Դϴ�.
 * 	<li>���� ȯ���� jre 1.5.x �̻��Դϴ�. 
 * 	<li>ksnetjapp.jar �� package�Ǿ� �ֽ��ϴ�.
 *  <li>code�� �����Ͽ� ����ϰ��� �� �� SocketFileHandler.fileDownload, SocketFileHandler.fileUpload �� ȣ���մϴ�.
 *  <li>�ٷ� �����ϰ��� �� �� com.ksnet.net.KsnetFileDownloader, com.ksnet.net.KsnetFileUploader�� �����մϴ�.
 * </ul>
 * 
 * @author ���Ʊ�(foxworld@ksnet.co.kr)
 * @version 1.0
 * @since 2007.01.12.
 * @see KsnetFileUploader
 * @see KsnetFileDownloader
 */
public class SocketFileHandler {

	private static final int ERROR_FILE_CREAT         = 100;
	private static final int ERROR_FILE_WRITE         = 101;
	private static final int ERROR_MISMATCH_RECV_CNT  = 102;
	private static final int ERROR_FILE_OPEN          = 103;
	private static final int ERROR_GET_FILENAME       = 104;
	private static final int ERROR_FILE_INSERT_DB     = 105;
	private static final int ERROR_FILE_READ          = 106;
	private static final int ERROR_REQTDATE_SMALL     = 107;
	private static final int ERROR_NOT_REGISTER_STORE = 108;


	private static final int TIMEOUT = 30000; // 30seconds.
	private static final int MAX_NUM_CHARS = 1024;
	private static final char UPLOAD = '1';
	private static final char DOWNLOAD = '2';
	
	/**
     * * <pre>	 
	 *  ������ CHARSET�� �����Ѵ�.
	 *  �⺻������ KSNET�� KSC5601(="EUC-KR") ���ڵ��� ����Ѵ�. 
	 *  ���� UTF-8�̳� ��Ÿ �ٸ� CHARSET���� ������ ���ڵ� �Ǿ� �ְų� �����ϰ� �ʹٸ�
	 *  �� ����� �����ؾ� �Ѵ�. 	 
	 * </pre>
	 **/
	private static final String LOCAL_FILE_CHARSET="KSC5601";
	
		
	                                                            

	private static PrintStream KSNOUT = null;
	
	private Socket socket = null;

	private InetSocketAddress destAddr = null;
	private int destPort = -1;
	private BufferedWriter bout = null;
	private BufferedReader bin = null;
	private BufferedReader bfin = null;
	private char rplyFlag = '\0';
	private boolean isAllClosed = false;

	private BufferedWriter bfout;


	private static void showLog(int rtnCode) {
		System.out.println("���������ڵ�: "+rtnCode);
		printreg("���������ڵ�: "+rtnCode);
	}
	private void closeAll() {
		KSNOUT.flush();
		if (isAllClosed)
			return;
		try {
			if (bfin!=null)
				bfin.close();
			if (bfout!=null)
				bfout.close();
			if (bin!=null)
				bin.close();
			if (bout!=null)
				bout.close();
			if (socket!=null)
				socket.close();
			isAllClosed= true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void printerr(String msg) {
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		String ts=cal.get(Calendar.YEAR)+""+
		          cal.get(Calendar.MONTH)+""+cal.get(Calendar.DATE)+""+"."
		          +""+cal.get(Calendar.HOUR)+""+cal.get(Calendar.MINUTE)+""+cal.get(Calendar.SECOND);
		KSNOUT.println("[ERR:"+ts+"]");

	}
	
	private static void printpkt(String msg) {
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		String ts=cal.get(Calendar.YEAR)+""+
		          cal.get(Calendar.MONTH)+""+cal.get(Calendar.DATE)+""+"."
		          +""+cal.get(Calendar.HOUR)+""+cal.get(Calendar.MINUTE)+""+cal.get(Calendar.SECOND);
		KSNOUT.println("[PKT:"+ts+"]"+msg);
		
	}
	
	private static void printreg(String msg) {
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		String ts=cal.get(Calendar.YEAR)+""+
		          cal.get(Calendar.MONTH)+""+cal.get(Calendar.DATE)+""+"."
		          +""+cal.get(Calendar.HOUR)+""+cal.get(Calendar.MINUTE)+""+cal.get(Calendar.SECOND);
		KSNOUT.println("    ["+ts+"]"+msg);
		
	}
	
	private static void setLogRedirect() {
		
		try {
			KSNOUT = new PrintStream(new BufferedOutputStream(new FileOutputStream("./kspay.socket.log",true)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	/**
         * @return  0:����   -1:����
	 * @param host : IP Adress	                        ex) "210.181.28.137" 
	 * @param port : Port Number	                        ex) 9800
	 * @param path : Full Path of a file to save	        ex) "C : /WORKs/���԰��/ksnet.20070129.down.txt"
	 * @param busDf : ���� ����                           ==> "LST":�ŷ����� �ٿ�ε� "EDI" : ���԰�� �ٿ�ε�   
	 * @param allOrNotRcv :  �̼��� ���ϸ� �ޱ� ���� 0-���, 1-�̼������ϸ�    ex) "0"
	 * @param shopId :  ���� ID	                        ex) "2002100397"
	 * @param date : �ް��� �ϴ� ������ ��������(���԰�������� ��� ���԰����)	ex) "20070125"
	 * 
	 * <pre>
	 *  <li>���� ȯ���� JRE 1.4.x �̻��Դϴ�.</li>
	 * 	<li>Ex) SocketFileHandler.fileDownload("210.181.28.137", 9800, "D:/WORKs/���Լۼ���/t.down.txt", "EDI","0","2002100397", "20070125");
	 *      </li>
	 *  <li>���� �α״� ���α׷��� �����ϴ� ���� ���丮(working directory)�� ksnet.socket.log ������Ͽ� ��ϵ˴ϴ�.</li> 
	 * </pre>
	 */
	public static int fileDownload(String host, int port, String path,
			String busDf, String allOrNotRcv,String shopId, String date) {
		int rtn=0;
		SocketFileHandler sfh = new SocketFileHandler();
		try {
			rtn=sfh.downloadFile(host, port, path, busDf,allOrNotRcv, shopId, date);
			showLog(rtn);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		sfh.closeAll();
		
		return rtn;
	}
	
	/**
	 * @return  0:����   -1:����
	 * @param host : IP Adress	                        ex) "210.181.28.137" 
	 * @param port : Port Number	                        ex) 9800
	 * @param path : Full Path of a file to save	        ex) "C : /WORKs/���԰��/ksnet.20070129.down.txt"
	 * @param busDf : ���� ����                           ==> "EDI" : ���Կ�û
	 * @param shopId :  ���� ID	                        ex) "2002100397"
	 * @param date : ���Կ�û��	ex) "20070125"
	 * <pre>
	 *  <li>���� ȯ���� JRE 1.4.x �̻��Դϴ�.</li>
	 * 	<li>Ex) SocketFileHandler.fileUpload("210.181.28.137", 9800, "D:/WORKs/���Լۼ���/t.txt", "EDI","2999199999", "20061220");
	 *  </li>
	 *  <li>���� �α״� ���α׷��� �����ϴ� ���� ���丮(working directory)�� ksnet.socket.log ������Ͽ� ��ϵ˴ϴ�.</li> 
	 * </pre>
	 *
	 */
	public static int fileUpload(String host, int port, String path,
			String busDf, String shopId, String date) {
		SocketFileHandler sfh = new SocketFileHandler();
		int rtn=0;
		try {
			rtn=sfh.uploadFile(host, port, path, busDf, shopId, date);
			showLog(rtn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return -1;
		}
		sfh.closeAll();
		
		return rtn;
	}

	private int uploadFile(String host, int port, String path, String busDf,
			String shopId, String date)  {
		int rtn=0;
		int len_rest = 0;

		try {
			createHandler(host, port);
			sendStartMsg(busDf, UPLOAD, "1", shopId, date);
			if ((rtn=checkReplyMsg())!=0) {
				closeAll();
				return rtn;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		printreg("startMsg ����!");

		try {
			bfin = new BufferedReader(new InputStreamReader(new FileInputStream(path), LOCAL_FILE_CHARSET)); //���ε� ���� ����
		} catch (Exception e) {
			sendInfoOrError('E', ERROR_FILE_OPEN);
			printreg("[FileUpload]file open error(code:"+ERROR_FILE_OPEN+",filename="
					+ path + ") \n");
			e.printStackTrace();
		}

		 byte filebuf[] = new byte[850];
		 initBytes(filebuf);

		//CharBuffer filebuf = CharBuffer.allocate(850);
		int sendCnt = 0, pos = 0;
		boolean isEOF = false;

		try {
			StringBuffer buffer = new StringBuffer();
			int len_sum = 0;
			while (true) {

				// ������ �а� 850����Ʈ�� �ѱ� ���� �����Ѵ�.
				String s_filebuf = new String();
				s_filebuf = bfin.readLine();
				System.out.println(">> " + s_filebuf);

				if(s_filebuf == null)
				{					
					isEOF = true;	
					filebuf = buffer.toString().getBytes("KSC5601");				
				}
				else
				{		
																
					len_sum =len_sum + s_filebuf.getBytes("KSC5601").length +1;
					if(len_sum <= 850)
					{
						buffer.append(s_filebuf+"\n");	
						continue;
					}
					else
					{
						len_sum = 0;
						System.out.println("buffer.toString():[" + buffer.toString() + "]");
						filebuf = buffer.toString().getBytes("KSC5601");
						buffer = null;
						buffer = new StringBuffer(s_filebuf+"\n");						
					}
				}
					
System.out.println("filebuf:[" + new String(filebuf) + "]");
				
				
				sendCnt++;
				if (writeMsg(filebuf, sendCnt) < 0) {
					printreg("����");

					closeAll();
					return -1;
				}

				rtn=checkReplyMsg();
				
				if (rplyFlag == 'E')
				{
					printerr("E�߻�! ");

					closeAll();
					return rtn;
				}

				if (isEOF)
					break;
			}
//			bfin.close();

//			Thread.sleep(1000);
			if( sendInfoOrError('F', sendCnt) < 0 ) 
				return -1;
			rtn = checkReplyMsg();

			if( rplyFlag == 'E' ) 
				return rtn;		/* ���� �߻� */
			/**
			 * 
			 */

			closeAll();
		} catch (Exception e) {
			return -1;
		}

		return 0;

	}

	private int downloadFile(String host, int port, String path, String busDf,
			String allOrNotRcv,String shopId, String date) throws IOException  {
		int rtn=0;

		try {
			createHandler(host, port);
			sendStartMsg(busDf, DOWNLOAD, allOrNotRcv, shopId, date);
						
			if ((rtn=checkReplyMsg())!=0) {
				closeAll();
				return rtn;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		printreg("startMsg ����!");
		
		
		try {			
			bfout = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), LOCAL_FILE_CHARSET));
		} catch (IOException e) {
			sendInfoOrError('E', ERROR_FILE_CREAT);
			printreg("[FileDownload]file create error(code:"+ERROR_FILE_CREAT+", filename:"+path+")");
			e.printStackTrace();
		}

		int 	fd, rcvCnt, len ;
		char [] buf= new char[2048];
		char [] tmp= new char[5];

		rcvCnt = 0;

		while(true)
		{
			initBuf(buf);

			try {
				bin.read(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}

//			printreg("buf==>"+new String(buf));
			
			/* ���� �߻� Ȥ�� �ٿ�ε� ���� */
			if( buf[6] == 'E' || buf[6] == 'F' ) 
				break;

			rcvCnt ++;

			String tmpStr=new String(buf,6,buf.length-6);
//			printreg("tmpStr==>"+tmpStr);
			len = strLen(tmpStr);

			if( buf[len+6 - 1] != '\n' )
			{
				System.out.println("last ch of buf : " + String.valueOf(buf[len - 1]));
				buf[len] = '\n';
				len += 1;
			}
			
			try {
				bfout.write(buf, 6+35, len  - 35 ) ;
				
			} catch (Exception e) {
				printreg("[FileDownload] write file error, filename = [%s] : %s\n");
				closeAll();
				return -1;
			
			}

			if( sendInfoOrError('R', 0) < 0 ){
				closeAll();
				return -1;
			}
		}

		initBuf(tmp); 
		System.arraycopy(buf, 7, tmp, 0, 4);
		
		printreg("tmp :"+new String(tmp));
		
		rtn = Integer.parseInt(new String(tmp).trim());
		if( buf[6] == 'E' ) return rtn;

		if( rcvCnt != rtn )
		{
			sendInfoOrError('E', ERROR_MISMATCH_RECV_CNT);

			printreg("[FileDownload] mismatch SendCount and RecvCount!! (code:\n"+ERROR_MISMATCH_RECV_CNT+")");
			return -1;
		}

		if( sendInfoOrError('F', rcvCnt) < 0 ) 
			return -1;

		if( rtn == 0 && rcvCnt == 0 )
			printreg("[FileDownload] Not Exist Recieved Files!!\n");
		
		return 0;
	}
	
	
		/**
     * @return  �������:����   null:����
	 * @param host : IP Adress	                        ex) "210.181.28.137" 
	 * @param port : Port Number	                        ex) 9800	 
	 * @param busDf : ���� ����                           ==> "LST":�ŷ����� �ٿ�ε� "EDI" : ���԰�� �ٿ�ε�   
	 * @param allOrNotRcv :  �̼��� ���ϸ� �ޱ� ���� 0-���, 1-�̼��ŵ���Ÿ��    ex) "0"
	 * @param shopId :  ���� ID	                        ex) "2002100397"
	 * @param date : �ް��� �ϴ� ������ ��������(���԰���� ��� ���԰����)	ex) "20070125"
	 * 
	 * <pre>
	 *  <li>���� ȯ���� JRE 1.4.x �̻��Դϴ�.</li>
	 * 	<li>Ex) SocketFileHandler.msgDownload("210.181.28.137", 9800, "EDI","0","2002100397", "20070125");
	 *      </li>
	 *  <li>���� �α״� ���α׷��� �����ϴ� ���� ���丮(working directory)�� ksnet.socket.log ������Ͽ� ��ϵ˴ϴ�.</li> 
	 * </pre>
	 */
	public static String msgDownload(String host, int port,
			String busDf, String allOrNotRcv,String shopId, String date) {
		String rtn = null;
		SocketFileHandler sfh = new SocketFileHandler();
		try {
			rtn=sfh.downloadFile2Str(host, port,  busDf,allOrNotRcv, shopId, date);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		sfh.closeAll();
		return rtn;
	}
	
	/**************************************************************************************************************************/
	
	/**
	 * @return  0:����   -1:����
	 * @param host : IP Adress	                        ex) "210.181.28.137" 
	 * @param port : Port Number	                    ex) 9800
	 * @param msg  : UPLOAD�� ����	                    ex) "1411233411271                                              \n1412485921121"
	 * @param busDf : ���� ����                       ==> "EDI" : ���Կ�û
	 * @param shopId :  ���� ID	                        ex) "2002100397"
	 * @param date : ���Կ�û��	ex) "20070125"
	 * <pre>
	 *  <li>���� ȯ���� JRE 1.4.x �̻��Դϴ�.</li>
	 * 	<li>Ex) SocketFileHandler.msgUpload("210.181.28.137", 9800, ����, "EDI","2999199999", "20061220");
	 *  </li>
	 *  <li>���� �α״� ���α׷��� �����ϴ� ���� ���丮(working directory)�� ksnet.socket.log ������Ͽ� ��ϵ˴ϴ�.</li> 
	 * </pre>
	 *
	 */
	public static int msgUpload(String host, int port, String msg,
			String busDf, String shopId, String date) {
		SocketFileHandler sfh = new SocketFileHandler();
		int rtn=0;
		try {
			rtn=sfh.uploadStr2File(host, port, msg, busDf, shopId, date);
			showLog(rtn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
			return -1;
		}
		sfh.closeAll();
		return rtn;
	}
	
	
	public int uploadStr2File(String host, int port, String msg, String busDf,
			String shopId, String date)  {
		int rtn=0;
		int len_rest = 0;

		try {
			createHandler(host, port);
			
			/* STEP1. ��������  Client=>Server */
			sendStartMsg(busDf, UPLOAD, "1", shopId, date);
			
			/* STEP2. ���������� ���� ����  Client<=Server */
			if ((rtn=checkReplyMsg())!=0) {
				closeAll();
				return rtn;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		printreg("startMsg ����!");

		 byte strbuf[] = new byte[850];
		 initBytes(strbuf);

		int sendCnt = 0, pos = 0;
		boolean isEOF = false;

		try {
			StringBuffer buffer = new StringBuffer();
			int len_sum = 0;
			
			StringTokenizer st = new StringTokenizer( msg,"\n");
			
			while (true) {
				
			
				// ������ �а� 850����Ʈ�� �ѱ� ���� �����Ѵ�.
				String s_strbuf = new String();			
				
				if(st.hasMoreTokens() == false)
				{
					isEOF = true;	
					strbuf = buffer.toString().getBytes();		
					System.out.println(">> " +  buffer.toString());		
				}
				else
				{	
					
					s_strbuf = st.nextToken();
					System.out.println(s_strbuf);
	
																
					len_sum =len_sum + s_strbuf.getBytes().length +1;
					if(len_sum <= 850)
					{
						buffer.append(s_strbuf+"\n");	
						continue;
					}
					else
					{
						len_sum = 0;
						System.out.println("buffer.toString():[" + buffer.toString() + "]");
						strbuf = buffer.toString().getBytes();
						buffer = null;
						buffer = new StringBuffer(s_strbuf+"\n");						
					}
				}
					
				
				/* STEP 3. DATA ���� */
				sendCnt++;
				System.out.println("buffer.toString():" + new String(strbuf));
				if (writeMsg(strbuf,sendCnt) < 0) {
					printreg("����");

					closeAll();
					return -1;
				}
				
				/* STEP 4. ���ۿ� ���� ���� */
				rtn=checkReplyMsg();
				
				if (rplyFlag == 'E')
				{
					printerr("E�߻�! ");

					closeAll();
					return rtn;
				}

				if (isEOF)
					break;
			}
			
			/* STEP 5. �����û */
			if( sendInfoOrError('F', sendCnt) < 0 ) 
				return -1;
				
			/* STEP 6. ����Ȯ������  */	
			rtn = checkReplyMsg();

			if( rplyFlag == 'E' ) 
				return rtn;		/* ���� �߻� */
			/**
			 * 
			 */

			
		} catch (Exception e) {
			return -1;
		}

		return 0;

	}

	public String downloadFile2Str(String host, int port,  String busDf,
			String allOrNotRcv,String shopId, String date) throws IOException  {
		int rtn=0;

		try {
			createHandler(host, port);
			
			/* STEP 1 �������� Client=>Server */
			sendStartMsg(busDf, DOWNLOAD, allOrNotRcv, shopId, date);
			/* STEP2. ���������� ���� ����  Client<=Server */			
			if ((rtn=checkReplyMsg())!=0) {
				closeAll();
				return null;
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		printreg("startMsg ����!");
		
		
	

		int 	fd, rcvCnt, len ;
		char [] buf= new char[2048];
		char [] tmp= new char[5];

		rcvCnt = 0;
		
		StringBuffer r_str = new StringBuffer("");		
		while(true)
		{
			initBuf(buf);

			try {
				/* STEP 3 DATA ����(Client<=Server)*/
				bin.read(buf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			
			/* ���� �߻� Ȥ�� �ٿ�ε� ���� */
			if( buf[6] == 'E' || buf[6] == 'F' ) 
				break;

			rcvCnt ++;

			String tmpStr=new String(buf,6,buf.length-6);

			len = strLen(tmpStr);

			if( buf[len+6 - 1] != '\n' )
			{
				System.out.println("last ch of buf : " + String.valueOf(buf[len - 1]));
				buf[len] = '\n';
				len += 1;
			}
			
			try {
				//bfout.write(buf, 6+35, len  - 35 ) ;
				r_str.append(new String(buf, 6+35,len-35));
				
			} catch (Exception e) {
				printreg("[FileDownload] write file error, filename = [%s] : %s\n");
				closeAll();
				return null;
			
			}
			
			/* STEP 4 DATA ������� (Client<=Server)*/
			if( sendInfoOrError('R', 0) < 0 ){
				closeAll();
				return null;
			}
		}

		initBuf(tmp); 
		System.arraycopy(buf, 7, tmp, 0, 4);
		
		printreg("tmp :"+new String(tmp));
		
		rtn = Integer.parseInt(new String(tmp).trim());
		if( buf[6] == 'E' ) return null;

		if( rcvCnt != rtn )
		{
			/* STEP 5 DATA ������� ���� (������)  (Client=>Server)*/
			sendInfoOrError('E', ERROR_MISMATCH_RECV_CNT);

			printreg("[FileDownload] mismatch SendCount and RecvCount!! (code:\n"+ERROR_MISMATCH_RECV_CNT+")");
			return null;
		}
		
		/* STEP 5 DATA ������� Ȯ��(����)  (Client=>Server)*/
		if( sendInfoOrError('F', rcvCnt) < 0 ) 
			return null;

		if( rtn == 0 && rcvCnt == 0 )
			printreg("[FileDownload] Not Exist Recieved Files!!\n");
		
		return r_str.toString();
	}
	

	private int readMsg(char [] rcvBuf) {
		char	tmp[] = new char[6];
		int		len;
		String s = new String(tmp);
		try {
			bin.read(tmp,0,6);
		
			len= !Character.isDigit(tmp[0]) ? 0 :Integer.parseInt(s);
			bin.read(rcvBuf,0,len);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		return 0;
	}
	

	
	
	private int sendInfoOrError(char errorOrInfo, int rtncode)
	{
		int n=0;
		String tmpStr= null;
		String sndStr = null;

		/* 20121029  ytjeon�߰�   -- 9999�� ��� �̻��� ���  ����/���� �Ǽ� �����ʵ� Ȯ��  */
		if( rtncode >= 9999 &&  errorOrInfo == 'F' )
		{
			tmpStr=String.format("%c9999%08d%22s", new Object[]{new Character(errorOrInfo), new Integer(rtncode), " "});
		}
		else
		{
			tmpStr=String.format("%c%04d%30s", new Object[]{new Character(errorOrInfo), new Integer(rtncode), " "});
		
		}
		sndStr=String.format("%06d%s", new Object[]{new Integer(n=strLen(tmpStr)),tmpStr});
		
				
		try {
			bout.write(sndStr,0,sndStr.length());
			bout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		
		printpkt("send string:"+sndStr);
		return 0;
	}
	
	
	
	private int writeMsg(byte[] filebuf, int sendCnt) {// ���ε� ���� KSNet �ʿ� ������ ���̴� �޼ҵ�
		String sndStr=null;
		String tmpStr=null;
		
		
		try {
			System.out.println("writeMsg.filebuf = "+filebuf);
			String tmp=new String(filebuf, "KSC5601");
			System.out.println("writeMsg.tmp = "+ tmp);
			
		//	tmp = new String(tmp.getBytes(),"KSC5601");
		//	System.out.println(tmp);
			int n = 0;
	
	
			/* 20121029  ytjeon�߰�   -- 9999�� ��� �̻��� ���  ����/���� �Ǽ� �����ʵ� Ȯ��  */
			if( sendCnt >= 9999 )
			{				
				//tmpStr=String.format("D9999%08d%22s%s",sendCnt , new Object[] { " ", tmp}).replaceAll("\r", "");	
				tmpStr=String.format("D9999%08d%22s%s", new Object[] { " ", tmp}).replaceAll("\r", "");	
			}
			else
			{
				tmpStr=String.format("D0000%30s%s", new Object[] { " ", tmp}).replaceAll("\r", "");				
			
			}
			sndStr=String.format("%06d%s", new Object[]{new Integer(n=strLen(tmpStr)),tmpStr});
			
			
			bout.write(sndStr);

			bout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		printpkt("send string:"+sndStr);

		initBytes(filebuf);
		return 0;
	}
	
	private void createHandler(String host, int port) throws IOException {
		try {
			setLogRedirect();
			socket=new Socket();
			socket.setSoTimeout(TIMEOUT);
			socket.setReuseAddress(true);
			socket.setOOBInline(true);
			socket.setKeepAlive(true);
			socket.setSoLinger(true, TIMEOUT);
			destPort = port;

			destAddr=new InetSocketAddress(InetAddress.getByName(host), port);
			socket.connect(destAddr);
			
			bin = new BufferedReader(new InputStreamReader(socket.getInputStream(), "KSC5601"));
			bout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"KSC5601"));

		} catch (Exception e) {
			closeAll();
			e.printStackTrace();
		}
		printreg("Connected !!");

	}

	/**
	 * @return
	 */
	private int checkReplyMsg() {
		char[] rcvBuf = new char[64];
//		String rcvStr = "";
//		String tmpStr = "";
//		int winSize=1024,pos=0,rtnLen=0;
//		printreg("*****");
		int rtn=0;
		
		try {
			bin.read(rcvBuf);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}

		rplyFlag=rcvBuf[1];
//		rplyFlag=rcvStr.charAt(1);
		String s = new String(rcvBuf);
//		String s=rcvStr;
		rtn= !Character.isDigit(s.charAt(0)) ? 0 :Integer.parseInt(s.substring(0,1));
		printreg("rcvBuf   :"+s);
//		printreg("rcvStr:"+rtn);
//		printreg("*****");
		return rtn;
	}
	
	private int sendStartMsg(String busDf, char upOrDownDF, String allOrNotRcv, String storeId, String requestDate) {
		String tmpStr=null;
		String sndStr=null;
		int n=0;
		
		tmpStr=String.format(
				"0%3s%c%s%10s%8s%50s",
				new Object[] { busDf, new Character(upOrDownDF), allOrNotRcv, storeId,requestDate, " " }).replaceAll("\r", "");
		printreg("tmpStr : "+tmpStr);
		sndStr=String.format("%06d%s",new Object[] { new Integer(tmpStr.getBytes().length), tmpStr });
		printreg("sndStr:"+sndStr);

		try {
			bout.write(sndStr);
			bout.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printreg("[start msg]:"+sndStr);

		return 1;
	}

	private int strLen(String str) {
		
		
		int tg=str.indexOf("\0");
		
		return (tg >=0 ? tg: str.length()) ;
	}
	private void initBuf(char [] buf ) {
		for (int i=0; i < buf.length; i++) {
			buf[i]='\0';
		}
	}
	
	private void initBytes(byte [] buf ) {
		for (int i=0; i < buf.length; i++) {
			buf[i]='\0';
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		 SocketFileHandler.fileUpload("192.168.90.35",9800, "D:\WORKs\���Լۼ���\t.txt", "CBI",	 "2999199999", "20061220");
//		SocketFileHandler.fileUpload("210.181.28.137", 9800, "D:/WORKs/���Լۼ���/t.txt", "EDI","2999199999", "20070406");
//		SocketFileHandler.fileDownload("210.181.28.137", 9800, "D:/WORKs/���Լۼ���/t.down.txt", "EDI","0","2999199999", "20070406");
		SocketFileHandler.fileUpload("210.181.28.137", 9800, "D:/WORKs/���Լۼ���/t.txt", "BCD","2999199999", "20070406");;
		// TODO Auto-generated method stub 210.181.28.137
	}

}
