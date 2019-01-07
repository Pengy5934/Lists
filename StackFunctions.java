public class StackFunctions
{
	public static void changeStacks(StackKelly goTo, StackKelly takeFrom)
	{
		StackKelly holder = new StackKelly();
		reverseStacks(holder, takeFrom);
		reverseStacks(goTo, holder);
	}
	
	public static void reverseStacks(StackKelly goTo, StackKelly takeFrom)
	{
		int size = takeFrom.size();
		goTo.clear();
		for (int i = 0; i < size; i++)
		{
			goTo.push(takeFrom.pop());
		}
		takeFrom.clear();
		System.out.println(goTo);
	}
	
	public static void changeQueues(QueueKelly goTo, QueueKelly takeFrom)
	{
		int size = takeFrom.size();
		goTo.clear();
		while(takeFrom.size() > 0)
		{
			goTo.push(takeFrom.pop());
		}
		takeFrom.clear();
	}
	
	public static CustomerLine findShortestLine(StackKelly<CustomerLine> lines)
	{
		if (lines.size() > 0)
		{
			StackKelly<CustomerLine> holder = new StackKelly<CustomerLine>();
			CustomerLine shortest = null;
			int size = lines.size();
			for (int i = 0; i < size; i++)
			{
				CustomerLine cl = lines.pop();

				if (i == 0)
					shortest = cl;
				else if (cl.size() < shortest.size())
					shortest = cl;
				holder.push(cl);
			}
			reverseStacks(lines, holder);
			return shortest;
		}
		else
			return null;
	}
	
	public static void equalizeLines(StackKelly<CustomerLine> lines)
	{
		CustomerLine compareTo = null;
		StackKelly<CustomerLine> before = lines;
		StackKelly<CustomerLine> after = new StackKelly<CustomerLine>();
		StackKelly<CustomerLine> holder = new StackKelly<CustomerLine>();
		int bSize = before.size();
		for (int i = 0; i < bSize; i++)
		{
			compareTo = before.pop();
			for (int j = 0; j < bSize - 1; j++)
			{
				CustomerLine cl = before.pop();
				equalize(compareTo, cl);
				holder.push(cl);
			}
			reverseStacks(before, holder);
			
			holder.clear();
			int aSize = after.size();
			for (int j = 0; j < aSize; j++)
			{
				CustomerLine cl = after.pop();
				equalize(compareTo, cl);
				holder.push(cl);
			}
			reverseStacks(after, holder);
		}
	}
	
	public static void equalize(CustomerLine c1, CustomerLine c2)
	{
		if (c1.size() - c2.size() > 1)
		{
			int extra = c1.size() - c2.size();
			for (int i = 0; i < extra / 2; i++)
			{
				c2.add(c1.getLast());
			}
		}
		else if (c2.size() - c1.size() > 1)
		{
			int extra = c2.size() - c1.size();
			for (int i = 0; i < extra / 2; i++)
			{
				c1.add(c2.getLast());
			}
		}
	}
}

