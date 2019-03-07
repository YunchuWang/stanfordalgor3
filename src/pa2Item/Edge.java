package pa2Item;

import pa2Item.Node;

public class Edge {
	private int cost = 0;
	private Node sNode, eNode;
	public Edge(int cost, Node sNode, Node eNode) {
		this.cost = cost;
		this.sNode = sNode;
		this.eNode = eNode;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public Node getsNode() {
		return sNode;
	}
	public void setsNode(Node sNode) {
		this.sNode = sNode;
	}
	public Node geteNode() {
		return eNode;
	}
	public void seteNode(Node eNode) {
		this.eNode = eNode;
	}
	
}
