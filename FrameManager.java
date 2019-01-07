//Billy Kelly
//Frame Manager
//December 4th, 2018

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;

public class FrameManager extends JFrame implements KeyListener
{
	//Global Variables
	private Store s;
	private MainThread mt;
	private Container c;
	private final int frameWidth = 600, frameHeight = 600;
	Random r = new Random();
	private int tick = 0;
	
	public FrameManager()
	{
		//Panel Variables
		setContentPane(new DrawingPanel());
		c = getContentPane();
		this.setVisible(true);
		this.setSize(frameWidth, frameHeight);
		this.setFocusable(true);
		addKeyListener(this);
		
		s = new Store();
		
		mt = new MainThread(this);
		
		s.addCustomerLine();
		s.addCustomer();
		s.addRegister();
		
		mt.run();
	}
	
	public void update()
	{
		tick++;
		
		//Draw
		if (tick % 6 == 0)
			repaint();
		
		s.update(tick);	
	}
	
	public class DrawingPanel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			s.draw(g);
		}
	}
	
	//----------------------------------------------<EVENT LISTENERS>------------------------------	
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_L)
		{
			s.addCustomerLine();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_R)
		{
			s.addRegister();
		}
		
		if (e.getKeyCode() == KeyEvent.VK_C)
		{
			s.addCustomer();
		}
	}
	
	public void keyReleased(KeyEvent e)
	{
	}
}

