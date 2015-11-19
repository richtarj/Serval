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
    
    public Stack<queryObject> getQueries(String dbType){
        Stack<queryObject> queries = new Stack<queryObject>();
        queryObject query;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:seval_modified.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM queries;" );
            while ( rs.next() ) {
                String mainQuery = rs.getString("main_query");
                String  conditionalQuery = rs.getString("conditional_query");
                String type  = rs.getString("type");
                int id = rs.getInt("id");
                int metaId = rs.getInt("meta_id");
                query = new queryObject(mainQuery,type,conditionalQuery,id,metaId);
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
    
    public void addQueries(Stack<queryObject> queries){
        ResultSet rs;
        try {
            stmt = c.createStatement();
            String sql;
            queryObject query;
            while (!queries.empty()){
                query = queries.pop();
                rs = stmt.executeQuery( "SELECT * FROM meta WHERE id = \"" + query.getID() + "\"" );
                if (rs.next() == false){
                    sql = "INSERT INTO QUERIES (main_query,conditional_query,type,id,meta_id) " +
                    "VALUES (" + query.getMainquery() + "," + query.getConditionalQuery() + "," + 
                    query.getType() + "," + query.getID() + "," + query.getMetaID() + ")";
                    stmt.executeUpdate(sql);
                }
                // @TODO
                // if (rs.next() == true)
                // -add an update function to update if the id is already there
                // -add a modal dialog for user to confirm any overwrites
            }
            stmt.close();
        } catch (Exception e){
            
        }
    }
}
