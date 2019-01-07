//Billy kelly
//Sprite
//10.17.2018

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

public class Sprite
{
    //GLOBAL VARIABLES
    private Spritesheet ss;
    private AffineTransform at;
    private int x, y;
    private String name;

    //----------------------------------------------<CONSTRUCTORS>----------------------------------------
    public Sprite(String name, Spritesheet s)
    {
		this.name = name;
		ss = s;
        at = new AffineTransform();
    }

    public Sprite(String name, BufferedImage bi, int rows, int cols)
    {
		ss = new Spritesheet(bi, rows, cols);
		this.name = name;
		at = new AffineTransform();
	}

    //------------------------------------------------<DRAWING>---------------------------------------
    public void draw(Graphics g, int width, int height)
    {
        Graphics2D g2d = (Graphics2D)g;
        g2d.transform(at);
        g2d.drawImage(ss.animateWithImage(), x, y, width, height, null);
    }

    //---------------------------------------------<IMAGE EDITORS>-------------------------------------
    public void scaleTo(int width, int height)
    {
        AffineTransform at = new AffineTransform();
        at.scale((double)(width / ss.getCurrentImage().getWidth()), (double)(height / ss.getCurrentImage().getHeight()));
        this.at = at;
	}

    //--------------------------------------------<ANIMATION EDITORS>---------------------------------
    public void changeAnimation(int row)
    {ss.setRow(row);}

    public void animate()
    {ss.animate();}

    //----------------------------------------------------<MOVERS>-----------------------------------------
    public void moveToX(int x)
    {this.x = x;}

    public void moveToY(int y)
    {this.y = y;}

    public void moveTo(int x, int y)
    {this.x = x; this.y = y;}

    //----------------------------------------------<GETTERS>-----------------------------------------------------
    public Spritesheet getSpritesheet()
    {return ss;}

    public AffineTransform getAffineTransform()
    {return at;}

    public int getX()
    {return x;}

    public int getY()
    {return y;}

    public String getName()
    {return name;}

    //------------------------------------------------<SETTERS>---------------------------------------------------
    public void setX(int x)
    {this.x = x;}

    public void setY(int y)
    {this.y = y;}

    public String toString()
    {return "Name: " + name + "\nAt:" + x + ", " + y;}
}
