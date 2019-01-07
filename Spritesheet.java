//Billy Kelly
//Spritesheet

import java.awt.image.BufferedImage;
import java.awt.*;

public class Spritesheet
{
	//---<VARIABLES FOR SHEET DATA>---
	private BufferedImage sheet;
	private int rows;
	private int cols;
	private int xInterval;
	private int yInterval;

	//---<VARIABLES FOR ANIMATION>-----
	private int currentCol = 0;
	private int currentRow = 0;

	public Spritesheet(BufferedImage sheet, int rows, int cols)
	{
		this.sheet = sheet;
		this.rows = rows;
		this.cols = cols;
		xInterval = sheet.getWidth() / cols;
		yInterval = sheet.getHeight() / rows;
	}

	//--------------------------------------------<ANIMATE AND GET IMAGE>-------------------------------------------

	public BufferedImage getImage(int row, int col)
	{//BROKEN METHOD
		if (row < rows && row > 0 && col < cols && col > 0)
			return sheet.getSubimage(col * xInterval, row * yInterval, sheet.getWidth() / cols, sheet.getHeight() / rows);
		return sheet.getSubimage(0, 0, sheet.getWidth() / cols, sheet.getHeight() / rows);
	}

	public BufferedImage getCurrentImage()
	{
		return sheet.getSubimage(currentCol * xInterval, currentRow * yInterval, sheet.getWidth() / cols, sheet.getHeight() / rows);
	}

	public BufferedImage animateWithImage()
	{
		if (currentCol < cols)
			currentCol++;
		if (currentCol >= cols)
			currentCol = 0;
		return getCurrentImage();
	}

	public void animate()
	{
		if (currentCol < cols)
			currentCol++;
		if (currentCol >= cols)
			currentCol = 0;
	}

	//-------------------------------------------------<GETTERS>-----------------------------------------

	public int getRow()
	{return currentRow;}

	public int getCol()
	{return currentCol;}

	public int getRows()
	{return rows;}

	public int getCols()
	{return cols;}

	public int getXInterval()
	{return xInterval;}

	public int getYInterval()
	{return yInterval;}

	public BufferedImage getSpritesheetImage()
	{return sheet;}

	//--------------------------------------------------<SETTERS>--------------------------------

	public void setRow(int cr)
	{
		if (cr < rows && cr >= 0)
			currentRow = cr;
	}

	public void setCol(int cc)
	{
		if (cc < cols && cc >= 0)
			currentCol = cc;
	}
}

