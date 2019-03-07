package pa1Algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import pa1Item.Job;
import pa1Item.Node;

public class PA1 {
	public long PAPart1() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/jobs.txt"));
		int numOfJobs = scanner.nextInt();
		
		long res = 0;
		Job[] jobs = new Job[numOfJobs];
		for(int i = 0; i < numOfJobs; i++) {
			Job job = new Job(scanner.nextInt(), scanner.nextInt());
			jobs[i] = job;
		}
		Arrays.sort(jobs, new Comparator<Job>() {
			@Override
			public int compare(Job j1, Job j2) {
				int j1Diff = j1.getWeight() - j1.getLength();
				int j2Diff = j2.getWeight() - j2.getLength();
				if(j1Diff == j2Diff) {
					return j2.getWeight() - j1.getWeight();
				}
				return j2Diff - j1Diff;
			}
		});
		int cumLength = 0;
		for(Job job : jobs) {
			cumLength += job.getLength();
			res += job.getWeight()*cumLength;
		}
		return res;
	}
	public long PAPart2() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/jobs.txt"));
		int numOfJobs = scanner.nextInt();
		
		long res = 0;
		Job[] jobs = new Job[numOfJobs];
		for(int i = 0; i < numOfJobs; i++) {
			Job job = new Job(scanner.nextInt(), scanner.nextInt());
			jobs[i] = job;
		}
		Arrays.sort(jobs, new Comparator<Job>() {
			@Override
			public int compare(Job j1, Job j2) {
				double j1Diff = (double) j1.getWeight() / j1.getLength();
				double j2Diff = (double) j2.getWeight() / j2.getLength();
				return j2Diff > j1Diff? 1 : -1;
			}
		});
		int cumLength = 0;
		for(Job job : jobs) {
			cumLength += job.getLength();
			res += job.getWeight()*cumLength;
		}
		return res;
	}
	public long PAPart3() throws FileNotFoundException {
		//-3612829
		Scanner scanner = new Scanner(new File("./resc/edges.txt"));
		int numOfNodes = scanner.nextInt();
		long numOfEdges = scanner.nextInt();
		long minCost = 0;
		PriorityQueue<Node> queue = new PriorityQueue<Node>(new Comparator<Node>() {
			@Override			public int compare(Node n1, Node n2) {
				 if(n1.getKey()  > n2.getKey()) return 1;
				 else if(n1.getKey() < n2.getKey()) return -1;
				 return 0;
			}
		});
		
		List<HashMap<Node, Integer>> adjList = new ArrayList<HashMap<Node, Integer>>();
		for(int i = 0; i <= numOfNodes; i++) {
			adjList.add(new HashMap<Node, Integer>());
		}
		Set<Node> visited = new HashSet<Node>();
		Node[] nodes = new Node[numOfNodes + 1];
		for(int i = 0; i < numOfEdges; i++) {
			int n1Index = scanner.nextInt();
			int n2Index = scanner.nextInt();
			int edgeCost = scanner.nextInt();
			if(nodes[n1Index] == null) {
				Node n1 = new Node(n1Index);
				queue.add(n1);
				nodes[n1Index] = n1;
			}
			if(nodes[n2Index] == null) {
				Node n2 = new Node(n2Index);
				queue.add(n2);
				nodes[n2Index] = n2;
			}
			adjList.get(n1Index).put(nodes[n2Index], edgeCost);
			adjList.get(n2Index).put(nodes[n1Index], edgeCost);
		}
		if(queue.isEmpty()) return 0;
		queue.remove(nodes[1]);
		nodes[1].setKey(0);
		queue.add(nodes[1]);
		while(visited.size() != numOfNodes) {
			Node curNode = queue.remove();
			minCost += curNode.getKey();
			visited.add(curNode);
			Map<Node, Integer> neighbors = adjList.get(curNode.getIndex());
			for(Map.Entry<Node, Integer> neighbor : neighbors.entrySet()) {
				Node adjNode = neighbor.getKey();
				if(visited.contains(adjNode)) 
					continue;
				int edgeCost = neighbor.getValue();
				queue.remove(adjNode);
				if(adjNode.getKey() > (edgeCost)) {
					adjNode.setKey(edgeCost);
				}
				queue.add(adjNode);
			}
		}
		return minCost;
	}
}
