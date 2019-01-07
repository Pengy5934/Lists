
public class QueueKelly<E> extends LinkedListKelly<E>
{	
	public E pop()
	{
		if (super.isDefined())
		{
			E e = super.getFirst();
			super.removeFirst();
			return e;
		}
		else
			return null;
	}
	
	public E peek()
	{
		return super.getFirst();
	}
	
	public boolean push(E e)
	{
		if (e != null)
		{
			super.addLast(e);
			if (peek().equals(e))
				return true;
			return false;
		}
		else
			return false;
	}
}

