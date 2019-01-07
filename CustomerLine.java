//Billy Kelly
//Customer Line
//12.6.2018

import java.awt.*;

public class CustomerLine
{
	//GLOBAL VAIRABLES
	private QueueKelly<Customer> line = new QueueKelly<Customer>();
	private QueueKelly<Customer> leaving = new QueueKelly<Customer>();
	private QueueKelly<Customer> holder = new QueueKelly<Customer>();
	
	//GAME VARIABLES
	private int colX, colY;
	private static int colWidth = 30, colHeight = 200;
	
	public CustomerLine(int colX, int colY, int colWidth, int colHeight)
	{
		this.colX = colX;
		this.colY = colY;
		this.colHeight = colHeight;
		this.colWidth = colWidth;
	}
	
	public CustomerLine(int colX, int colY)
	{
		this.colX = colX;
		this.colY = colY;
	}
	
	/*-------------------------------------------------------------------------
	 * The Processes Handles by the CustomerLine Class are as follows:
	 * 
	 * -Drawing every Customer
	 * -Updating each Customer
	 * -Moving each Customer
	 * -Adding a Customer to the Line
	 * -Removing the last Customers from the Line
	 * -Transferring money to the LineManager
	 * ------------------------------------------------------------------------*/
	 
	 //--------------------------------------------------<DRAWING>--------------------------------------
	 public void draw(Graphics g)
	 {
		 g.setColor(Color.YELLOW);
		 g.fillRect(colX, colY, colWidth, colHeight);
		 
		 int size = line.size();
		 for (int i = 0; i < size; i++)
		 {
			 Customer c = line.pop();
			 if (c != null)
				c.draw(g);
			 holder.push(c);
		 }
		 StackFunctions.changeQueues(line, holder);
		 
		 size = leaving.size();
		 for (int i = 0; i < size; i++)
		 {
			 Customer c = leaving.pop();
			 if (c != null)
				c.draw(g);
			 holder.push(c);
		 }
		 StackFunctions.changeQueues(leaving, holder);
	 }
	 
	 //--------------------------------------------------<UPDATING>--------------------------------------
	 public void update(int tick)
	 {
		 int size = line.size();
		 for (int i = 0; i < size; i++)
		 {
			Customer c = line.pop();
			if (c != null)
			{
				c.update(tick);
				 
				if (i == 0)
					moveUntilBottom(c);
				else
					moveUntilCustomer(c);
					
				if (c.isServed())
				{
					leaving.push(c);
					//c.setX(c.getX() + c.getWidth());
					c.changeAnimation(3);
				}
					
				if (c.isFurious())
				{
					leaving.push(c);
					c.setX(c.getX() + c.getWidth());
					c.changeAnimation(3);
				}
				else			 
					holder.push(c);
			}
		 }
		 StackFunctions.changeQueues(line, holder);
		 
		 size = leaving.size();
		 for (int i = 0; i < size; i++)
		 {
			Customer c = leaving.pop();
			if (c != null)
			{
				c.update(tick);
				
				moveOffScreen(c);
			}
			holder.push(c);
		 }
		 StackFunctions.changeQueues(leaving, holder);
	 }
	 
	 //--------------------------------------------------<MOVEMENT>-----------------------------------------
	 public void moveUntilBottom(Customer c)
	 {
		 if (c.getY() + c.getHeight() < (colY + colHeight))
		 {
			 c.setY(c.getY() + 1);
		 }
	 }
	 
	 public void moveUntilCustomer(Customer c)
	 {
		 int size = holder.size();
		 boolean hasSpace = true;
		 QueueKelly<Customer> holder2 = new QueueKelly<Customer>();
		 
		 for (int i = 0; i < size; i++)
		 {
			 Customer c2 = holder.pop();
			 if (c.getY() + c.getHeight() > c2.getY())
				hasSpace = false;
			holder2.push(c2);
		 }
		 StackFunctions.changeQueues(holder, holder2);
		 
		 if (hasSpace)
			c.setY(c.getY() + 1);
	 }
	 
	 public void moveOffScreen(Customer c)
	 {
		 c.setY(c.getY() - 1);
		 if (c.getY() + c.getHeight() < 0)
			leaving.pop();
	 }
	 
	 //---------------------------------------------------<MONEY>-----------------------------------------
	 public int getMoneys()
	 {
		 int total = 0;
		 for (int i = 0; i < line.size(); i++)
		 {
			 Customer c = line.pop();
			 if (c != null)
				total += c.getMoney();
			 holder.push(c);
		 }
		 StackFunctions.changeQueues(line, holder);
		 return total;
	 }
	 //--------------------------------------------------<ADDING>-----------------------------------------
	 public void add(Customer c)
	 {
		 line.push(c);
		 c.setX(colX);
		 c.setY(colY);
	 }
	 
	 //--------------------------------------------------<REMOVING>----------------------------------------
	 public Customer remove(int index)
	 {
		 if (index > 0 && index < line.size())
		 {
			Customer send = null;
			int size = line.size();
			for (int i = 0 ; i < size; i++)
			{
				Customer c = line.pop();
				if (i == index)
					send = c;
				else
					holder.push(c);
			}
			StackFunctions.changeQueues(line, holder);
			return send;
		 }
		 else
			return null;
	 }
	 
	 public Customer removeLast()
	 {
		Customer send = null;
		int size = line.size();
		for (int i = 0 ; i < size; i++)
		{
			Customer c = line.pop();
			if (i == size - 1)
				send = c;
			else
				holder.push(c);
		}
		StackFunctions.changeQueues(line, holder);
		return send;
	 }
	 
	 public Customer removeFirst()
	 {return line.pop();}
	 
	 //-------------------------------------------------<SETTING>-------------------------------------------
	 public void setComplete()
	 {
		 Customer c = line.peek();
		 c.setServed(true);
	 }
	 
	//--------------------------------------------------<GETTING>-------------------------------------------
	public Customer getAt(int index)
	 {
		 if (index > 0 && index < line.size())
		 {
			Customer send = null;
			int size = line.size();
			for (int i = 0 ; i < size; i++)
			{
				Customer c = line.pop();
				if (i == index)
					send = c;
				holder.push(c);
			}
			StackFunctions.changeQueues(line, holder);
			return send;
		 }
		 else
			return null;
	 }
	 
	 public Customer getLast()
	 {
		Customer send = null;
		int size = line.size();
		for (int i = 0 ; i < size; i++)
		{
			Customer c = line.pop();
			if (c != null)
			{
				if (i == size - 1)
					send = c;
				holder.push(c);
			}
		}
		StackFunctions.changeQueues(line, holder);
		return send;
	 }
	 
	 public Customer getFirst()
	 {return line.peek();}
	 
	 public int getColX()
	 {return colX;}
	 
	 public int getColY()
	 {return colY;}
	 
	 public int getColWidth()
	 {return colWidth;}
	 
	 public int getColHeight()
	 {return colHeight;}
	 
	 public QueueKelly<Customer> getLine()
	 {return line;}
	 
	 public int size()
	 {return line.size();}
	 
	public String toString()
	{
		String all = "CustomerLine: ";
		 
		int size = line.size();
		for (int i = 0; i < size; i++)
		{
			Customer c = line.pop();
			if (c != null)
			{
				all += c.toString() + "\n\t\t";
				holder.push(c);
			}
		}
		StackFunctions.changeQueues(line, holder);
		return all;
	}
}
