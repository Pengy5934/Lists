//Billy kelly
//MainThread
//12.4.2018

public class MainThread extends Thread
{
	//Global Variables
	private FrameManager fm;
	private final int MAX_FPS = 60;
	
	public MainThread(FrameManager fm)
	{this.fm = fm;}
	
	public void run()
	{
		//Variables
		long startTime;
		long elapsedTime;
		long waitTime;
		long targetTime = 1000 / MAX_FPS;
		long totalTime = 0;
		boolean running = true;
		
		while(running)
		{
			startTime = System.nanoTime();
			//System.out.println("TICK");
			
			fm.update();
			
			elapsedTime = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - elapsedTime;
			
			
			try 
			{
				if (waitTime > 0)
					sleep(waitTime);
			}
			catch (Exception e)
			{e.printStackTrace();}
		}
	}
}

