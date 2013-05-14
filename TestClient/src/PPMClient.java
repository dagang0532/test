import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;


public class PPMClient {

	private static final int REGISTER_PLAYER=0X010001;//ע�����
	private static final int LOGIN_PLAYER=0X010002;//��¼���
	private static final int CHANGE_IMAGE_PLAYER=0X010003;//�޸����ͷ��
	private static final int TEST_ROBOT=0X010099;//���Ի�����
	
	
	private static final int FRIEND_LIST=0X030001;//��ȡ�����б�
	private static final int FRIEND_ADD=0X030002;//��Ӻ���
	private static final int FRIEND_REMOVE=0X030003;//ɾ������
	
	
	private static java.util.concurrent.atomic.AtomicInteger index=new AtomicInteger();;
	static long use=System.currentTimeMillis();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		use=System.currentTimeMillis();
		for (int j = 1; j <=1; j++) {
			Runnable run = new Runnable() {
				public void run() {
					for (int i = 1; i <= 100; i++) {
						new PPMClient().createLink();
						System.out.println("��ɵ�"+index+"��ɫ����!");
						System.out.println("����ʱ:"+(System.currentTimeMillis()-use)+"����");
					}
				}
			};
			new Thread(run).start();
		}
		
	}
	/**
	 * @author liuzhigang
	 * ��ʼ��������
	 */
    public void createLink(){
    	 HttpURLConnection conn = null;
			try {
				System.out.println("��ʼ��������");
					URL url = new URL("http://127.0.0.1:12001/test.lzg");//58.30.47.158
					conn = (HttpURLConnection) url.openConnection();		
					conn.setConnectTimeout(30000);
					conn.setReadTimeout(10000);
					conn.setUseCaches(false);
					conn.setDoInput(true);	
					conn.setDoOutput(true);			
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Charset", "UTF-8");
					
					conn.connect();	
					
					OutputStream out=conn.getOutputStream();
					DataOutputStream dos=new DataOutputStream(out);
					byte[] sendData=send();
					dos.writeInt(sendData.length);
					dos.write(sendData);
					System.out.println("�������ݳ���:"+sendData.length);
					out.flush();
					out.close();

					InputStream in=conn.getInputStream();
					int length = in.available();
					byte[] data=new byte[length];
					in.read(data);
					receive(data);
					in.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.disconnect();
			}
    }
    /**
     * @author liuzhigang
     * @return
     * ������Ϣ
     */
    public byte[] send(){
    	ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream dos=new DataOutputStream(baos);
    	try {
			int cmd=REGISTER_PLAYER;			
			dos.writeInt(cmd);
			switch(cmd){
			case REGISTER_PLAYER:
				sendRegister(dos);
				break;
			case LOGIN_PLAYER:
				sendLogin(dos);
				break;
			case CHANGE_IMAGE_PLAYER:
				sendChangeImage(dos);
				break;
			case FRIEND_LIST:
				sendListFriend(dos);
				break;
			case FRIEND_ADD:
				sendAddFriend(dos);
				break;
			case FRIEND_REMOVE:
				sendRemoveFriend(dos);
				break;
			case TEST_ROBOT:
				sendTestRobo(dos);
				break;
			default :
				return new byte[0];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new byte[0];
		}
    	return baos.toByteArray();
    	
    }
   
    /**
     * @author liuzhigang
     * @return
     * ����ע������
     */
    public  void sendRegister(DataOutputStream dos){
    	try {
    		index.getAndIncrement();
			dos.writeUTF("");//imei
			dos.writeUTF("liuzg0008"+index);
			dos.writeUTF("123456");
			dos.writeUTF("��");
			byte[] image=new byte[]{1,2,3,4,5,6};
			dos.writeInt(image.length);
			dos.write(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dos
     * ���͵�¼����
     */
    public  void sendLogin(DataOutputStream dos){
    	try {
    		dos.writeByte(1);
    		dos.writeUTF("51652f640364190186be207a");
//			dos.writeByte(2);//1.����id����ʽ��¼ 2.�����ǳ�&�������ʽ��¼
//			dos.writeUTF("liuzg001");
//			dos.writeUTF("123456");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dos
     * �����޸�ͷ��
     */
    public  void sendChangeImage(DataOutputStream dos){
    	try {
			dos.writeUTF("515aa988036401ff1d1c3aa4");//playerID;
			byte[] data=new byte[]{3,3,3,3,3,3,3,3,3,4,4,4,4,4};
			dos.writeInt(data.length);
			dos.write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dos
     *  ��������б�
     */
   public  void sendListFriend(DataOutputStream dos){
	   try {
		dos.writeUTF("5160d9120364e277597889f7");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * @author liuzhigang
    * @param dos
    * ��Ӻ���
    */
   public  void sendAddFriend(DataOutputStream dos){
	   try {
		dos.writeUTF("5160d9120364e277597889f7");//self_id
		   dos.writeUTF("liuzg002");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * @author liuzhigang
    * @param dos
    * ���ͻ����˲���
    */
   public void sendTestRobo(DataOutputStream dos){
	   try {
		   /*
		    * 1.���Բ������
		    * 2.�������
		    * 3.�������
		    */
		dos.writeInt(3);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   /**
    * @author liuzhigang
    * @param dos
    * ɾ������
    */
   public  void sendRemoveFriend(DataOutputStream dos){
	   try {
		dos.writeUTF("5160d9120364e277597889f7");//self_id
		   dos.writeUTF("5160d9120364e277597889f7");//remove_friend_id
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
    /**
     * @author liuzhigang
     * @param data
     * ������Ϣ
     */
    public  void receive(byte[] data){
    	try {
			ByteArrayInputStream bais=new ByteArrayInputStream(data);
			DataInputStream dis=new DataInputStream(bais);
			int realLen=dis.readInt();
			int cmd=dis.readInt();
			switch(cmd){
			case REGISTER_PLAYER:
				receiveRegister(dis);
				break;
			case LOGIN_PLAYER:
				receiveLogin(dis);
				break;
			case CHANGE_IMAGE_PLAYER:
				receiveChangeImage(dis);
				break;
			case FRIEND_LIST:
				receiveListFriend(dis);
				break;
			case FRIEND_ADD:
				receiveAddFriend(dis);
				break;
			case FRIEND_REMOVE:
				receiveRemoveFriend(dis);
				break;
			case TEST_ROBOT:
				receiveTestRobot(dis);
				break;
			}
			System.out.println("�յ���¼������Ϣ:realLen="+realLen+",cmd=0x"+Integer.toHexString(cmd));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     *  ����ע�᷵�ص���Ϣ
     */
    public  void receiveRegister(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			if(isSuccess){//ע��ɹ�
				String id=dis.readUTF();
				System.out.println("ע��ɹ�,id="+id);
			}else{
				String desc=dis.readUTF();
				System.out.println("ע��ʧ��ԭ����:"+desc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }
    /**
     * @author liuzhigang
     * @param dis
     * ���յ�¼���ص���Ϣ
     */
    public  void receiveLogin(DataInputStream dis){
    	try {
			boolean isLogin=dis.readBoolean();
			if(isLogin){//��¼�ɹ�
				String id=dis.readUTF();
				System.out.println("��¼�ɹ�:id="+id);
				int serverCount=dis.readInt();
				System.out.println("����"+serverCount+"���������ɹ�ʹ��");
				for(int index=0;index<serverCount;index++){
					String name=dis.readUTF();
					String type=dis.readUTF();
					String url=dis.readUTF();
					System.out.println("name="+name+",type="+type+",url="+url);
				}
			}else{
				String desc=dis.readUTF();
				System.out.println("��¼ʧ��:desc="+desc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * �����޸�ͷ��Ľ��
     */
    public  void receiveChangeImage(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			System.out.println("ͷ���޸ĳɹ�"+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * ���պ����б�
     */
    public  void receiveListFriend(DataInputStream dis){
    	System.out.println("�����б���Ϣ");
    	try {
			int count=dis.readInt();
			if(count<=0){
				System.out.println("û�к���");
			}
			for(int index=1;index<=count;index++){
				String id=dis.readUTF();
				String name=dis.readUTF();
				String sex=dis.readUTF();
				System.out.println(id+",name="+name+",sex="+sex);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * ��Ӻ���
     */
    public  void receiveAddFriend(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			String result=dis.readUTF();
			System.out.println("��Ӻ��ѽ��:result="+result+",isSuccess="+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * ɾ������
     */
    public  void receiveRemoveFriend(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			String result=dis.readUTF();
			System.out.println("ɾ�����ѽ��:result="+result+",isSuccess="+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    /**
     * @author liuzhigang
     * @param dis
     * ���Ի�����
     */
    public void receiveTestRobot(DataInputStream dis){
    	try {
			boolean isSuccess=dis.readBoolean();
			System.out.println("�����˲��Է���:"+isSuccess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
