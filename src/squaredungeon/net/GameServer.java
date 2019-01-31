package squaredungeon.net;

import squaredungeon.gameObjects.Entity;
import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Mob;
import squaredungeon.gameObjects.NetPlayer;
import squaredungeon.gfx.SpriteSheet;
import squaredungeon.main.*;
import squaredungeon.net.Packet.PacketTypes;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class GameServer extends Thread{
		private DatagramSocket socket;
	    private Main main;
	    private List<NetPlayer> Players = new ArrayList<NetPlayer>();

	    public GameServer(Main main) {
	        this.main = main;
	        try {
	            this.socket = new DatagramSocket(1331);
	        } catch (SocketException e) {
	            e.printStackTrace();
	        }
	    }

	    public void run() {
	        while (true) {
	            byte[] data = new byte[1024];
	            DatagramPacket packet = new DatagramPacket(data, data.length);
	            try {
	                socket.receive(packet);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
//	            String message = new String(packet.getData());
//	            System.out.println("CLIENT [" + packet.getAddress().getHostAddress() + ":" + packet.getPort() + "]> "+ message);
//	            if (message.trim().equalsIgnoreCase("ping")) {
//	                sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
	            //}
	        }
	  
		}
	    private void parsePacket(byte[] data, InetAddress ip, int port) {
	    	String msg = new String(data).trim();
	    	PacketTypes type = Packet.lookupPacket(msg.substring(0, 2));
	    	Packet packet = null;
	    	switch(type) {
	    	default:
	    	case INVALID:
	    		break;
	    	case JOIN:
	    		packet = new Packet00Join(data);
	    		System.out.println(ip.getHostAddress() + "/" +port +"   "+ ((Packet00Join) packet).getUsername() + " is connecting...");
	    		NetPlayer player = new NetPlayer(100,100, ID.PLAYER, main.ssMob, 100, ((Packet00Join) packet).getUsername(), ip, port);
	    		this.addConnection(player, (Packet00Join) packet);
	    		break;
	    	case DISCONNECT:
	    		packet = new Packet01Disconnect(data);
	    		System.out.println(ip.getHostAddress() + "/" +port +"   "+ ((Packet01Disconnect) packet).getUsername()+ " is tryna finna go sicko mode!");
	    		this.removeConnection((Packet01Disconnect) packet);
	    		break;
	    	case MOVE:
	    		packet = new Packet02Movement(data);
	    		this.handleMove(((Packet02Movement) packet));
	    		break;
	    	case MOBMOVE:
	    		packet = new Packet03MobMovement(data, main.handler);
	    		handleMoveMob(((Packet03MobMovement) packet));
	    		break;
	    	case ENTITYREMOVE:
	    		packet = new Packet04EntityRemove(data);
	    		removeEntity(((Packet04EntityRemove) packet));
	    		break;
	    	}
		}
	    
	

		private void handleMoveMob(Packet03MobMovement packet) {
		
	    	for(int i = 0; i < main.handler.mob.size(); i++) {
	    		Mob m = main.handler.mob.get (i);
	    	
	    		if(m.mobID != null && Integer.parseInt(m.mobID) == Integer.parseInt(packet.getMobID().trim())) {
				
					if(packet.getX() > m.x) {
						m.dir = 1;
					}
					else if(packet.getX() < m.x){
						m.dir = 2;
					}
					m.x = packet.getX();
					m.y = packet.getY();
				
				
	    		
				packet.writeData(this);
	    		}
			}
		}

		public void removeConnection(Packet01Disconnect packet) {
			NetPlayer player = getNetPlayer(packet.getUsername());
			this.Players.remove(getNetPlayerIndex(packet.getUsername()));
			packet.writeData(this);
		}
		public void removeEntity(Packet04EntityRemove packet) {
	    	for(int i = 0; i < main.handler.entity.size(); i++) {
	    		Entity e = main.handler.entity.get (i);
	    	
	    		if(e.EntityID != null && Integer.parseInt(e.EntityID) == Integer.parseInt(packet.getEntityID().trim())) {
				
	    			main.handler.removeEntity(e);
				packet.writeData(this);
	    		}
			}
		}
	    public NetPlayer getNetPlayer(String username) {
	    	for(NetPlayer p: this.Players) {
	    		if(p.getUsername().equalsIgnoreCase(username)) {
	    			return p;
	    		}
	    	}
	    	return null;
	    }
	    
	    public int getNetPlayerIndex(String username) {
	    	int index = 0;
	    	for(NetPlayer p: this.Players) {
	    		if(p.getUsername().equalsIgnoreCase(username)) {
	    			break;
	    		}
	    		index++;
	    	}
	    	return index;
	    }
		public void addConnection(NetPlayer player, Packet00Join packet) {
	    	boolean Connected = false;
	    	for(NetPlayer p : this.Players) {
	    		if(player.getUsername().equalsIgnoreCase(p.getUsername())) {
	    			if(p.ip == null) {
	    				p.ip = player.ip;
	    			}
	    			if(p.port == -1) {
	    				p.port = player.port;
	    			}
	    			Connected = true;
	    		}
	    		else {
	    			sendData(packet.getData(), p.ip, p.port);
	    			Packet00Join packetNew = new Packet00Join(p.getUsername(), p.x, p.y);
	    			sendData(packetNew.getData(),player.ip,player.port);
	    		}
	    
	    	}
	    	if(!Connected) {
	    		this.Players.add(player);
	    	}
	    }
		
	    private void handleMove(Packet02Movement packet) {
			if(getNetPlayer(packet.getUsername()) != null) {
				int index = getNetPlayerIndex(packet.getUsername());
			//	this.Players.get(index).moving = false;
				if(packet.getX() > this.Players.get(index).x) {
					this.Players.get(index).dir = 2;
					//this.Players.get(index).moving = true;
				}
				else if(packet.getX() < this.Players.get(index).x){
					this.Players.get(index).dir = 1;
					//this.Players.get(index).moving = true;
				}
				
			
				this.Players.get(index).moving = packet.getMoving();
				this.Players.get(index).x = packet.getX();
				this.Players.get(index).y = packet.getY();
				packet.writeData(this);
			}
		}
	    
	    public void sendData(byte[] data, InetAddress ipAddress, int port) {
	    	DatagramPacket packet;
	    	if(port != -1) {
	    		packet = new DatagramPacket(data, data.length, ipAddress, port);
	    	}
	    	else {
	    		packet = null;
	    	}
	        try {
	        if(packet != null)
	            this.socket.send(packet);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    public void sendDataToEveryClient(byte[] data) {
	    	for(NetPlayer i : Players) {
	    		sendData(data, i.ip, i.port);
	    	}
	    }
}

