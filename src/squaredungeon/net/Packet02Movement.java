package squaredungeon.net;

public class Packet02Movement extends Packet{
	
	private String name;
	private int x,y;
	
	public Packet02Movement(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.name = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}
	
	public Packet02Movement(String name, int x, int y) {
		super(02);
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
		return ("02" + this.name + "," + this.x + "," + this.y).getBytes();
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
