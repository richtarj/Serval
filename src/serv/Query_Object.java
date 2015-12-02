package reportingTool.src;

public class Query_Object{
   
	// region Properties
	private String title;
	private String main_query;
	private String type;
	private String conditional_query;
	private int id;
	private int meta_id;
	// endregion

	
	// region Constructors
	public Query_Object()
	{
		
	    title = "";
	    main_query = "";
	    type = "";
	    conditional_query = "";
	    id = -1;
	    meta_id = -1;
	}
   
	public Query_Object(String t, String main, String newType, String conditional, int newId, int meta)
	{
	   title = t;
	   main_query = main;
	   type = newType;
	   conditional_query = conditional;
	   id = newId;
	   meta_id = meta;
	}
	//endregion

	// region Getters/Setters
	public String getTitle()
	{
		return title;
	}
   
	public String getMainquery()
	{
		return main_query;
	}
   
	public String getType()
	{
		return type;
	}
   
	public String getConditionalQuery()
	{
		return conditional_query;
	}
   
	public int getID()
	{
		return id;
	}
   
	public int getMetaID()
	{
		return meta_id;
	}
   
	public void setTitle(String arg)
	{
	   title = arg;
	}
   
	public void setMainquery(String arg)
	{
		main_query = arg;
	}
   
	public void setType(String arg)
	{
		type = arg;
	}
   
	public void setConditionalQuery(String arg)
	{
		conditional_query = arg;
	}
   
	public void setID(int arg)
	{
		id = arg;
	}
   
    public void setMetaID(int arg)
    {
    	meta_id = arg;
    }
    // endregion
}