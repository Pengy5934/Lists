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
			System.out.println("GET METHOD");
			if (index < 0 || index > listLength())
				return null;
			System.out.println("GET METHOD 2");

			while (index > 0 && hasNext())
			{
				currentNode = next();
				index--;
			}
			System.out.println("GET METHOD 3");
			return currentNode;
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
			System.out.println("LIST LENGTH START");
			if (head == null)
				return 0;

			int counter = 1;
			Node<E> holder = currentNode;
			currentNode = head;
			System.out.println(this);

			while (currentNode.hasNext())
			{
				counter++;
				currentNode = currentNode.getNext();
				System.out.println(counter + "----" + hasNext());
				if (counter > 100)
					break;
			}
			currentNode = holder;
			System.out.println("LIST LENGTH END");

			return counter;
		}

		public String toString()
		{
			return "List Iterator: " + head;
		}
	}//--------------------------------------<end list_iterator>--------------------------------------


	//---------------------------------------------<CHECKERS>-------------------------------------
	public boolean isDefined()
	{System.out.println("DEFINED");return ((head != null) || (li != null));}

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

	//-----------------------------------------------------------<ADDERS>---------------------------------------------------
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

	//-------------------------------------------------------------<REMOVERS>-------------------------------------------
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

	public E removeLast()
	{
		Node<E> last = li.getLast();
		Node<E> before = li.get(li.getCurrentIndex() + 1);
		before.setNext(null);
		return last.getData();
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

	//-----------------------------------------------------------<GETTERS>---------------------------------------------
	public E getLast()
	{
		if (isDefined())
			return li.getLast().getData();
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
		System.out.println("GET");
		if (isDefined())
		{
			System.out.println(this);
			return li.get(index).getData();
		}
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
/*


//Billy Kelly
//LinkedList
//11.12.2018

/*----------------------------------------------------------------------//
//
//							CLASS LINKEDLISTKELLY
//
//----------------------------------------------------------------------*/

/*public class LinkedListKelly<E>
{
	//---------------------------------<GLOBAL VARIABLES>-------------------------------
	private Node<E> head = null;
	private ListIterator li = null;

	/*----------------------------------------------------------------------//
	//
	//							CLASS NODE
	//
	//----------------------------------------------------------------------*/
	/*private class Node<E>
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
	/*public class ListIterator
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
			if (currentNode.hasNext())
			{
				currentNode = currentNode.getNext();
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
			while (currentNode.hasNext())
			{
				currentNode = currentNode.getNext();
			}
			return currentNode;
		}

		public Node<E> get(int index)
		{
			reset();

			if (index < 0 || index > listLength())
				return null;

			while (index > 0)
			{
				currentNode = currentNode.getNext();
				index--;
			}

			return currentNode;
		}

		public void moveTo(int index)
		{
			reset();
			if (index >= 0 && index <= listLength())
			{
				for (int i = 0; i < index; i++)
				{
					if (currentNode.hasNext())
						currentNode = currentNode.getNext();
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
			index = listLength() - (index + 1);
			moveTo(index);
			return index;
		}

		public void reset()
		{currentNode = head;}

		public int listLength()
		{
			if (head == null)
				return 0;

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

		public String toString()
		{
			return "List Iterator: " + head;
		}
	}//--------------------------------------<end list_iterator>--------------------------------------


	//---------------------------------------------<CHECKERS>-------------------------------------
	public boolean checkDefined()
	{return ((head != null) || (li != null));}

	public void define(E e)
	{
		if (!checkDefined())
		{
			head = new Node(e);
			li = new ListIterator(head);
		}
	}

	public boolean contains(Object o)
	{
		if (checkDefined())
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
		if (head == null)
			return 0;
		else
			return li.listLength();
	}

	//---------------------------------------------<ADDERS>-----------------------------------------
	public boolean add(E e)
	{
		if (checkDefined())
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
			if (checkDefined())
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
		if (checkDefined())
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
		if (checkDefined())
		{
			Node n = new Node(e);
			li.getLast().setNext(n);
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

	public E removeLast()
	{
		E e = li.get(li.listLength() - 2).getData();
		li.get(li.listLength() - 2).setNext(null);
		return e;
	}

	public E removeFirst()
	{
		Node<E> first = li.getFirst();
		if (li.listLength() == 1)
		{
			head = null;
			li = null;
		}
		else
		{
			head = first.getNext();
			li = new ListIterator(head);
		}
		return first.getData();
	}

	//-----------------------------------------------<GETTERS>-----------------------------------
	public E getLast()
	{return li.getLast().getData();}

	public E getFirst()
	{return head.getData();}

	public E get(int index)
	{
		if (checkDefined())
			return li.get(index).getData();
		else
			return null;
	}

	public ListIterator getIterator()
	{return li;}

	public String toString()
	{
		String all = "[";
		if (checkDefined())
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
}*/
