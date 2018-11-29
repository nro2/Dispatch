public class Station
{
	private int requestToday;
	private String name;

	public Station(String name, int requestToday)
	{
		this.name = name;
		this.requestToday = requestToday;
	}


	public String getName()
	{
		return this.name;
	}
	
	public int getRequest()
	{
		return this.requestToday;
	}

	public void setName(String nm)
	{
		this.name = nm;
	}
	
	public void setRequest(int req)
	{
		this.requestToday = req;
	}


}
