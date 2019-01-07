//Billy Kelly
//Customer
//12.4.2018

import java.awt.image.BufferedImage;
import java.awt.*;
import java.util.Random;

public class Customer extends Sprite
{
	//Global Variables
	private int x = 0, y = 0;
	private final int SPRITE_WIDTH = 30, SPRITE_HEIGHT = 30;
	private int angerRate;
	private int anger = 255;
	private int money;
	private boolean served = false, furious = false;
	
	//------------------------------------------<CONSTRUCTORS>--------------------------------------
	public Customer(String name, Spritesheet ss)
	{
		super(name, ss);
		money = new Random().nextInt(80) + 20;
		angerRate = new Random().nextInt(15) + 5;
	}
	
	public Customer(String name, Spritesheet ss, int aRate, int money)
	{
		super (name, ss);
		angerRate = aRate;
		this.money = money;
	}
	
	public Customer(String name, BufferedImage bi, int rows, int cols, int aRate, int money)
	{
		super(name, new Spritesheet(bi, rows, cols));
		angerRate = aRate;
		this.money = money;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(new Color(255, anger, anger));
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		super.draw(g, SPRITE_WIDTH, SPRITE_HEIGHT);
	}
	
	public void update(int tick)
	{
		if (tick % angerRate == 0 && !served)
			incrementAnger();
	}
	
	public void incrementAnger()
	{
		if (anger > 0)
			anger--;
		if (anger <= 0)
			anger = 0;
		if (anger <= 0)
			furious = true;
	}
	
	//------------------------------------------<GETTERS>------------------------------------------
	public int getX()
	{return super.getX();}
	
	public int getY()
	{return super.getY();}
	
	public int getHeight()
	{return SPRITE_HEIGHT;}
	
	public int getWidth()
	{return SPRITE_WIDTH;}
	
	public int getAngerRate()
	{return angerRate;}
	
	public boolean isFurious()
	{return furious;}
	
	public int getAnger()
	{return anger;}
	
	public int getMoney()
	{
		if (served)
			return money;
		return 0;
	}
	
	public boolean isServed()
	{return served;}
	
	//------------------------------------------<SETTERS>------------------------------------------
	public void setX(int x)
	{super.setX(x);}
	
	public void setY(int y)
	{super.setY(y);}
	
	public void setServed(boolean b)
	{served = b;}
	
	public void setMoney(int m)
	{money = m;}
	
	public String toString()
	{
		String all = "";
		all += "Name : " +  getName();
		all += "\n\tMoney: " + money + "\n\tAnger: " + anger + "\n\tAnger Rate: " + angerRate;
		return all;
	}
}

