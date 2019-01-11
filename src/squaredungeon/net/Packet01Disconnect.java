package squaredungeon.net;

public class Packet01Disconnect extends Packet{
	
	private String name;
	
	public Packet01Disconnect(byte[] data) {
		super(01);
		this.name = readData(data);
	}
	
	public Packet01Disconnect(String name) {
		super(01);
		this.name = name;
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
		return ("01" + this.name).getBytes();
	}
	
	public String getUsername() {
		return name;
	}
}
