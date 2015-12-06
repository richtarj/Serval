package reportingTool.src;

public class Query_Object{
   
	// region Properties
	private String title;
	private String main_query;
	private String type;
	private String conditional_query;
	private int id;
	private int meta_id;
	private String intro;
	private String conclusion;
	// endregion

	
	// region Constructors
	/**
	 * Constructor: Query_Object
	 * 
	 * Creates an empty Query_Object object
	 *
	 */
	public Query_Object()
	{
	    title = "";
	    main_query = "";
	    type = "";
	    conditional_query = "";
	    id = -1;
	    meta_id = -1;
	}
   
	/**
	 * Constructor: Query_Object
	 * 
	 * Creates a fully populated Query_Object
	 * 
	 * Parameters:
	 * 
	 *   String t             - Title of the query
	 *   String main          - Main query to be run
	 *   String newType       - Type of database associated with
	 *                        	query
	 *   String conditional   - Condition query to be ran before
	 *                        	main query
	 *   int newId            - Id from database
	 *   String introtxt      - Intro metadata from database
	 *   String conclusiontxt - Conclusion metadata from database
	 *   int meta_id	      - Id of metadata from database
	 * 
	 */
	public Query_Object(String t, String main, String newType, 
			String conditional, int newId, String introtxt, 
			String conclusiontxt, int meta)
	{
	   title = t;
	   main_query = main;
	   type = newType;
	   conditional_query = conditional;
	   id = newId;
	   meta_id = meta;
	   intro = introtxt;
	   conclusion = conclusiontxt;
	}
	//endregion

	// region Getters
	public String getTitle()
	{
		return title;
	}
   
	public String getMainQuery()
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
	
	public String getIntro()
	{
		return intro;
	}
	
	public String getConclusion()
	{
		return conclusion;
	}
	
	// endregion
	
	// region Setters
   
	public void setTitle(String arg)
	{
	   title = arg;
	}
   
	public void setMainQuery(String arg)
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
    
    public void setIntro(String arg)
    {
    	intro = arg;
    }
    
    public void setConclusion(String arg)
    {
    	conclusion = arg;
    }
    // endregion
    
    public String toString()
    {
    	return title;
    }
}