import java.util.ArrayList;
import java.util.List;

public class ExampleNode<Integer> extends ProcessNode<Integer>{

	public ExampleNode(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Integer> process(Integer...params) {
		// TODO Auto-generated method stub
		int sum = 0;
		for(int i = 0; i < params.length; i++) {
			sum+=(int)params[i];
		}
		ArrayList<Integer> out = new ArrayList<Integer>();
		out.add(Integer.valueOf(sum));
		return out;
	}

}
