import java.util.ArrayList;
import java.util.List;

public abstract class ProcessNode<T> {
	private List<FIFOChannel<T>> incomingChannels;
	private FIFOChannel<T> outgoingChannel;
	private String id;
	
	public ProcessNode(String name) {
		id = name;
		incomingChannels = new ArrayList<FIFOChannel<T>>();
		outgoingChannel = new FIFOChannel<T>();
	}
	
	public abstract List<T> process(T...params);
	
	public void addIncomingChannel(FIFOChannel<T> f) {
		incomingChannels.add(f);
	}
	
	public FIFOChannel<T> getOutgoingChannel() {
		return outgoingChannel;
	}
	
	public void setOutgoingChannel(FIFOChannel<T> f) {
		this.outgoingChannel = f;
	}
	
	
	public void addTokensToChannel() {
		T[] params = (T[]) new Object[incomingChannels.size()];
		int idx = 0;
		for(FIFOChannel<T> f : incomingChannels) {
			for(int i = 0; i < f.getDestAcceptAmt(); i++) {
				params[idx] = f.pop();
				idx++;
			}
		}
		List<T> tokens = process(params);
		for(T t : tokens) {
			outgoingChannel.push(t);
		}
	}
	
	public String toString() {
		return "Outgoing from " + id + ": " + outgoingChannel.toString();
	}
}
