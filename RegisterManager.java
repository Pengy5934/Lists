//Billy Kelly
//Register Manager
//12.20.2018

import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;

public class RegisterManager
{
	//GLOBAL VARIABLES
	private StackKelly<Register> rs = new StackKelly<Register>();
	private StackKelly<Register> holder = new StackKelly<Register>();
	private int registerInterval = 100;
	private int startX, startY;
	private int lastX, lastY;
	
	private int numComplete = 0;
	private int numWaiting = 0;
	private int numWorking = 0;
	
	//--------------------------------------------<CONSTRUCTORS>----------------------------------
	public RegisterManager(int startX, int startY)
	{
		this.startX = startX;
		this.startY = startY;
	}
	
	/*----------------------------------------------------------------------/
	 * The processes managed by the RegisterManager Class are as follows:
	 * 
	 *  -Update each register
	 *  -Draw each register
	 *  -Adding a register
	 *  -Removing a register
	 *  -Count # of complete registers, working registers, or waiting registers
	 * 
	 * ---------------------------------------------------------------------*/
	
	//-----------------------------------------------<DRAWING>---------------------------------
	public void draw(Graphics g)
	{
		int size = rs.size();
		for (int i = 0; i < size; i++)
		{
			Register r = rs.pop();
			r.draw(g);
			holder.push(r);
		}
		StackFunctions.reverseStacks(rs, holder);
	}
	
	//-----------------------------------------------<UPDATE>----------------------------------
	public void update(int tick)
	{
		int size = rs.size();
		for (int i = 0; i < size; i++)
		{
			Register r = rs.pop();
			
			r.update(tick);
			
			if (r.isComplete())
				numComplete++;
			else if (r.isWorking())
				numWorking++;
			else
				numWaiting++;
				
			setToWait();
			
			holder.push(r);
		}
		StackFunctions.reverseStacks(rs, holder);
	}
	
	//------------------------------------------------<ADDERS>---------------------------------
	public void add(Register r)
	{rs.push(r);}
	
	public void addRegister()
	{
		try {
			Register r = new Register("Regis" + rs.size(), ImageIO.read(new File("Register.jpeg")), startX * (rs.size() + 1), startY);
			rs.push(r);
		}
		catch (Exception e)
		{e.printStackTrace();}
	}
	
	//-----------------------------------------------<REMOVERS>--------------------------------
	public Register remove()
	{return rs.pop();}
	
	public Register removeLast()
	{return rs.pop();}
	
	//-----------------------------------------------<SETTERS>----------------------------------
	public void setToWork(int num)
	{
		int size = rs.size();
		for (int i = 0; i < size && num > 0; i++)
		{
			Register r = rs.pop();
			if (!r.isWorking())
			{
				r.setWorking(true);
				num--;
			}
			holder.push(r);
		}
		StackFunctions.reverseStacks(rs, holder);
	}
	
	public void setToWait()
	{
		int size = rs.size();
		for (int i = 0; i < size; i++)
		{
			Register r = rs.pop();
			if (r.isComplete())
			{
				r.setWorking(false); //This sets state to 'WAITING'
			}
			holder.push(r);
		}
		StackFunctions.reverseStacks(rs, holder);
	}
	
	//-----------------------------------------------<GETTERS>----------------------------------
	public StackKelly<Register> getRegisters()
	{return rs;}
	
	public int getComplete()
	{return numComplete;}
	
	public int getWorking()
	{return numWorking;}
	
	public int getWaiting()
	{return numWaiting;}
	
	public String toString()
	{
		String all = "";
		int size = rs.size();
		for (int i = 0; i < size; i++)
		{
			Register r = rs.pop();
			all += r.toString() + "\n\t";
			holder.push(r);
		}
		StackFunctions.reverseStacks(rs, holder);
		return all;
	}
}

