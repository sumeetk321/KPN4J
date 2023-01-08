
public abstract class ProcessNode<T> {
	private T data;
	
	
	public abstract T process(T...params);
}
