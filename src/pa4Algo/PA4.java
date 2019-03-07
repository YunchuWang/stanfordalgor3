package pa4Algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.jgrapht.alg.util.Pair;

public class PA4 {
	public long part1() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/knapsack1.txt"));
		int totalWeight = scanner.nextInt();
		int numItems = scanner.nextInt();
		int[] weights = new int[numItems];
		int[] values = new int[numItems];
		for (int i = 0; i < numItems; i++) {
			values[i] = scanner.nextInt();
			weights[i] = scanner.nextInt();
		}
		int K[][] = new int[numItems + 1][totalWeight + 1];
		for(int i = 0; i <= numItems; i++) {
			for(int j = 0; j <= totalWeight; j++) {
				if (i==0 || j==0) 
					K[i][j] = 0;
				else if(j - weights[i - 1] < 0)
					K[i][j] = K[i - 1][j];
				else
					K[i][j] = Math.max(K[i - 1][j], K[i - 1][j - weights[i - 1]] + values[i - 1]);
			}
		}
		return K[numItems][totalWeight];
	}
	public long part2() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("./resc/knapsack_big.txt"));
		int totalWeight = scanner.nextInt();
		int numItems = scanner.nextInt();
		int[] weights = new int[numItems];
		int[] values = new int[numItems];
		for (int i = 0; i < numItems; i++) {
			values[i] = scanner.nextInt();
			weights[i] = scanner.nextInt();
		}
		Map<Pair<Integer,Integer>, Integer> mapping = new HashMap();
		return knapSackHelper(values, weights, numItems, totalWeight, mapping);
	}
	private int knapSackHelper(int[] values, int[] weights, int iIndex, int wIndex, Map<Pair<Integer,Integer>, Integer> K) {
		Pair key = new Pair(iIndex, wIndex);
		if(iIndex == 0 || wIndex == 0) {
			K.put(key, 0);
		} else if(wIndex - weights[iIndex - 1] < 0) {
			K.put(key, knapSackHelper(values, weights, iIndex - 1, wIndex, K));
		} else if(!K.containsKey(key)){
			K.put(key, Math.max(knapSackHelper(values, weights, iIndex - 1, wIndex, K), 
					knapSackHelper(values, weights, iIndex - 1, wIndex - weights[iIndex - 1], K) + values[iIndex - 1]));
		} 
		return K.get(key);
	}
}
