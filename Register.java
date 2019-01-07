//Billy Kelly
//Register
//12.4.2018

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class Register
{
	//--------------------------------------<GLOBAL VARIABLES>-----------------------------
	private String name;
	private int x, y;
	private static int WIDTH = 100, HEIGHT = 100;
	private BufferedImage bi, biComplete = null;
	private int completion = 255;
	private String state = "WAITING";//WORKING, WAITING, or COMPLETE
	private static int workRate = 1;
	
	//--------------------------------------<CONSTRUCTORS>---------------------------------
	public Register(String name, BufferedImage b, int x, int y)
	{
		this.name = name;
		bi = b;
		this.x = x;
		this.y = y;
		
		try
		{
			biComplete = ImageIO.read(new File("RegisterComplete.jpeg"));
		}
		catch (Exception e)
		{e.printStackTrace();}
	}
	
	//-----------------------------------------<DRAWING>----------------------------------------
	public void draw(Graphics g)
	{
		if (biComplete != null && isComplete())
			g.drawImage(biComplete, x, y, WIDTH, HEIGHT, null);
		else
			g.drawImage(bi, x, y, WIDTH, HEIGHT, null);
	}
	
	public void update(int tick)
	{
		if (completion <= 0)
		{
			state = "COMPLETE";
		}
		
		if (tick % workRate == 0 && state.equalsIgnoreCase("WORKING"))
			completion--;	
	}
	
	//------------------------------------------<CHECKERS>--------------------------------------
	public boolean hasCustomer()
	{return (state.equalsIgnoreCase("WORKING") || state.equalsIgnoreCase("COMPLETE"));}
	
	public boolean isWorking()
	{return (state.equalsIgnoreCase("WORKING"));}
	
	public boolean isComplete()
	{return (state.equalsIgnoreCase("COMPLETE"));}
	
	//------------------------------------------<SETTERS>---------------------------------------	
	public void setWorking(boolean b)
	{if(b) state = "WORKING";else state = "WAITING";}
	
	public void setX(int newX)
	{x = newX;}
	
	public void setY(int newY)
	{y = newY;}
	
	//------------------------------------------<GETTERS>---------------------------------------	
	public int getX()
	{return x;}
	
	public int getY()
	{return y;}
	
	public int getWidth()
	{return WIDTH;}
	
	public int getHeight()
	{return HEIGHT;}
	
	public String getState()
	{return state;}
	
	public String toString()
	{
		return "Name: " + name + " is " + state;
	}
}

