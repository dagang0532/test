import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;


public class TestHttp {
	public static void main(String[] args) {
    	writeInfo();
    }
    public static void writeInfo(){
    	 HttpURLConnection conn = null;
 		try {
 			System.out.println("��ʼ��������");
// 			URL url = new URL("http://127.0.0.1:61000/test.lzg?command=0x10001&msg=Hello");
 			URL url = new URL("http://127.0.0.1:12001/test.lzg");
// 				ByteArrayOutputStream baos=new ByteArrayOutputStream();
// 				XMLOutputFactory factory=XMLOutputFactory.newInstance();
// 				XMLStreamWriter writer=factory.createXMLStreamWriter(baos);
// 				writer.writeStartDocument();
// 				writer.writeStartElement("data");
// 					
// 				writer.writeStartElement("command");
// 				writer.writeCharacters("0x010001");
// 				writer.writeEndElement();
// 				
// 				writer.writeStartElement("msg");
// 				writer.writeCharacters("Hello,World!");
// 				writer.writeEndElement();				
// 				
// 				writer.writeEndElement();
// 				writer.writeEndDocument();
// 				writer.flush();
// 				writer.close();
// 	           byte [] sendData=baos.toByteArray();
 				conn = (HttpURLConnection) url.openConnection();
 				
 				conn.setConnectTimeout(30000);
 				conn.setReadTimeout(10000);
 				conn.setUseCaches(false);
 				conn.setDoInput(true);	
 				conn.setDoOutput(true);			
 				conn.setRequestMethod("GET");
 				conn.connect();	
 				
// 				OutputStream out=conn.getOutputStream();
 				
// 				ByteArray ba=new ByteArray();
// 				ba.writeInt(0x10001);
// 				ba.writeUTF("liuzg");
// 				byte[] sendData=ba.toArray();
// 				ba=new ByteArray();
// 				ba.writeInt(sendData.length);
// 				ba.writeByteArray(sendData);
// 				sendData=ba.toArray();
// 				out.write(sendData);
// 				out.flush();

 				InputStream in=conn.getInputStream();
 				int length = in.available();
 				System.out.println("�յ����ݳ���:"+length);
 			InputStreamReader isr=new InputStreamReader(in);
 	   		BufferedReader br=new BufferedReader(isr);
 	   		System.out.println("�յ���Ϣ");
 	   		String line="";
 	   		while((line=br.readLine())!=null){
 	   			System.out.println(line);
 	   		}
 		} catch (Exception e) {
 			e.printStackTrace();
 		} finally {
 			conn.disconnect();
 		}
    }
    public static void writeInfo2(){
    	try {
			// Post�����url����get��ͬ���ǲ���Ҫ������
			URL postUrl = new URL("http://127.0.0.1:9001/test.lzg");
			// ������
			HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
			// Output to the connection. Default is
			// false, set to true because post
			// method must write something to the
			// connection
			// �����Ƿ���connection�������Ϊ�����post���󣬲���Ҫ����
			// http�����ڣ������Ҫ��Ϊtrue
			connection.setDoOutput(true);
			// Read from the connection. Default is true.
			connection.setDoInput(true);
			// Set the post method. Default is GET
			connection.setRequestMethod("POST");
			// Post cannot use caches
			// Post ������ʹ�û���
			connection.setUseCaches(false);
			// This method takes effects to
			// every instances of this class.
			// URLConnection.setFollowRedirects��static���������������е�URLConnection����
			// connection.setFollowRedirects(true);
 
			// This methods only
			// takes effacts to this
			// instance.
			// URLConnection.setInstanceFollowRedirects�ǳ�Ա�������������ڵ�ǰ����
			connection.setInstanceFollowRedirects(true);
			// Set the content type to urlencoded,
			// because we will write
			// some URL-encoded content to the
			// connection. Settings above must be set before connect!
			// ���ñ������ӵ�Content-type������Ϊapplication/x-www-form-urlencoded��
			// ��˼��������urlencoded�������form�������������ǿ��Կ������Ƕ���������ʹ��URLEncoder.encode
			// ���б���
			connection.setRequestProperty("Content-Type",
			        "application/x-www-form-urlencoded");
			// ���ӣ���postUrl.openConnection()���˵����ñ���Ҫ��connect֮ǰ��ɣ�
			// Ҫע�����connection.getOutputStream�������Ľ���connect��
			connection.connect();
			DataOutputStream out = new DataOutputStream(connection
			        .getOutputStream());
			// The URL-encoded contend
			// ���ģ�����������ʵ��get��URL��'?'��Ĳ����ַ���һ��
			String content = "aaaaaaaaaaaaaa";
			// DataOutputStream.writeBytes���ַ����е�16λ��unicode�ַ���8λ���ַ���ʽд��������
			out.writeBytes(content); 
			out.flush();
			out.close(); // flush and close
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));//���ñ���,������������
			String line="";
			System.out.println("=============================");
			System.out.println("Contents of post request");
			System.out.println("=============================");
			while ((line = reader.readLine()) != null){
			    //line = new String(line.getBytes(), "utf-8");
			    System.out.println(line);
			}
			System.out.println("=============================");
			System.out.println("Contents of post request ends");
			System.out.println("=============================");
			reader.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }
    public static void  writeInfo3(){
    	 HttpURLConnection conn = null;
 		try {
 			System.out.println("��ʼ��������");
 			URL url = new URL("http://127.0.0.1:9001/test.lzg?command=0x10001");
 				conn = (HttpURLConnection) url.openConnection();		
 				conn.setConnectTimeout(30000);
 				conn.setReadTimeout(10000);
 				conn.setUseCaches(false);
 				conn.setDoInput(true);	
 				conn.setDoOutput(true);			
 				conn.setRequestMethod("POST");
 				conn.connect();	
 				
 				OutputStream out=conn.getOutputStream();
 				
 				ByteArray ba=new ByteArray();
 				ba.writeByte(1);
 				ba.writeInt(0x10001);
 				ba.writeUTF("liuzg");
 				byte[] sendData=ba.toArray();
 				ba=new ByteArray();
 				ba.writeInt(sendData.length);
 				ba.writeByteArray(sendData);
 				sendData=ba.toArray();
 				out.write(sendData);
 				out.flush();

 				InputStream in=conn.getInputStream();
 				int length = in.available();
 	   		    System.out.println("�յ���Ϣ");
 	   		    DataInputStream dis=new DataInputStream(in);
 	   		    int len=dis.readInt();
 	   		    int command=dis.readInt();
 	   		    String sss=dis.readUTF();
 	   		    System.out.println(command+sss);
 		} catch (Exception e) {
 			e.printStackTrace();
 		} finally {
 			conn.disconnect();
 		}
    }
}
