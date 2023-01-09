import java.util.ArrayList;
import java.util.List;

public class ExampleNode extends ProcessNode<Integer>{

	public ExampleNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public List<Integer> process(Object...params) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0; i < params.length; i++) {
			sum+=(int)params[i];
		}
		List<Integer> out = new ArrayList<Integer>();
		out.add(sum);
		
		return out;
	}

}
