package squaredungeon.net;

public class Packet00Join extends Packet{
	
	private String name;
	private int x,y;
	
	public Packet00Join(byte[] data) {
		super(00);
		String[]dataArray = readData(data).split(",");
		this.name = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}
	
	public Packet00Join(String name, int x, int y) {
		super(00);
		this.name = name;
		this.x = x;
		this.y = y;
	}
	@Override
	public void writeData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void writeData(GameServer server) {
		server.sendDataToEveryClient(getData());
	}

	@Override
	public byte[] getData() {
		return ("00" + this.name +"," + getX()+"," +getY()).getBytes();
	}
	
	public String getUsername() {
		return name;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
