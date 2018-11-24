package squaredungeon.main;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import squaredungeon.gameObjects.ID;
import squaredungeon.gameObjects.Tile;

public class PathFinder {
	private Handler handler;
	public PathFinder(Handler handler) {
		this.handler = handler;
	}
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if(n1.fCost < n0.fCost) return +1;
			if(n1.fCost > n0.fCost) return -1;
			return 0;
		}
		
	};
	
	public List<Node> findPath(Vector2i start, Vector2i goal){
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node currentNode = new Node(start, null, 0, getDistance(start, goal));
		openList.add(currentNode);
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			currentNode = openList.get(0);
			if(currentNode.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while(currentNode.parent != null) {
					path.add(currentNode);
					currentNode = currentNode.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(currentNode);
			closedList.add(currentNode);
			for(int i = 0; i < 9; i++) {
				if(i == 4) continue;
				int x = currentNode.tile.getX();
				int y = currentNode.tile.getY();
				int xi = ((i % 3) - 1);
				int yi = ((i / 3) - 1);
				boolean at = getTile(x + xi, y + yi);
				if(at) continue;
				Vector2i a = new Vector2i(x+xi, y+yi);
				double gCost = currentNode.gCost + (getDistance(currentNode.tile, a) == 1 ? 1 : 0.95);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, currentNode, gCost, hCost);
				if(vectorInList(closedList, a) && gCost >= node.gCost) continue;
				if(!vectorInList(openList, a) || gCost < node.gCost) openList.add(node);
			}
		}
		closedList.clear();
		return null;
	}
	
	private boolean vectorInList (List<Node> list, Vector2i vector) {
		for(Node n : list) {
			if(n.tile.equals(vector)) return true;
		}
		return false;
	}
	
	private boolean getTile(int x, int y) {
		//if(x<0 || x>Main.WIDTH || y<0 || y > Main.HEIGHT) return true;
	for (int i = 0; i < handler.tile.size(); i++) {
		Tile tempTile = handler.tile.get(i);
		if(tempTile.getId() == ID.Block) {
		if(new Rectangle(x << 5,y << 5,32,32).intersects(tempTile.getBounds())) {
			return true;
		}
		}
	}
	return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		return Math.hypot(tile.getX()-goal.getX(), tile.getY()-goal.getY());
	}
	
}
