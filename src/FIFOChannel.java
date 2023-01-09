import java.util.ArrayDeque;
import java.util.Queue;

public class FIFOChannel<T> {
	
	private int sourceProduceAmt;
	private int destAcceptAmt;
	private Queue<T> channelQueue;
	
	public FIFOChannel(int sourceProduceAmt, int destAcceptAmt) {
		this.sourceProduceAmt = sourceProduceAmt;
		this.destAcceptAmt = destAcceptAmt;
		channelQueue = new ArrayDeque<T>();
	}
	
	public FIFOChannel() {
		sourceProduceAmt = 0;
		destAcceptAmt = 0;
		channelQueue = new ArrayDeque<T>();
	}
	
	public T pop() {
		return channelQueue.remove();
	}
	
	public void push(T token) {
		channelQueue.add(token);
	}

	public int getSourceProduceAmt() {
		return sourceProduceAmt;
	}

	public void setSourceProduceAmt(int sourceProduceAmt) {
		this.sourceProduceAmt = sourceProduceAmt;
	}

	public int getDestAcceptAmt() {
		return destAcceptAmt;
	}

	public void setDestAcceptAmt(int destAcceptAmt) {
		this.destAcceptAmt = destAcceptAmt;
	}

	public String toString() {
		String s = "";
		for(T t : channelQueue) {
			s+=t.toString() + " ";
		}
		return s.trim();
	}
	
}
