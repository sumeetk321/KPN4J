import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 
 * @author Sumeet Kulkarni This class defines the first-in-first-out channel
 *         object. It is essentially a wrapper around a Queue object, but also
 *         defines the amount of tokens that the source outputs, as well as the
 *         amount of tokens that the destination consumes to perform its
 *         computation.
 * @version 1.0
 * @param <T> a generic type which determines the data type in the channel
 */
public class FIFOChannel<T> {

	private int sourceProduceAmt;
	private int destAcceptAmt;
	private Queue<T> channelQueue;

	/**
	 * Constructor that sets the source production amount and the destination
	 * consumption amount.
	 * 
	 * @param sourceProduceAmt the amount of tokens that the source node produces
	 *                         after executing a process
	 * @param destAcceptAmt    the amount of tokens the destination node requires
	 *                         from this channel to complete its computation
	 */
	public FIFOChannel(int sourceProduceAmt, int destAcceptAmt) {
		this.sourceProduceAmt = sourceProduceAmt;
		this.destAcceptAmt = destAcceptAmt;
		channelQueue = new ArrayDeque<T>();
	}

	/**
	 * Default constructor. Sets source production amount and destination
	 * consumption amount to 0.
	 * 
	 * @see FIFOChannel#FIFOChannel(int, int)
	 */
	public FIFOChannel() {
		sourceProduceAmt = 0;
		destAcceptAmt = 0;
		channelQueue = new ArrayDeque<T>();
	}

	/**
	 * Returns the token at the front of the channel.
	 * 
	 * @return the token at the front of the channel
	 */
	public T pop() {
		return channelQueue.remove();
	}

	/**
	 * Adds a token to the end of the channel.
	 * 
	 * @param token the token to be added
	 */
	public void push(T token) {
		channelQueue.add(token);
	}

	/**
	 * Returns the size of the channel.
	 * 
	 * @return the size of the channel
	 */
	public int size() {
		return channelQueue.size();
	}

	/**
	 * Returns the amount of tokens the source node produces after each process
	 * execution.
	 * 
	 * @return the amount of tokens the source node produces after each process
	 *         execution
	 */
	public int getSourceProduceAmt() {
		return sourceProduceAmt;
	}

	/**
	 * Sets the amount of tokens the source node produces after each process
	 * execution.
	 * 
	 * @param sourceProduceAmt the amount of tokens the source node produces after
	 *                         each process execution
	 */
	public void setSourceProduceAmt(int sourceProduceAmt) {
		this.sourceProduceAmt = sourceProduceAmt;
	}

	/**
	 * Returns the amount of tokens the destination node needs to execute its
	 * process.
	 * 
	 * @return the amount of tokens the destination node needs to execute its
	 *         process
	 */
	public int getDestAcceptAmt() {
		return destAcceptAmt;
	}

	/**
	 * Sets the amount of tokens the destination node needs to execute its process.
	 * 
	 * @param destAcceptAmt the amount of tokens the destination node needs to
	 *                      execute its process
	 */
	public void setDestAcceptAmt(int destAcceptAmt) {
		this.destAcceptAmt = destAcceptAmt;
	}

	/**
	 * Returns a string representation of the channel object. Namely, it prints each
	 * token in the queue, assuming it has a defined toString() function itself.
	 */
	public String toString() {
		String s = "";
		for (T t : channelQueue) {
			s += t.toString() + " ";
		}
		return s.trim();
	}

}
