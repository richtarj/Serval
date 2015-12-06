/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportingTool.src;
import java.sql.*;
import java.util.Vector;
import reportingTool.src.Query_Object;
import reportingTool.src.Report_Object;
import reportingTool.src.ErrorDialog;

public class SQLiteJDBC {
    private Connection c;
    private boolean connected;
    
    boolean DEBUG = false;
    
    public SQLiteJDBC()
    {
    	connected = false;
    }
    
    /**
     * Attempts to open a connection to DB
     */
    public void GetConnection()
    {
    	try
    	{
    		Class.forName("org.sqlite.JDBC");
    		// change to one with slash when building .jar file
    		//c = DriverManager.getConnection("jdbc:sqlite:/database/serval.db");
            c = DriverManager.getConnection("jdbc:sqlite:database/serval.db");
            c.setAutoCommit(false);
            connected = true;
            if (DEBUG) { System.out.println("Opened database successfully"); }
    	}
    	catch (ClassNotFoundException ex)
    	{
    		System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
            System.exit(0);
    	}
    	catch (SQLException ex)
    	{
    		System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
    		System.err.println("Connection cannot be established.");
    		System.exit(1);
    	}
    }
    
    /**
     * Closes connection to DB
     */
    public void CloseConnection()
    {
    	try
    	{
    		c.close();
    		connected = false;
    	}
    	catch (SQLException ex)
    	{
    		System.err.println( ex.getClass().getName() + ": " + ex.getMessage() );
    		System.err.println("Connection cannot be closed.");
    		System.exit(1);
    	}
    }
    
    /**
     * Method: isConnected
     * 
     * checks status of connection to database
     * 
     * Returns:
     * 
     *   boolean - connection status
     */
    public boolean isConnected()
    {
    	return connected;
    }
    
    /**
     * Method: getQueries
     * 
     * Gets all of the queries stored in the database
     * of the given type
     * 
     * Parameters:
     * 
     *   String dbType - type of DB software to extract queries
     *                       for
     * 
     * Returns:
     * 
     *   Vector<Query_Object> - list of Query_Objects extracted.
     */
    public Vector<Query_Object> getQueries(String dbType){
        Vector<Query_Object> list = new Vector<Query_Object>();
        Query_Object query;
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
            		"select * from " +
            		"("+
            		"select m.title, m.intro, q.id, m.id meta_id, q.conditional_query, q.main_query," +
            		" m.conclusion, q.type from queries q join meta m on q.meta_id = m.id " +
            		")" +
            		"where type = \""+ dbType + "\";");
            while ( rs.next() ) {
                String mainQuery = rs.getString("main_query");
                String  conditionalQuery = rs.getString("conditional_query");
                String type  = rs.getString("type");
                int id = rs.getInt("id");
                int metaId = rs.getInt("meta_id");
                String title = rs.getString("title");
                String intro = rs.getString("intro");
                String conclusion = rs.getString("conclusion");
                
                query = new Query_Object(
                	title, mainQuery, type, conditionalQuery, id,
                	intro, conclusion, metaId);
                list.add(query);
            }
            rs.close();
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(1);
        }
        if (DEBUG) { System.out.println("Queries successfully read in."); } 
        return list;
    }
    
    /**
     * Method: SaveNewQuery
     * 
     * Saves a new query to the database
     * 
     * Parameters:
     * 
     *   Query_Object q - Query_Object containing the info
     *                        to write
     * 
     * Returns:
     * 
     *   boolean - indication of success
     */
    public boolean SaveNewQuery(Query_Object q)
    {
    	try
    	{
            Statement s = c.createStatement();
            s.execute(
            		"insert into meta(title, intro, conclusion)" +
            		"values(\""+q.getTitle()+"\", \""+q.getIntro()+"\", \""+q.getConclusion()+"\");"
            		);
            ResultSet rs = s.executeQuery(
            		"select id from meta where title = \""+q.getTitle()+"\";"
            		);
            int meta_id = rs.getInt("id");
            s.execute(
            		"insert into queries(meta_id, main_query, conditional_query, type)" +
            		"values( "+ meta_id +", \""+ q.getMainQuery() +"\", \""+ q.getConditionalQuery() +
            		"\", \""+ q.getType() + "\");"
            		);
            c.commit();
            rs.close();
            s.close();
            return true;
    	}
    	catch (SQLException ex)
    	{
    		String message = ex.getMessage();
    		System.err.println( ex.getClass().getName() + ": " + message );
    		if (message.startsWith("[SQLITE_CONSTRAINT]")) // One or more constraints on database
    		{
    			if (message.contains("meta.title")) // Error with title
    			{
    				if (message.contains("NOT NULL")) // Violates not null constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Query must have a title.");
    					d.ShowDialog();
    				}
    				else // Violates unique constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Query title must be unique.");
        				d.ShowDialog();
    				}
    			}
    			if(message.contains("queries.main_query")) // Error with main query only constraint
    			{ 										   // is not null
    				ErrorDialog d = new ErrorDialog("Main query cannot be empty.");
    				d.ShowDialog();
    			}
    		}
    		return false;
    	}
    }
    
    /**
     * Method: UpdateExistingQuery
     * 
     * Updates the information for a query that already exists in 
     * the database
     * 
     * Parameters:
     * 
     *   Query_Object q - Query_Object containing info to write
     * 
     * Returns:
     * 
     *   boolean - indication of success
     */
    public boolean UpdateExistingQuery(Query_Object q)
    {
    	try
    	{
            Statement s = c.createStatement();
            s.execute(
            		"update meta " +
            		"set title = \""+q.getTitle()+"\", intro=\""+q.getIntro()+"\", conclusion=\""+q.getConclusion()+"\" "+
            		"where id="+q.getMetaID()
            		);
            s.execute(
            		"update queries " +
            		"set meta_id="+ q.getMetaID() +", main_query=\""+ q.getMainQuery() +"\", " +
            		"conditional_query=\""+ q.getConditionalQuery() +"\", type=\""+ q.getType() + "\" "+
            		"where id=" + q.getID()
            		);
            c.commit();
            s.close();
            return true;
    	}
    	catch (SQLException ex)
    	{
    		String message = ex.getMessage();
    		System.err.println( ex.getClass().getName() + ": " + message );
    		if (message.startsWith("[SQLITE_CONSTRAINT]")) // One or more constraints on database
    		{
    			if (message.contains("meta.title")) // Error with title
    			{
    				if (message.contains("NOT NULL")) // Violates not null constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Failed to add: Query must have a title.");
    					d.ShowDialog();
    				}
    				else // Violates unique constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Failed to add: Query title must be unique.");
        				d.ShowDialog();
    				}
    			}
    			if(message.contains("queries.main_query")) // Error with main query only constraint
    			{ 										   // is not null
    				ErrorDialog d = new ErrorDialog("Failed to add: Main query cannot be empty.");
    				d.ShowDialog();
    			}
    		}
    		return false;
    	}
    }
    
    /**
     * Method: GetReports
     * 
     * Queries the database to get all reports saved 
     * there of given type
     * 
     * Parameters:
     * 
     *   String type - type of DB software to get
     *                 	reports for
     * 
     * Returns:
     * 
     *   Vector<Report_Object> - Vector of all reports found
     */
    public Vector<Report_Object> GetReports(String type)
    {
    	try
    	{
    		Vector<Report_Object> v = new Vector<Report_Object>();
    		Report_Object r;
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(
            		"select * from reports "+
            		"where type = \""+ type + "\";");
            while ( rs.next() ) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String queries = rs.getString("queries");
                String t = rs.getString("type");
                r = new Report_Object(id, title, queries, t);
                v.add(r);
            }
            rs.close();
            stmt.close();
            return v;
    	}
    	catch (SQLException ex)
    	{
    		String message = ex.getMessage();
    		System.err.println( ex.getClass().getName() + ": " + message );
    		return null;
    	}
    }
    
    /**
     * Method: SaveNewReport
     * 
     * Saves a new report to the database
     * 
     * Parameters:
     * 
     *   Report_Object r - Report_Object to be written
     * 
     * Returns:
     * 
     *   boolean - indication of success
     */
    public boolean SaveNewReport(Report_Object r)
    {
    	try
    	{
            Statement s = c.createStatement();
            s.execute(
            		"insert into reports(title, queries, type)" +
            		"values(\""+r.getTitle()+"\", \""+r.createQueriesString()+"\", \""+r.getType()+"\");"
            		);
            c.commit();
            s.close();
            return true;
    	}
    	catch (SQLException ex)
    	{
    		String message = ex.getMessage();
    		System.err.println( ex.getClass().getName() + ": " + message );
    		if (message.startsWith("[SQLITE_CONSTRAINT]")) // One or more constraints on database
    		{
    			if (message.contains("reports.title")) // Error with title
    			{
    				if (message.contains("NOT NULL")) // Violates not null constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Report must have a title.");
    					d.ShowDialog();
    				}
    				else // Violates unique constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Report title must be unique.");
        				d.ShowDialog();
    				}
    			}
    		}
    		return false;
    	}
    }
    
    /**
     * Method: UpdateExistingReport
     * 
     * Updates an existing report in the database
     * 
     * Parameters:
     * 
     *   Report_Object r - Report_Object to be written
     * 
     * Returns:
     * 
     *   boolean - indication of success
     */
    public boolean UpdateExistingReport(Report_Object r)
    {
    	try
    	{
            Statement s = c.createStatement();
            s.execute(
            		"update reports " +
            		"set title = \""+r.getTitle()+"\", queries=\""+r.createQueriesString()+"\", type=\""+r.getType()+"\" "+
            		"where id="+r.getID()
            		);
            c.commit();
            s.close();
            return true;
    	}
    	catch (SQLException ex)
    	{
    		String message = ex.getMessage();
    		System.err.println( ex.getClass().getName() + ": " + message );
    		if (message.startsWith("[SQLITE_CONSTRAINT]")) // One or more constraints on database
    		{
    			if (message.contains("reports.title")) // Error with title
    			{
    				if (message.contains("NOT NULL")) // Violates not null constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Report must have a title.");
    					d.ShowDialog();
    				}
    				else // Violates unique constraint on database
    				{
    					ErrorDialog d = new ErrorDialog("Report title must be unique.");
        				d.ShowDialog();
    				}
    			}
    		}
    		return false;
    	}
    }
    
    /**
     * Method: GetSelectedQueries
     * 
     * gets the queries with the supplied ids 
     * from the database
     * 
     * Parameters:
     * 
     *   Vector<Integer> v - vector containing ids of 
     *                       	queries need
     * 
     * Returns:
     * 
     *   Vector<Query_Object> - vector of query objects
     */
    public Vector<Query_Object> GetSelectedQueries(Vector<Integer> v)
    {
    	try
    	{
    		Vector<Query_Object> l = new Vector<Query_Object>();
    		StringBuilder sb = new StringBuilder();
    		sb.append("(");
    		int len = v.size();
    		for (int i = 0; i < len; i++)
    		{
    			sb.append(v.get(i) + ", ");
    		}
    		sb.deleteCharAt(sb.length() - 1);
    		sb.deleteCharAt(sb.length() - 1);
    		sb.append(")");
            Statement stmt = c.createStatement();
            Query_Object query;
            ResultSet rs = stmt.executeQuery(
            		"select * from " +
            		"("+
            		"select m.title, m.intro, q.id, m.id meta_id, q.conditional_query, q.main_query," +
            		" m.conclusion, q.type from queries q join meta m on q.meta_id = m.id " +
            		")" +
            		"where id in "+ sb.toString() + ";");
            while ( rs.next() ) {
                String mainQuery = rs.getString("main_query");
                String  conditionalQuery = rs.getString("conditional_query");
                String type  = rs.getString("type");
                int id = rs.getInt("id");
                int metaId = rs.getInt("meta_id");
                String title = rs.getString("title");
                String intro = rs.getString("intro");
                String conclusion = rs.getString("conclusion");
                
                query = new Query_Object(
                	title, mainQuery, type, conditionalQuery, id,
                	intro, conclusion, metaId);
                l.add(query);
            }
            rs.close();
            stmt.close();
            return l;
    	}
    	catch (SQLException ex)
    	{
    		String message = ex.getMessage();
    		System.err.println( ex.getClass().getName() + ": " + message );
    		return null;
    	}
    }
}
