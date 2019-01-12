package squaredungeon.net;

public class Packet02Movement extends Packet{
	
	private String name;
	private int x,y;
	private boolean moving;
	
	public Packet02Movement(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.name = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.moving = Integer.parseInt(dataArray[3]) == 1;
	}
	
	public Packet02Movement(String name, int x, int y, boolean moving) {
		super(02);
		this.name = name;
		this.x = x;
		this.y = y;
		this.moving = moving;
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
		return ("02" + this.name + "," + this.x + "," + this.y + "," + (this.moving?1:0)).getBytes();
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
	public boolean getMoving() {
		return moving;
	}
}
