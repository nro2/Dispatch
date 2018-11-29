import java.util.*;
import java.io.*;


enum Priority
{
	HIGH,
	MEDIUM,
	LOW;
}

enum Type
{
	VEHICLE,
	MEDICAL,
	FACILITY,
	ENVIRONMENT;
}

public class Dispatcher
{
	private int day = 1;
	private int carryOver = 0;
	private int STATION_MAX = 3;
	Queue<Emergency> queue = new PriorityQueue<Emergency>(11, priorComparator);
	List<Station> list = new ArrayList<Station>();

	public void add(String call)
	{

	 	Emergency emer = new Emergency();
		String station = findName(call);
		Type type = findType(call);
		Priority priority = findPriority(call, type);
		int id = findId(call);

		emer.setId(id);
		emer.setName(station);
		emer.setType(type);
		emer.setPriority(priority);
		queue.add(emer);
	}

	
	public boolean checkEmpty()
	{
		boolean ret = false;
		if(queue.isEmpty())
		{
			ret = true;
		}
		return ret;
	}


	public void print()
	{
		System.out.println(" ");
		List<Emergency> carry = new ArrayList<Emergency>();	
		System.out.println("======= Processing requests for day " + day);
		System.out.println("======= " + carryOver + " requests carried over from previous day");
	
		
		while(queue.peek().getPriority().toString() == "HIGH") //PRINTING HIGH PRIORITY
		{
			System.out.println("Dispatching " + queue.peek().getPriority() + " " + queue.peek().getType() + " responder to " + queue.peek().getName());
			for(int i = 0; i < list.size(); i++)
			{
				if(queue.peek().getName().equals(list.get(i).getName()))
				{
				
					list.get(i).setRequest(list.get(i).getRequest() + 1);
				}
			}
			queue.remove();
		}
		
		while(!queue.isEmpty())
		{
			String nm = queue.peek().getName();
			for(int i = 0; i < list.size(); i++)
			{
				if(nm.equals(list.get(i).getName()) && list.get(i).getRequest() < STATION_MAX)
				{
					System.out.println("Dispatching " + queue.peek().getPriority() + " " + queue.peek().getType() + " responder to " + queue.peek().getName());
					list.get(i).setRequest(list.get(i).getRequest() + 1);
				}
				else if(nm.equals(list.get(i).getName()) && list.get(i).getRequest() == STATION_MAX)
				{
					carry.add(queue.peek());
				}
			}
			queue.remove();
		}
		carryOver = carry.size();
		for(int i = 0; i < carry.size(); i++)
		{
			queue.add(carry.get(i));
		}
		day++;
		for(int i = 0; i < list.size(); i++)
		{
			list.get(i).setRequest(0);
		}	
	}		

	public static Comparator<Emergency> priorComparator = new Comparator<Emergency>()
	{
		@Override
		public int compare(Emergency e1, Emergency e2) 
		{
			if(!e1.getPriority().equals(e2.getPriority()))
			{
				return (e1.getPriority().compareTo(e2.getPriority()));
			}
			else
			{
				return(e1.getId() - e2.getId());
			}
		}
	};

	public int findId(String call)
	{
		String[] arr = call.split(":");
		int id = Integer.parseInt(arr[0]);
		return id;
	}


	public String findName(String call)
	{
		String search1 = "at";
		String search2 = "in";
		String name = " ";
		String per = ".";	
		String [] arr = call.split(" ");
		
		for(int i = 1; i < arr.length - 1; i++)
		{
			String test = arr[i];
			if(test.equals(search1)|test.equals(search2))
			{
				String check = arr[i+1];
				boolean hasUpper = !check.equals(check.toLowerCase());
				if(hasUpper == true)
				{
					boolean hasPeriod = false;
					name = arr[i+1];
					int lgth = name.length();
					if(name.charAt(lgth-1) == per.charAt(0)) 
					{
						hasPeriod = true;
						StringBuilder sb = new StringBuilder(name);
						sb.deleteCharAt(lgth-1);
						name = sb.toString();
					}
					if(hasPeriod = true && (i+2) < arr.length)
					{
						String wordAfter = arr[i+2];
						boolean hasUppercase = !wordAfter.equals(wordAfter.toLowerCase());
						if(hasUppercase == true)
						{
							name = name + " " + arr[i+2];
						}
					}
				}
			}
			if(name.charAt(name.length()-1) == per.charAt(0))
			{
				StringBuilder sb = new StringBuilder(name);
				sb.deleteCharAt(name.length()-1);
				name = sb.toString();
			}
		}
		boolean hasMatch = false;			
		for(int i = 0; i < list.size(); i++)
		{
			if(name.equals(list.get(i).getName()))
			{
				hasMatch = true;
			}
		}
		if(hasMatch == false)
		{
			Station stat = new Station(name, 0);
			list.add(stat);
		}
		return name;
	}

	public static Type findType(String call)
	{
		String ret;
		//MEDICAL
		String searchMed1 = "having trouble breathing";
		String searchMed2 = "having a seizure";
		String searchMed3 = "passed out";
		String searchMed4 = "fell and broke something";
		String searchMed5 = "has a high fever";
		String searchMed6 = "had an accident with a knife";

		//VEHICLE
		String searchVeh1 = "a car accident";
		String searchVeh2 = "car broke";
		String searchVeh3 = "car won't start";
		String searchVeh4 = "car is stalled";
		String searchVeh5 = "have a flat tire";
		String searchVeh6 = "vehicle is dead";
		String searchVeh7 = "vehicle is stuck";

		//FACILITIES
		String searchFac1 = "power is out";
		String searchFac2 = "air conditioning stopped";
		String searchFac3 = "pipes are leaking";
		String searchFac4 = "broke a window";
		String searchFac5 = "roof is leaking";
		String searchFac6 = "front door lock broke";

		//ENVIRONMENTAL
		String searchEnv1 = "a gigantic hole in the dome";
		String searchEnv2 = "dome is falling down";
		String searchEnv3 = "had a marsquake";
		String searchEnv4 = "been struck by a meteorite";
	
		Type type = Type.MEDICAL;

		if(call.contains(searchMed1) | call.contains(searchMed2) | call.contains(searchMed3) | call.contains(searchMed4) | call.contains(searchMed5) | call.contains(searchMed5) | call.contains(searchMed6))
		{
			type = Type.MEDICAL;
		}
		else if(call.contains(searchVeh1) | call.contains(searchVeh2) | call.contains(searchVeh3) | call.contains(searchVeh4) | call.contains(searchVeh5) | call.contains(searchVeh6) | call.contains(searchVeh7))
		{
			type = Type.VEHICLE;
		}
		else if(call.contains(searchFac1) | call.contains(searchFac2) | call.contains(searchFac3) | call.contains(searchFac4) | call.contains(searchFac5) | call.contains(searchFac6))
		{
			type = Type.FACILITY;
		}
		else if(call.contains(searchEnv1) | call.contains(searchEnv2) | call.contains(searchEnv3) | call.contains(searchEnv4))
		{
			type = Type.ENVIRONMENT;
		}
		
		return type;
	}
	
	
	public static Priority findPriority(String call, Type type)
	{
		Priority prior = Priority.LOW;
		String searchMed = "MEDICAL";
		String searchVeh = "VEHICLE";
		String searchFac = "FACILITY";
		String searchEnv = "ENVIRONMENT";
		if(type.toString().equals(searchMed))
		{
			
			String search1 = "having trouble breathing";
			String search2 = "having a seizure";
			if(call.contains(search1) | call.contains(search2))
			{
				prior = Priority.HIGH;
			}
			else
			{
				prior = Priority.MEDIUM;
			}
		}
		if(type.toString().equals(searchVeh))
		{
			String search1 = "a car accident";
			if(call.contains(search1))
			{
				prior = Priority.HIGH;
			}
			else
			{
				prior = Priority.LOW;
			}
		}
		if(type.toString().equals(searchFac))
		{
			prior = Priority.LOW;
		}
		if(type.toString().equals(searchEnv))
		{
			prior = Priority.HIGH;
		}
		return prior;
	}
		
}
