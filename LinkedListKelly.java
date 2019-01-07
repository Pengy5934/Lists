//Billy Kelly
//LinkedList
//11.12.2018

/*----------------------------------------------------------------------//
//
//							CLASS LINKEDLISTKELLY
//
//----------------------------------------------------------------------*/

public class LinkedListKelly<E>
{
	//---------------------------------<GLOBAL VARIABLES>-------------------------------
	private Node<E> head = null;
	private ListIterator li = null;

	/*----------------------------------------------------------------------//
	//
	//							CLASS NODE
	//
	//----------------------------------------------------------------------*/
	private class Node<E>
	{
		private E data = null;
		private Node<E> next = null;

		public Node(E e)
		{data = e;}

		public E getData()
		{return data;}

		public void setNext(Node<E> n)
		{next = n;}

		public boolean hasNext()
		{return (next != null);}

		public Node<E> getNext()
		{return next;}

		public String toString()
		{return "Data: " + data;}
	}//-------------------------------------------<end node>---------------------------------------

	/*--------------------------------------------------------------------------------------------
	//
	//									CLASS LIST_ITERATOR
	//
	/*--------------------------------------------------------------------------------------------*/
	public class ListIterator
	{
		private final Node<E> head;
		private Node<E> currentNode;

		public ListIterator(Node<E> n)
		{
			head = n;
			currentNode = head;
		}

		public boolean hasNext()
		{return currentNode.hasNext();}

		public Node<E> next()
		{
			if (hasNext())
			{
				Node<E> n = currentNode;
				currentNode = n.getNext();
				return currentNode;
			}
			return null;
		}

		public Node<E> getHead()
		{return head;}

		public Node<E> getCurrentNode()
		{return currentNode;}

		public Node<E> getFirst()
		{return head;}

		public Node<E> getLast()
		{
			if (head != null)
			{
				if (listLength() == 1)
				{return head;}
				
				reset();
				while (hasNext())
				{
					currentNode = currentNode.getNext();
				}
				return currentNode;
			}
			return null;
		}

		public Node<E> get(int index)
		{
			reset();

			if (index < 0 || index > listLength())
				return null;

			while (index > 0 && hasNext())
			{
				currentNode = next();
				index--;
			}
			return currentNode;
		}
		
		public int getIndex(Node<E> n)
		{
			Node<E> holder = currentNode;
			reset();
			int counter = 0;
			while (currentNode.hasNext() && !currentNode.equals(n))
			{
				currentNode = currentNode.getNext();
				counter++;
			}
			currentNode = holder;
			return counter;
		}
		
		public int getBeforeIndex(Node<E> n)
		{
			Node<E> holder = currentNode;
			
			if (n.equals(head))
				return -1;
			
			reset();
			int counter = 0;
			while (currentNode.hasNext() && !currentNode.getNext().equals(n))
			{
				currentNode = currentNode.getNext();
				counter++;
			}
			currentNode = holder;
			return counter;
		}
		
		public Node<E> getBefore(Node<E> n)
		{
			Node<E> holder = currentNode;
			Node<E> before = null;
			
			if (n.equals(head))
				return null;
			
			reset();
			while (currentNode.hasNext() && !currentNode.getNext().equals(n))
			{
				currentNode = currentNode.getNext();
			}
			before = currentNode;
			currentNode = holder;
			return before;
		}

		public void moveTo(int index)
		{
			reset();
			if (index >= 0 && index < listLength())
			{
				for (int i = 0; i < index; i++)
				{
					if (hasNext())
						currentNode = next();
				}
			}
		}

		public int getCurrentIndex()
		{
			int index = 0;
			while (currentNode.hasNext())
			{
				currentNode = currentNode.getNext();
				index++;
			}
			index = listLength() - (index);
			moveTo(index);
			return index;
		}

		public void reset()
		{currentNode = head;}

		public int listLength()
		{
			if (head == null)
				return 0;
			else
			{
				int counter = 1;
				Node<E> holder = currentNode;
				currentNode = head;

				while (currentNode.hasNext())
				{
					counter++;
					currentNode = currentNode.getNext();
				}
				currentNode = holder;

				return counter;
			}
		}

		public String toString()
		{
			return "List Iterator: " + head;
		}
	}//--------------------------------------<end list_iterator>--------------------------------------


	//---------------------------------------------<CHECKERS>-------------------------------------
	public boolean isDefined()
	{return ((head != null) && (li != null));}

	public void define(E e)
	{
		if (!isDefined())
		{
			head = new Node(e);
			li = new ListIterator(head);
		}
	}

	public boolean contains(Object o)
	{
		if (isDefined())
		{
			li = new ListIterator(head);
			if (o.equals(head.getData()))
				return true;
			while (li.hasNext())
			{
				if (o.equals(li.next().getData()))
					return true;
			}
			return false;
		}
		else
			return false;
	}

	public int indexOf(Object o)
	{
		int index = 0;
		Node n = head;

		while (!n.getData().equals(o))
		{
			if (n.getNext() == null && !n.getData().equals(o))
			{
				index = -1;
				break;
			}

			if (n.getData().equals(o))
				break;
			else
			{
				n = n.getNext();
				index++;
			}
		}

		return index;
	}

	public int size()
	{
		if (isDefined())
		{
			return li.listLength();
		}
		else
		{
			return 0;
		}
	}

	//---------------------------------------------<ADDERS>-----------------------------------------
	public boolean add(E e)
	{
		if (isDefined())
		{
			Node<E> n = new Node<E>(e);
			Node<E> replace = li.getCurrentNode();
			if (replace.equals(head))
			{
				n.setNext(replace);
				head = n;
				li = new ListIterator(head);
			}
			else
			{
				Node<E> before = li.get(li.getCurrentIndex() - 1);
				before.setNext(n);
				n.setNext(replace);
			}

			if (li.getCurrentNode().getData().equals(e))
				return true;
			return false;

		}
		else
		{
			define(e);
			return true;
		}
	}

	public void add(int index, E e)
	{
		if (index >= 0 && index <= li.listLength())
		{
			if (isDefined())
			{
				Node n = new Node(e);

				//Must manually modify iterator beause variable head in iterator is final
				if (index == 0)
				{
					n.setNext(head);
					head = n;
					li = new ListIterator(n);
				}
				else
				{
					Node<E> before = li.get(index - 1);
					if (before.hasNext())
					{
						Node<E> after = before.getNext();
						before.setNext(n);
						n.setNext(after);
					}
					else
					{
						before.setNext(n);
					}
				}
			}
			else
				define(e);
		}
		else
			System.out.println("ERROR: GIVEN INDEX IS OUT OF BOUNDS OF LIST");
	}

	public void addFirst(E e)
	{
		if (isDefined())
		{
			Node n = new Node(e);
			n.setNext(head);
			head = n;
			li = new ListIterator(head);
		}
		else
			define(e);
	}

	public void addLast(E e)
	{
		if (isDefined())
		{
			Node<E> n = new Node<E>(e);
			Node<E> last = li.getLast();//NULL POINTER EXCEPTION
			last.setNext(n);
		}
		else
			define(e);
	}

	public E set(int index, E e)
	{
		if (index >= 0 && index < li.listLength())
		{
			Node n = new Node(e);
			E elem;

			if (index == 0)
			{
				elem = head.getData();
				n.setNext(head);
				head = n;
				li = new ListIterator(head);
				return elem;
			}
			else
			{
				Node<E> before = li.get(index - 1);
				Node<E> replace = before.getNext();
				elem = replace.getData();
				if (replace.hasNext())
				{
					Node<E> after = replace.getNext();
					before.setNext(n);
					n.setNext(after);
				}
				else
				{
					before.setNext(n);
				}
				return elem;
			}
		}
		else
		{
			System.out.println("ERROR: GIVEN INT IS OUT OF BOUNDS OF LIST");
			return null;
		}
	}

	//-----------------------------------------------<REMOVERS>-------------------------------
	public void clear()
	{
		head = null;
		li = null;
	}

	public E remove()
	{
		Node<E> n = head.getNext();
		E element = head.getData();
		head = n;
		li = new ListIterator(head);
		return element;
	}

	public E remove(int index)
	{
		if (index > 0 && index < li.listLength())
		{
			E e = li.get(index).getData();
			if (index > 0)
			{
				li.get(index - 1).setNext(li.get(index + 1));
			}
			else
				head = null;
			return e;
		}
		else
			return null;
	}
	
	public void remove(E e)
	{
		li.reset();
		while(li.getCurrentNode().hasNext())
		{
			if (li.getCurrentNode().getData().equals(e))
			{
				remove(li.getIndex(li.getCurrentNode()));
				li.reset();
			}
		}
	}

	public E removeLast()
	{
		if (isDefined())
		{
			Node<E> last = li.getLast();
			Node<E> before = li.getBefore(last);
			if (before != null)
				before.setNext(null);
			return last.getData();
		}
		else
			return null;
	}

	public E removeFirst()
	{
		if (isDefined())
		{
			Node<E> first = li.getFirst();
			if (first.hasNext())
			{
				head = first.getNext();
				li = new ListIterator(head);
			}
			else
			{
				head = null;
				li = null;
			}
			return first.getData();
		}
		else
		{
			return null;
		}
	}

	//-----------------------------------------------<GETTERS>-----------------------------------
	public E getLast()
	{
		if (isDefined())
		{
			Node<E> n = li.getLast();
			if (n != null)
				return n.getData();
		}
		return null;
	}

	public E getFirst()
	{
		if (isDefined())
			return head.getData();
		return null;
	}

	public E get(int index)
	{
		if (isDefined())
			return li.get(index).getData();
		else
			return null;
	}

	public ListIterator getIterator()
	{return li;}

	public String toString()
	{
		String all = "[";
		if (isDefined())
		{
			li.reset();
			all += head.getData();
			while(li.hasNext())
			{
				all += ", " + li.next().getData();
			}
			all += "]";
		}
		else
		{
			all += "]";
		}
		return all;
	}
}
