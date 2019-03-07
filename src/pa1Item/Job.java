package pa1Item;

public class Job {
	private int weight = 0;
	private int length = 0;

	public Job(int weight, int length) {
		this.weight = weight;
		this.length = length;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

}
