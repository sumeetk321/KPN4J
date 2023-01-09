import java.util.HashSet;

public class KahnProcessNetwork<T> {
	private HashSet<ProcessNode<T>> nodeSet;
	
	public KahnProcessNetwork() {
		nodeSet = new HashSet<ProcessNode<T>>();
	}
	
	public void addProcessNode(ProcessNode<T> node) {
		nodeSet.add(node);
	}
		
	public void addFIFOChannel(ProcessNode<T> source, ProcessNode<T> dest, int sourceProduceAmt, int destAcceptAmt) {
		FIFOChannel<T> f = new FIFOChannel<T>(sourceProduceAmt, destAcceptAmt);
		dest.addIncomingChannel(f);
		source.setOutgoingChannel(f);
		
	}
	
	public void simulateTimestep() {
		for(ProcessNode<T> p : nodeSet) {
			p.addTokensToChannel();
		}
	}
}
