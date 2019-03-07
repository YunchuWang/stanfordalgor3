package pa3Algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class PA3 {
	public int part1() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/huffman.txt"));
		int numSymbols = scanner.nextInt();
		PriorityQueue<Node> nodes = new PriorityQueue<Node>(new Comparator<Node>() {
			// sort by code frequency - increasing order
			@Override
			public int compare(Node o1, Node o2) {
				return (int) (o1._key - o2._key);
			}
		});

		// populate heap with nodes
		for (int i = 0; i < numSymbols; i++) {
			Node node = new Node(scanner.nextInt());
			nodes.add(node);
		}
		
		while(nodes.size() > 1) {
			Node lowestN = nodes.poll();
			Node sndlowestN = nodes.poll();
			Node mergedNode = new Node(lowestN._key + sndlowestN._key, sndlowestN, lowestN);
			nodes.add(mergedNode);
		}
		Node resNode = expandNode(nodes.poll());
		return minLength(resNode);
		
	}
	// compute the maximum weight independent set of a path graph
	public String part2() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/mwis.txt"));
		int numVertices = scanner.nextInt();
		int[] vertices = new int[numVertices + 1];
		long[] maxWeights = new long[numVertices + 1];
		Arrays.fill(maxWeights, -1);
		for(int i = 1; i <= numVertices; i++) {
			vertices[i] = scanner.nextInt();
		}
		List[] vertexStores = new ArrayList[numVertices + 1];
		for(int i = 0; i <= numVertices; i++) {
			vertexStores[i] = new ArrayList<Integer>();
		}
		computeMaxWeight(numVertices, maxWeights, vertices, vertexStores);
		StringBuilder resBuilder = new StringBuilder();
		int[] checkedVertices = new int[] {1,2,3,4,17,117,517,997};
		for(Integer checkedVertex : checkedVertices) {
			if(vertexStores[numVertices].contains(checkedVertex)) resBuilder.append("1");
			else resBuilder.append("0");
		}
		System.out.println(maxWeights[numVertices]);
		System.out.println(vertexStores[numVertices]);
		return resBuilder.toString();
	}
	private long computeMaxWeight(int index, long[] maxWeights, int[] vertices, List[] vertexStore) {
		if(index < 0) return 0;
		if(maxWeights[index] >= 0) return maxWeights[index];
		if(index == 0) {
			maxWeights[index] = 0;
			return 0;
		} else if(index == 1) {
			maxWeights[index] = vertices[index];
			vertexStore[index].add(index);
			return maxWeights[index];
		}
		long includeCurVertexWeight = computeMaxWeight(index - 2, maxWeights, vertices,  vertexStore) + vertices[index];
		long excludeCurVertexWeight = computeMaxWeight(index - 1, maxWeights, vertices,  vertexStore);
		if(excludeCurVertexWeight < includeCurVertexWeight) {
			vertexStore[index].addAll(vertexStore[index - 2]);
			vertexStore[index].add(index);
		} else {
			vertexStore[index].addAll(vertexStore[index - 1]);
		}
		maxWeights[index] = Math.max(excludeCurVertexWeight, includeCurVertexWeight);
		return maxWeights[index];
	}
	private Node expandNode(Node node) {
		if(node._left == null && node._right == null) return node;
		node._key = 0;
		node._left = expandNode(node._left);
		node._right = expandNode(node._right);
		return node;
	}
	private int maxLength(Node node) {
		if(node == null) return -1;
		return Math.max(maxLength(node._left), maxLength(node._right)) + 1;
	}
	private int minLength(Node node) {
		if(node == null) return -1;
		if(node._key != 0) return 0;
		return Math.min(minLength(node._left), minLength(node._right)) + 1;
	}
}

class Node {
	long _key;
	public Node _left = null;
	public Node _right = null;

	public Node() {
	}

	public Node(long _key, Node _left, Node _right) {
		this._key = _key;
		this._left = _left;
		this._right = _right;
	}

	public Node(long key) {
		this._key = key;
	}

}
