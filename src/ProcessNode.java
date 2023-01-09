import java.util.List;

public abstract class ProcessNode<T> {
	private List<FIFOChannel<T>> incomingChannels;
	private FIFOChannel<T> outgoingChannel;
	private String id;
	
	public ProcessNode(String name) {
		id = name;
	}
	
	public abstract T[] process(Object...params);
	
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
		Object[] params = new Object[incomingChannels.size()];
		int idx = 0;
		for(FIFOChannel<T> f : incomingChannels) {
			for(int i = 0; i < f.getDestAcceptAmt(); i++) {
				params[idx] = f.pop();
				idx++;
			}
		}
		T[] tokens = process(params);
		for(T t : tokens) {
			outgoingChannel.push(t);
		}
	}
	
	public String toString() {
		return "Outgoing from " + id + ": " + outgoingChannel.toString();
	}
}
