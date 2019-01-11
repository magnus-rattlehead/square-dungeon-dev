package squaredungeon.net;

import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Mob;
import squaredungeon.gameObjects.NetPlayer;
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

public class GameClient extends Thread{
	   private InetAddress ipAddress;
	    private DatagramSocket socket;
	    private Main main;

	    public GameClient(Main main, String ipAddress) {
	        this.main = main;
	        try {
	            this.socket = new DatagramSocket();
	            this.ipAddress = InetAddress.getByName(ipAddress);
	        } catch (SocketException e) {
	            e.printStackTrace();
	        } catch (UnknownHostException e) {
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
	    		handleJoin((Packet00Join) packet, ip, port);
	    		break;
	    	case DISCONNECT:
	    		packet = new Packet01Disconnect(data);
	    		System.out.println(ip.getHostAddress() + "/" +port +"   "+ ((Packet01Disconnect) packet).getUsername() + " went sicko mode!");
	    		List<Mob> NetPlayertoRemove = new ArrayList<Mob>();
	    		for(Mob m : main.handler.mob) {
	    			if(m instanceof NetPlayer && ((NetPlayer) m).getUsername().equals(((Packet01Disconnect) packet).getUsername())) {
	    				NetPlayertoRemove.add(m);
	    			}
	    		}
	    		main.handler.mob.removeAll(NetPlayertoRemove);
	    		break;
	    	case MOVE:
	    		packet = new Packet02Movement(data);
	    		handleMove((Packet02Movement) packet);
	    		break;
	    	case MOBMOVE:
	    		packet = new Packet03MobMovement(data, main.handler);
	    		handleMoveMob(((Packet03MobMovement) packet));
	    		break;
	    	}
		}
	    private void handleMoveMob(Packet03MobMovement packet) {
	    
	    	for(int i = 0; i < main.handler.mob.size(); i++) {
	    		Mob m = main.handler.mob.get (i);
	    	//	
	    		if(m.mobID != null && Integer.parseInt(m.mobID) == Integer.parseInt(packet.getMobID().trim())) {
	    			
	    			//System.out.println(m.mobID + ", " + packet.getMobID() + ", " + m.getId());
					if(packet.getX() > m.x) {
						m.dir = 1;
					}
					else if(packet.getX() < m.x){
						m.dir = 2;
					}
					m.x = packet.getX();
					m.y = packet.getY();
			
			}
    		continue;
	    	}
		}
	    
	    private void handleMove(Packet02Movement packet) {
	    	for(Mob m : main.handler.mob) {
    			if(m instanceof NetPlayer && ((NetPlayer) m).getUsername().equals(((Packet02Movement) packet).getUsername())) {
    				if(packet.getX() > m.x) {
    					((NetPlayer)m).dir = 1;
    				}
    				if(packet.getX() < m.x) {
    					((NetPlayer)m).dir = 2;
    				}
    				m.x = packet.getX();
    				m.y = packet.getY();
    			
    			}
    		}
		}
	    
	    private void handleJoin(Packet00Join packet, InetAddress ip, int port) {
	    	
    		System.out.println(ip.getHostAddress() + "/" +port +"   "+ ((Packet00Join) packet).getUsername()+ " has connected...");
    		
    		NetPlayer player = new NetPlayer(packet.getX(),packet.getY(), ID.PLAYER, main.ssMob, 100, packet.getUsername(), ip, port);
    		main.handler.addMob(player);
	    }

		public void sendData(byte[] data) {
	        DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
	        try {
	            socket.send(packet);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	}