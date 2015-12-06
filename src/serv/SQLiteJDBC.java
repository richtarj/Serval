/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serv;
import java.sql.*;
import java.util.Stack;

public class SQLiteJDBC {
    Connection c = null;
    Statement stmt = null;
    
    public SQLiteJDBC(){
    }
    
    public Stack<Query_Object> getQueries(String dbType){
        Stack<Query_Object> queries = new Stack<Query_Object>();
        Query_Object query;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:seval_modified.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM queries;" );
            while ( rs.next() ) {
            	String title = rs.getString("title");
                String mainQuery = rs.getString("main_query");
                String  conditionalQuery = rs.getString("conditional_query");
                String type  = rs.getString("type");
                int id = rs.getInt("id");
                int metaId = rs.getInt("meta_id");
                String introtxt = rs.getString("introtxt");
                String conclusiontxt = rs.getString("conclusiontxt");
                query = new Query_Object(title,mainQuery,type,conditionalQuery,id,introtxt,conclusiontxt,metaId);
                queries.push(query);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        
        return queries;
    }
    
    public void addQueries(Stack<Query_Object> queries){
        ResultSet rs;
        try {
            stmt = c.createStatement();
            String sql;
            Query_Object query;
            while (!queries.empty()){
                query = queries.pop();
                rs = stmt.executeQuery( "SELECT * FROM meta WHERE id = \"" + query.getID() + "\"" );
                if (rs.next() == false){
                    sql = "INSERT INTO QUERIES (main_query,conditional_query,type,id,meta_id) " +
                    "VALUES (" + query.getMainQuery() + "," + query.getConditionalQuery() + "," + 
                    query.getType() + "," + query.getID() + "," + query.getMetaID() + ")";
                    stmt.executeUpdate(sql);
                }
                else{
                	sql = "UPDATE queries SET main_query=" + query.getMainQuery() + ", conditional_query=" + query.getConditionalQuery();
                	stmt.executeUpdate(sql);
                }
            }
            stmt.close();
        } catch (Exception e){
            
        }
    }
}
