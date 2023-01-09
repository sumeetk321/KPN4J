
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KahnProcessNetwork<Integer> k = new KahnProcessNetwork<Integer>();
		
		ExampleNode<Integer> a = new ExampleNode<Integer>("A");
		ExampleNode<Integer> b = new ExampleNode<Integer>("B");
		ExampleNode<Integer> c = new ExampleNode<Integer>("C");
		
		FIFOChannel<Integer> aChannel = new FIFOChannel<Integer>(1, 1);
		aChannel.push(1);
		aChannel.push(4);
		a.addIncomingChannel(aChannel);
		
		FIFOChannel<Integer> bChannel = new FIFOChannel<Integer>(1, 1);
		bChannel.push(8);
		bChannel.push(9);
		b.addIncomingChannel(bChannel);
		
		k.addProcessNode(a);
		k.addProcessNode(b);
		k.addProcessNode(c);
		k.addFIFOChannel(a, c, 1, 1);
		k.addFIFOChannel(b, c, 1, 1);
		
		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());

		k.simulateTimestep();

		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
	}

}
