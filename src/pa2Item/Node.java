package pa2Item;


public class Node {
	private int index = -1;
	private int gIndex = -1;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (index != other.index)
			return false;
		return true;
	}

	public int getgIndex() {
		return gIndex;
	}

	public void setgIndex(int gIndex) {
		this.gIndex = gIndex;
	}

	public Node(int index, int gIndex) {
		this.index = index;
		this.gIndex = gIndex;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public boolean isSameGroup(Node other) {
		return gIndex == other.getgIndex();
	}

}
