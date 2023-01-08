
public class FIFOChannel {
	ProcessNode<?> sourceNode;
	ProcessNode<?> destNode;
	int sourceProduce;
	int destAccept;
	public FIFOChannel(ProcessNode<?> source, ProcessNode<?> dest, int sourceProduceAmt, int destAcceptAmt) {
		sourceNode = source;
		destNode = dest;
		sourceProduce = sourceProduceAmt;
		destAccept = destAcceptAmt;
	}
}
