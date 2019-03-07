package pa2Algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import pa2Item.Edge;
import pa2Item.Node;
import org.jgrapht.alg.util.*;

public class PA2 {
	// K clustering max-spacing algorithm based on union-find Krusal mst algorithm
	public long part1() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/clustering_1.txt"));
		int numNodes = scanner.nextInt();
		Node[] nodes = new Node[numNodes + 1];
		PriorityQueue<Edge> pq = new PriorityQueue<Edge>(new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.getCost() - o2.getCost();
			}
			
		});
		for (int i = 1; i < nodes.length; i++) {
			nodes[i] = new Node(i, i);
		}
		while(scanner.hasNextLine()) {
			Node sNode = nodes[scanner.nextInt()];
			Node eNode = nodes[scanner.nextInt()];
			int edgeCost = scanner.nextInt();
			Edge edge = new Edge(edgeCost, sNode, eNode);
//			System.out.println("sNode " + edge.getsNode().getIndex() + " eNode " + 
//			edge.geteNode().getIndex() + " cost " + edge.getCost());
			pq.add(edge);
		}
		
		int numGroups = numNodes;
		Map<Integer, List<Node>> gMapping = new HashMap<>();
		for (int i = 1; i <= numGroups; i++) {
			List<Node> list = new ArrayList<Node>();
			list.add(nodes[i]);
			gMapping.put(i, list);
		}
		while (numGroups > 4) {
			Edge edge = pq.poll();
			System.out.println("sNode " + edge.getsNode().getIndex() + " eNode " + 
			edge.geteNode().getIndex() + " cost " + edge.getCost());
			
			if(edge == null) return 0;
			Node sNode = edge.getsNode();
			Node eNode = edge.geteNode();
			if (sNode.isSameGroup(eNode))
				continue;
			List<Node> sList = gMapping.get(sNode.getgIndex());
			List<Node> eList = gMapping.get(eNode.getgIndex());
			if (sList.size() < eList.size()) {
				int oldIndex = sNode.getgIndex();
				for(Node node : sList) {
					node.setgIndex(eNode.getgIndex());
					eList.add(node);
				}
				gMapping.remove(oldIndex);
				gMapping.put(eNode.getgIndex(), eList);
			} else {
				int oldIndex = eNode.getgIndex();
				for(Node node : eList) {
					node.setgIndex(sNode.getgIndex());
					sList.add(node);
				}
				gMapping.remove(oldIndex);
				gMapping.put(sNode.getgIndex(), sList);
			}
			numGroups--;
		}
		for(Entry<Integer, List<Node>> entry : gMapping.entrySet()) {
			System.out.println(entry.getValue().size());
		}
		System.out.println(gMapping.size() == 4);
		while(!pq.isEmpty()) {
			Edge edge = pq.poll();
			Node sNode = edge.getsNode();
			Node eNode = edge.geteNode();
			if (!sNode.isSameGroup(eNode))
				return edge.getCost();
		}
		return 0;
	}
	public long part2() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/clustering_big.txt"));
		int numNodes = scanner.nextInt();
		int numBits = scanner.nextInt();
		Set<BitSet> vertexes = new HashSet<BitSet>();
		UnionFind<BitSet> uf = new UnionFind(new HashSet<BitSet>());
		Map<BitSet, List<BitSet>> gMapping = new HashMap<>();
		for(int i = 0; i < numNodes; i++) {
			BitSet bs = new BitSet(numBits);
			for(int j = 0; j < numBits; j++) {
				if(scanner.nextInt() == 1) bs.set(j);
			}
			vertexes.add(bs);
		}
		for(BitSet vertex : vertexes) {
			if(!gMapping.containsKey(vertex)) {
				addCloseNodes(vertex, gMapping, numBits);
				uf.addElement(vertex);
			} else {
				uf.addElement(vertex);
				for(BitSet gVertex : gMapping.get(vertex)) {
					uf.union(vertex, gVertex);
				}
				addCloseNodes(vertex, gMapping, numBits);
			}
		}
		return uf.numberOfSets();
		
	}
	public static void addCloseNodes(BitSet bitSet, Map<BitSet, List<BitSet>> map,int numOfBits) {
	    insertIfExist(bitSet, bitSet, map);
	    for (int i = 0; i < numOfBits; i++) {
	        BitSet bs = (BitSet) bitSet.clone();
	        bs.flip(i);
	        insertIfExist(bitSet, bs, map);
	    }

	    for (int i = 0; i < numOfBits; i++) {
	        for (int j = i+1; j < numOfBits; j++) {
	            BitSet bs = (BitSet) bitSet.clone();
	            bs.flip(i);
	            bs.flip(j);
	            insertIfExist(bitSet, bs, map);
	        }
	    }
	}
	public static void insertIfExist(BitSet oldSet, BitSet bitSet, Map<BitSet, List<BitSet>> map) {
	    if (!map.containsKey(bitSet)) {
	    	List<BitSet> groups = new ArrayList<>();
	    	groups.add(oldSet);
	    	map.put(bitSet, groups);
	    } else {
	    	List<BitSet> groups = map.get(bitSet);
	    	groups.add(oldSet);
	    	map.put(bitSet, groups);
	    }
	}
}
