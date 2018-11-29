public class Emergency
{
	private String name;
	private Type type;
	private Priority priority;
	private int id;

	public String getName()
	{
		return this.name;
	}
	
	public Type getType()
	{
		return this.type;
	}
	
	public Priority getPriority()
	{
		return this.priority;
	}

	public int getId()
	{
		return this.id;
	}

	public void setName(String nm)
	{
		this.name = nm;
	}

	public void setType(Type tp)
	{
		this.type = tp;
	}
	
	public void setPriority(Priority prior)
	{
		this.priority = prior;
	}	
	
	public void setId(int ide)
	{
		this.id = ide;
	}
}





