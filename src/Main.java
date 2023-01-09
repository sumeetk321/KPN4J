
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		KahnProcessNetwork<Integer> k = new KahnProcessNetwork<Integer>();
		
		ExampleNode a = new ExampleNode("A");
		ExampleNode b = new ExampleNode("B");
		ExampleNode c = new ExampleNode("C");
		
		FIFOChannel<Integer> aChannel = new FIFOChannel<Integer>(1, 1);
		aChannel.push(1);
		aChannel.push(4);
		a.addIncomingChannel(aChannel);
		
		FIFOChannel<Integer> bChannel = new FIFOChannel<Integer>(1, 1);
		bChannel.push(8);
		bChannel.push(9);
		b.addIncomingChannel(bChannel);
		
		FIFOChannel<Integer> cChannel = new FIFOChannel<Integer>(1, 1);
		cChannel.push(7);
		cChannel.push(2);
		c.addIncomingChannel(cChannel);
		
		k.addProcessNode(a);
		k.addProcessNode(b);
		k.addProcessNode(c);
		k.addFIFOChannel(a, c, 1, 1);
		k.addFIFOChannel(b, c, 1, 1);
		k.addFIFOChannel(c, b, 1, 1);
		
		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());

		k.simulateTimestep();

		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
		
		k.simulateTimestep();

		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
		
		k.simulateTimestep();

		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
		
		k.simulateTimestep();

		System.out.println(a.toString());
		System.out.println(b.toString());
		System.out.println(c.toString());
		
	}

}
