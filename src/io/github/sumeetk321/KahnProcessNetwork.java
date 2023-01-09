package io.github.sumeetk321;

import java.util.HashSet;

/**
 * 
 * 
 * 
 * The root class for Kahn Process Network construction. A KPN object simply
 * contains a set of process nodes, each of which contain their own set of
 * first-in-first-out channels that send and receive tokens (data).
 * 
 * Each process node will read a certain number of tokens and perform some
 * user-defined function (through polymorphism), which must be deterministic
 * (e.g., given some initial input, the process gives the same output each
 * time).
 * 
 * Reading from channels is blocked; that is, if a process node does not have a
 * sufficient number of tokens to perform its process, it will stall. Writing to
 * a channel, however, is not blocked - it will always succeed. Keep this in
 * mind when constructing your own models; reading dependencies can cascade and
 * lead to overflows or deadlocks that disrupt the whole network.
 * 
 * Note that this KPN class defines an *open* KPN, in which each process node
 * contains at least one input channel and exactly one output channel.
 * 
 * 
 * @author Sumeet Kulkarni
 * @param <T> a generic which defines the type of each "atom" or "token" in the
 *            KPN.
 */
public class KahnProcessNetwork<T> {
	private HashSet<ProcessNode<T>> nodeSet;

	/**
	 * Default constructor. Initializes the empty set of nodes.
	 */
	public KahnProcessNetwork() {
		nodeSet = new HashSet<ProcessNode<T>>();
	}

	/**
	 * Adds a process node to the network.
	 * 
	 * @param node the node to be added
	 */
	public void addProcessNode(ProcessNode<T> node) {
		nodeSet.add(node);
	}

	/**
	 * Adds a first-in-first-out channel between two nodes to the network. This
	 * channel will send tokens from the source node to the destination node.
	 * 
	 * @param source           the source node from which the channel starts
	 * @param dest             the destination node where the channel ends
	 * @param sourceProduceAmt the amount of tokens the source node produces each
	 *                         time its process is executed
	 * @param destAcceptAmt    the amount of tokens the destination node consumes to
	 *                         execute its process
	 */
	public void addFIFOChannel(ProcessNode<T> source, ProcessNode<T> dest, int sourceProduceAmt, int destAcceptAmt) {
		FIFOChannel<T> f = new FIFOChannel<T>(sourceProduceAmt, destAcceptAmt);
		dest.addIncomingChannel(f);
		source.setOutgoingChannel(f);

	}

	/**
	 * Same as
	 * {@link KahnProcessNetwork#addFIFOChannel(ProcessNode, ProcessNode, int, int)},
	 * except sourceProduceAmt and destAcceptAmt are set by default to 1.
	 * 
	 * @see KahnProcessNetwork#addFIFOChannel(ProcessNode, ProcessNode, int, int)
	 * @param source the source node from which the channel starts
	 * @param dest   the destination node where the channel ends
	 */
	public void addFIFOChannel(ProcessNode<T> source, ProcessNode<T> dest) {
		addFIFOChannel(source, dest, 1, 1);
	}

	/**
	 * Simulates a single time step in the execution of the network. Nodes that do
	 * not have the tokens required in any channel to perform their process stall
	 * and do not execute, rendering them inactive.
	 */
	public void simulateTimestep() {
		nodeLoop: for (ProcessNode<T> p : nodeSet) {
			for (FIFOChannel<T> f : p.getIncomingChannels()) {
				if (f.size() < f.getDestAcceptAmt()) {
					p.setActive(false);
					continue nodeLoop;
				}
			}
			p.addTokensToChannel();
		}
	}

	/**
	 * Returns a string representation of the network at the current time step.
	 */
	public String toString() {
		String s = "";
		for (ProcessNode<T> p : nodeSet) {
			s += p.toString() + "\n";
		}
		return s;
	}
}
