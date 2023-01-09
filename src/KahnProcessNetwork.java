import java.util.HashSet;

public class KahnProcessNetwork<T> {
	private HashSet<ProcessNode<T>> nodeSet;
	
		
	public void addProcessNode(ProcessNode<T> node) {
		nodeSet.add(node);
	}
		
	public void addFIFOChannel(ProcessNode<T> source, ProcessNode<T> dest, int sourceProduceAmt, int destAcceptAmt) {
		FIFOChannel<T> f = new FIFOChannel<T>(sourceProduceAmt, destAcceptAmt);
		dest.addIncomingChannel(f);
		source.setOutgoingChannel(f);
		
	}
}
