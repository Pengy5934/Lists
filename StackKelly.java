
public class StackKelly<E> extends LinkedListKelly<E>
{
	public E pop()
	{
		if (super.isDefined())
		{
			E e = super.getLast();
			super.removeLast();
			return e;
		}
		else
			return null;
	}
	
	public E peek()
	{
		return super.getLast();
	}
	
	public boolean push(E e)
	{
		super.addLast(e);
		if (peek() != null && peek().equals(e))
			return true;
		return false;
	}
}

