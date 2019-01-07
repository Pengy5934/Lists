//Billy Kelly
//LineManager
//12.20.2018

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

public class LineManager
{
	//-----------------------------------------<GLOBAL VARIABLES>-----------------------------------
	StackKelly<CustomerLine> lines = new StackKelly<CustomerLine>();
	StackKelly<CustomerLine> holder = new StackKelly<CustomerLine>();
	
	//GAME VARIABLES
	private int lineInterval = 50;
	private int startColX, colY;
	private int colWidth, colHeight;
		
	//--------------------------------------------<CONSTRUCTOR>-------------------------------------
	public LineManager(int startColX, int colY, int colWidth, int colHeight)
	{
		this.startColX = startColX;
		this.colY = colY;
		this.colWidth = colWidth;
		this.colHeight = colHeight;
	}
	
	/*---------------------------------------------------------------
	 * The Processes managed by the LineManager Class are as follows:
	 * 
	 * -Updating each CustomerLine
	 * -Drawing Each CustomerLine
	 * -Transferring Money to the Store
	 * -Adding a new Customer to the Shrotest Line
	 * -Finding the shortest line
	 * -Adding a new CustomerLine
	 * -Equalizing the number of Customers in each line
	 * -Adding the Customers to other lines when one is removed
	 *--------------------------------------------------------------*/
	//-----------------------------------------------<UPDATE>---------------------------------------
	public void update(int tick)
	{
		int size = lines.size();
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			cl.update(tick);
			holder.push(cl);
		}
		StackFunctions.reverseStacks(lines, holder);
	}
	
	//---------------------------------------------<DRAWING>---------------------------------------
	public void draw(Graphics g)
	{
		int size = lines.size();
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			if (cl != null)
				cl.draw(g);
			holder.push(cl);
		}
		StackFunctions.reverseStacks(lines, holder);
	}
	
	//----------------------------------------------<MONEY>-----------------------------------------
	public int getMoneys()
	{
		int total = 0;
		int size = lines.size();
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			total += cl.getMoneys();
			holder.push(cl);
		}
		StackFunctions.reverseStacks(lines, holder);
		return total;
	}
	
	//----------------------------------------------<ADDING>----------------------------------------
	public void add(Customer c)
	{
		StackFunctions.findShortestLine(lines).add(c);
	}
	
	public void addCustomer()
	{
		CustomerLine cl = StackFunctions.findShortestLine(lines);
		if(cl != null)
		{
			try {
				cl.add(new Customer("Cust" + cl.size(), new Spritesheet(ImageIO.read(new File("Customer.png")), 4, 4)));
			} catch (Exception ex)
			{ex.printStackTrace();}
		}
	}
	
	public void add(CustomerLine cl)
	{
		lines.push(cl);
		equalizeLines();
	}
	
	public void addCustomerLine()
	{
		lines.add(new CustomerLine(startColX + (startColX * lines.size()), colY, colWidth, colHeight));
		equalizeLines();
	}
	
	//-------------------------------------------<SHORTEST LINE>-------------------------------------
	
	//--------------------------------------------<EQUALIZING>----------------------------------------
	public void equalizeLines()
	{
		int size = lines.size();
		StackKelly<CustomerLine> before = lines;
		StackKelly<CustomerLine> after = new StackKelly<CustomerLine>();
		
		if (size > 1)
		{
			//Iterate through each customerline in the before stack
			for (int i = 0; i < size; i++)
			{
				CustomerLine apart = before.pop();
				int bSize = before.size();
				
				//Iterate through each customerline left in the before stack
				for (int j = 0; j < bSize; j++)
				{
					CustomerLine inLine = before.pop();
					if (inLine.size() > apart.size())
					{
						while (inLine.size() > apart.size() + 1)
						{
							apart.add(inLine.getLast());
						}
					}
					if (apart.size() > inLine.size())
					{
						while (apart.size() > inLine.size() + 1)
						{
							inLine.add(apart.getLast());
						}
					}
					holder.push(inLine);
				}
				StackFunctions.reverseStacks(before, holder);
				
				//Iterate through each customerline in the after stack
				int aSize = after.size();
				for (int k = 0; k < aSize; k++)
				{
					CustomerLine inLine = after.pop();
					if (inLine.size() > apart.size())
					{
						while (inLine.size() > apart.size() + 1)
						{
							apart.add(inLine.getLast());
						}
					}
					if (apart.size() > inLine.size())
					{
						while (apart.size() > inLine.size() + 1)
						{
							inLine.add(apart.getLast());
						}
					}
					holder.push(inLine);
				}
				StackFunctions.reverseStacks(after, holder);
				after.push(apart);
			}
			StackFunctions.reverseStacks(lines, after);
		}
	}
	
	//Method will search through the first customers of each line and return the highest anger
	public Customer findHighestAnger()
	{
		int size = lines.size();
		Customer lowest = null;
		
		//Iterate through each customerLine
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			if (cl != null)
			{
				//Error catching for null comparisons
				if (lowest == null)
					lowest = cl.getFirst();
				else
				{
					//Return if furious. Nothing is more angry than furious.
					if (lowest.isFurious())
						return lowest;
					else
					{
						//If new Customer is more angry than lowest, change lowest
						Customer c = cl.getFirst();
						if (c != null && lowest.getAnger() > c.getAnger())
							lowest = cl.getFirst();
					}
				}
			}
			holder.push(cl);
		}
		StackFunctions.reverseStacks(lines, holder);
		return lowest;
	}
	
	//---------------------------------------------<REMOVING>----------------------------------------
	public CustomerLine removeFirstLine()
	{
		int size = lines.size();
		CustomerLine first = null;
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			first = cl;
			if (i < size - 1)
				holder.push(cl);
		}
		if (first != null)
		{
			while(first.size() > 0)
			{
				lines.peek().add(first.getLast());
			}
		}
		equalizeLines();
		return first;
	}
	
	public CustomerLine removeLastLine()
	{
		CustomerLine cl =lines.pop();
		while(cl.size() > 0 && lines.size() > 0)
		{
			lines.peek().add(cl.getLast());
		}
		equalizeLines();
		return cl;
		
	}
	//----------------------------------------------<SETTERS>-------------------------------------------
	public void setComplete(int num)
	{
		for (int i = 0; i < num; i++)
		 {
			Customer c = findHighestAnger();
			if (c != null)
			{
				c.setServed(true);
			}
		}
	}
	
	//----------------------------------------------<GETTERS>-------------------------------------------
	public StackKelly<CustomerLine> getCustomerLines()
	{return lines;}
	
	public int getNumCustomers()
	{
		int size = lines.size();
		int numCust = 0;
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			if (cl.getFirst() != null)
				numCust++;
			holder.push(cl);
		}
		StackFunctions.reverseStacks(lines, holder);
		return numCust;
	}
	
	/*public int linesPopulated()
	{
		System.out.println("CHECK 2");
		int size = lines.size();
		int num = 0;
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			if (cl.getFirst() != null)
				num++;
			holder.push(cl);
		}
		StackFunctions.reverseStacks(lines, holder);
		return num;
	}*/
	
	public String toString()
	{
		String all = "";
		
		int size = lines.size();
		for (int i = 0; i < size; i++)
		{
			CustomerLine cl = lines.pop();
			all += cl.toString() + "\n\t";
			holder.push(cl);
		}
		StackFunctions.reverseStacks(lines, holder);
		
		return all;
	}
		
}
