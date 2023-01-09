import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Sumeet Kulkarni
 * @version 1.0
 * 
 *          This class defines the process node object in the Kahn Process
 *          Network. Each node contains a list of incoming FIFO channels, as
 *          well as a singular output channel, as is consistent with an open
 *          KPN. It also contains a string identifier. A node is considered
 *          "active" if there is nothing preventing it from executing. However,
 *          if it stalls due to a read dependency, it becomes inactive, or in a
 *          waiting state.
 * 
 *          The actual process method must be user-defined, which is why it is
 *          abstract. The user must create a subclass that extends this one, and
 *          define their own process method. Note that the method takes in an
 *          array of Objects - ensure to cast them to your desired data type
 *          before performing computations on them.
 *
 * @param <T> a generic which determines the type of data this node outputs
 */
public abstract class ProcessNode<T> {
	private List<FIFOChannel<T>> incomingChannels;
	private FIFOChannel<T> outgoingChannel;
	private String id;
	private boolean isActive;

	/**
	 * A constructor which sets the name/identifier of the node.
	 * 
	 * @param name the name/ID of the node
	 */
	public ProcessNode(String name) {
		id = name;
		incomingChannels = new ArrayList<FIFOChannel<T>>();
		outgoingChannel = new FIFOChannel<T>();
	}

	/**
	 * The abstract process method which must be user-defined. A process is any sort
	 * of computation, and the parameters can be of any number and data type.
	 * 
	 * @param params an abstract, generic list of Objects with variable length
	 * @return the result of the process
	 */
	public abstract List<T> process(Object... params);

	/**
	 * Returns the list of incoming FIFO channels.
	 * 
	 * @return the list of incoming FIFO channels
	 */
	public List<FIFOChannel<T>> getIncomingChannels() {
		return incomingChannels;
	}

	/**
	 * Adds a FIFO channel to the list of incoming FIFO channels.
	 * 
	 * @param f the FIFO channel to be added
	 */
	public void addIncomingChannel(FIFOChannel<T> f) {
		incomingChannels.add(f);
	}

	/**
	 * Returns the outgoing FIFO channel.
	 * 
	 * @return the outgoing FIFO channel
	 */
	public FIFOChannel<T> getOutgoingChannel() {
		return outgoingChannel;
	}

	/**
	 * Sets the outgoing FIFO channel
	 * 
	 * @param f the outgoing FIFO channel
	 */
	public void setOutgoingChannel(FIFOChannel<T> f) {
		this.outgoingChannel = f;
	}

	/**
	 * Returns whether this node is active; that is, whether this node is able to
	 * execute its process without any dependencies.
	 * 
	 * @return whether the node is active
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * Sets the isActive member.
	 * 
	 * @param a the new isActive state
	 */
	public void setActive(boolean a) {
		isActive = a;
	}

	/**
	 * Pops the requisite number of tokens from all of the incoming FIFO channels
	 * and executes the node's process, then adds the result(s) to the outgoing FIFO
	 * channel. This node is also set to active.
	 */
	public void addTokensToChannel() {
		setActive(true);
		Object[] params = new Object[getNumTotalAcceptedTokens()];
		int idx = 0;
		for (FIFOChannel<T> f : incomingChannels) {
			for (int i = 0; i < f.getDestAcceptAmt(); i++) {
				params[idx] = f.pop();
				idx++;
			}
		}
		List<T> tokens = process(params);
		for (T t : tokens) {
			outgoingChannel.push(t);
		}
	}

	/**
	 * Returns the total number of tokens required from all incoming channels to
	 * execute the process.
	 * 
	 * @return the total number of tokens required from all incoming channels to
	 *         execute the process
	 */
	private int getNumTotalAcceptedTokens() {
		int sum = 0;
		for (FIFOChannel<T> f : incomingChannels) {
			sum += f.getDestAcceptAmt();
		}
		return sum;
	}

	/**
	 * Returns a string representation of the node.
	 */
	public String toString() {
		return "Outgoing from " + id + ": " + outgoingChannel.toString();
	}
}
