package global;

public class ItemSaveInfo {
	private int index=-1;
	private int hashCode=0;
	
	public ItemSaveInfo(int index,int hashCode) {
		this.index=index;
		this.hashCode=hashCode;
	}

	public int getIndex() {
		return index;
	}

	public int getHashCode() {
		return hashCode;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

}
