import java.util.*;
import java.io.*;

class Driver
{
	private static int REQUESTS_PER_DAY = 20;
	public static void main(String[] args) 
	{
	
		List<String> list = new ArrayList<String>();
		if(args.length != 1)
		{
			System.out.println("Program requires an input file.");
		}
		else
		{
			try
			{
				Scanner sc = new Scanner(new File(args[0]));
				while(sc.hasNextLine())
				{
					String call = sc.nextLine();
					list.add(call);
				}
			}
			catch (Exception e)
			{
				System.out.println("Error: " + e.getMessage());
			}
		}
	
		int start = 0;
		int end = REQUESTS_PER_DAY;

		Dispatcher dp = new Dispatcher();
		int i = 0;
		while(i != list.size())
		{
			
			for(i = start; i < end && i < list.size(); i++)
			{
				dp.add(list.get(i));
			}
			if(!dp.checkEmpty())
			{
				dp.print();		
			}
			start = end;
			end += REQUESTS_PER_DAY;
		}
		while(!dp.checkEmpty())
		{
			dp.print();
		}
		
	}
}
