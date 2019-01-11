package squaredungeon.net;

import squaredungeon.gameObjects.Mob;
import squaredungeon.main.Handler;

public class Packet03MobMovement extends Packet{

	private String mobID;
	private int x,y;
	private Handler handler;
	
	public Packet03MobMovement(byte[] data, Handler handler) {
		super(03);
		String[]dataArray = readData(data).split(",");
		this.mobID = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.handler = handler;
		
	}
	
	public Packet03MobMovement(String mobID, int x, int y, Handler handler) {
		super(03);
		this.handler = handler;
		this.mobID = mobID;
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
		return ("03" + this.mobID +"," + getX()+"," +getY()).getBytes();
	}
	

	public String getMobID() {
		
		return mobID;
	}
	
	public Mob getMob() {
		for(Mob m : handler.mob) {
			if(m.GetMobID() == mobID) {
			System.out.println(mobID);
			return m;
			}
			else {
				return null;
			}
		}
		return null;
		
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
}
