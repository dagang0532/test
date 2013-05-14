import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestHttp2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		writeInfo();
	}
	public static void writeInfo(){
	   	 HttpURLConnection conn = null;
			try {
				System.out.println("开始建立连接");
				URL url = new URL("http://127.0.0.1:9001/test.lzg?command=0x10001");
					conn = (HttpURLConnection) url.openConnection();		
					conn.setConnectTimeout(30000);
					conn.setReadTimeout(10000);
					conn.setUseCaches(false);
					conn.setDoInput(true);	
					conn.setDoOutput(true);			
					conn.setRequestMethod("GET");
					conn.connect();	
					
					OutputStream out=conn.getOutputStream();
					
					ByteArray ba=new ByteArray();
					//ba.writeByte(1);
					ba.writeInt(0x10001);
					ba.writeUTF("liuzg0003");
					byte[] sendData=ba.toArray();
					ba=new ByteArray();
					ba.writeInt(sendData.length);
					ba.writeByteArray(sendData);
					sendData=ba.toArray();
					out.write(sendData);
					out.flush();

					InputStream in=conn.getInputStream();
					int length = in.available();
		   		    System.out.println("收到信息");
		   		    DataInputStream dis=new DataInputStream(in);
		   		    int len=dis.readInt();
		   		    int command=dis.readInt();
		   		    String sss=dis.readUTF();
		   		    System.out.println("cmd="+command+",msg="+sss);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}
	   }
}
