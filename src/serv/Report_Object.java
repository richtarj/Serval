package reportingTool.src;

import java.util.Vector;

public class Report_Object 
{
	// region Properties
	
	private String title;
	private Vector<Integer> queries;
	private String queryString;
	private String type;
	private int id;
	private boolean DEBUG = false;
	
	//endregion
	
	// region Constructors
	
	/**
	 * Default no argument constructor
	 */
	public Report_Object()
	{
		// Default no param constructor
	}
	
	/**
	 * Constructor: Report_Object
	 * 
	 * Builds a fully populated Report_Object
	 * 
	 * Parameters:
	 * 
	 * 	 int i     - id of report
	 *   String t  - title of report
	 *   String q  - CSV string of query indexes
	 *   String ty - Type of database (SQLite, MySQL, ...)
	 * 
	 */
	public Report_Object(int i, String t, String q, String ty)
	{
		queries = new Vector<Integer>();
		queryString = q;
		this.parseString(q);
		id = i;
		title = t;
		type = ty;
	}
	
	/**
	 * Constructor: Report_Object
	 * 
	 * Partially populated constructor for when saving
	 * a new report and query id's can already be
	 * placed in a vector, and id is unknown    
	 * 
	 * Parameters:
	 * 
	 *   String t          - title of report 
	 *   Vector<Integer> q - vector of query ids
	 *   String ty         - database type
	 * 
	 */
	public Report_Object(String t, Vector<Integer> q, String ty)
	{
		queries = new Vector<Integer>();
		queries.addAll(q);
		queryString = createQueriesString();
		title = t;
		type = ty;
		if (DEBUG) 
		{
			System.out.println("QUERIES:");
			for (int i = 0; i < queries.size(); i++)
			{
				System.out.println(queries.get(i));
			}
			System.out.println("Query String:");
			System.out.println(queryString);
		}
	}
	
	// endregion
	
	/**
	 * Method: parseString
	 * 
	 * parses a CSV string of indexes to store
	 * in queries as a Vector<Integer>, also
	 * populates the queries property from within 
	 * the method
	 * 
	 * Parameters:
	 * 
	 *   String s - CSV string to be parsed
	 * 
	 */
	private void parseString(String s)
	{
		Vector<Integer> v = new Vector<Integer>();
		String[] l = s.split(",");
		int len = l.length;
		for (int i = 0; i < len; i++)
		{
			try
			{
				v.add(Integer.parseInt(l[i]));
			}
			catch(NumberFormatException ex)
			{
				System.err.printf("Character: %s at position %d could not be parsed. Skipping.\n", l[i], i);
			}
		}
		queries.addAll(v);
	}
	
	/**
	 * Method: createString
	 * 
	 * creates a CSV string from queries object
	 * 
	 * Returns:
	 * 
	 *   String - a CSV string of query indexes
	 */
	public String createQueriesString()
	{
		StringBuilder sb = new StringBuilder();
		int l = queries.size();
		if (l == 0)
		{
			System.err.println("Cannot write report with 0 queries.");
			return null;
		}
		else
		{
			for (int i = 0; i < l; i++)
			{
				sb.append(queries.get(i).toString() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
	}
	
	// region Getters
	
	public int getID()
	{
		return id;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public Vector<Integer> getQueries()
	{
		return queries;
	}
	
	public String getType()
	{
		return type;
	}
	
	// endregion
	
	// region Setters
	
	public void setID(int arg)
	{
		id = arg;
	}
	
	public void setTitle(String arg)
	{
		title = arg;
	}
	
	public void setQueries(Vector<Integer> arg)
	{
		queries = arg;
	}
	
	public void setType(String arg)
	{
		type = arg;
	}
	
	// endregion
}
