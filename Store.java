//Billy Kelly
//Store
//12.20.2018

import java.awt.*;

public class Store
{
	//Structure Variables
	LineManager lm;
	RegisterManager rm;
	
	//Game Variables
	private int totalMoney = 0;
	private int payRegRate = 40;
	
	public Store()
	{
		lm = new LineManager(50, 100, 30, 200);
		rm = new RegisterManager(0, 400);
	}
	
	/*---------------------------------------------------------------------/
	 * The functions handled by the Store class are as follows:
	 * 
	 *  -Drawing each CustomerLine and Register
	 *  -Updating each CustomerLine and Register
	 *  -Create an environment in which each Register can interact with a 
	 * corresponding CustomerLine
	 *  -Add a CustomerLine to the Stack
	 *  -Add a Register to the Stack
	 *  -Add a Customer to the shortest CustomerLine
	 * 
	 * --------------------------------------------------------------------*/
	
	//---------------------------------------------------<DRAWING>---------------------------------
	public void draw(Graphics g)
	{
		lm.draw(g);
		rm.draw(g);
	}
	
	//---------------------------------------------------<UPDATE>---------------------------------
	public void update(int tick)
	{
		lm.update(tick);
		rm.update(tick);
		
		System.out.println("TICKTICKTICKTICKTICKTICKTICK");
		
		rm.setToWork(lm.getNumCustomers());
		
		lm.setComplete(rm.getComplete());
		
		totalMoney += lm.getMoneys();
		
		if (tick % payRegRate == 0)
			totalMoney -= rm.getRegisters().size() * 10;	
	}
	
	//------------------------------------------------<ADDERS>----------------------------------------
	public void add(CustomerLine cl)
	{lm.add(cl);}
	
	public void addCustomerLine()
	{lm.addCustomerLine();}
	
	public void add(Customer c)
	{lm.add(c);}
	
	public void addCustomer()
	{lm.addCustomer();}
	
	public void add(Register r)
	{rm.add(r);}
	
	public void addRegister()
	{rm.addRegister();}
	
	//------------------------------------------------<GETTERS>------------------------------------------
	
	
	public String toString()
	{
		String all = "";
		
		all += "LINE DATA:\n";
		all += lm.toString();
		
		all += "REGISTER DATA:\n";
		all += rm.toString();
		
		return all;
	}
}

