/*
Name: Fahim Zubaer
title: Two-Tier Client-Server Application Development With MySQL and JDBC
*/

package project3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// A TableModel that supplies ResultSet data to a JTable.
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;


// ResultSet rows and columns are counted from 1 and JTable 
// rows and columns are counted from 0. When processing 
// ResultSet rows or columns for use in a JTable, it is 
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is 
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
public class ResultSetTableModel extends AbstractTableModel 
{
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
        private boolean connectedToDatabase = false;

	// Constructs the object that will contain the results from the SQL query
	public ResultSetTableModel( String driver, String query ) throws SQLException, ClassNotFoundException
	{
		if ( checkQuery( query ) )
		{
			setQuery( query );
		}
		else
		{
			setUpdate( query );
		}	
	}
	
	// 
	public static boolean checkQuery(String proposedQuery)
	{
		if ( proposedQuery.toLowerCase().contains("update") || proposedQuery.toLowerCase().contains("insert") || 
				proposedQuery.toLowerCase().contains("delete") ) { return false; }
		else { return true; }
	}
	
	// Get the class that represents column type
	@SuppressWarnings({ "unchecked", "rawtypes" })

   // get class that represents column type
   public Class getColumnClass( int column ) throws IllegalStateException
   {
      // ensure database connection is available
     // if ( !connectedToDatabase ) 
      //   throw new IllegalStateException( "Not Connected to Database" );

      // determine Java class of column
      try 
      {
         String className = metaData.getColumnClassName( column + 1 );
         
         // return Class object that represents className
         return Class.forName( className );
      } // end try
      catch ( Exception exception ) 
      {
         exception.printStackTrace();
      } // end catch
      
      return Object.class; // if problems occur above, assume type Object
   } // end method getColumnClass

   // get number of columns in ResultSet
   public int getColumnCount() throws IllegalStateException
   {   
      // ensure database connection is available
     // if ( !connectedToDatabase ) 
     //    throw new IllegalStateException( "Not Connected to Database" );

      // determine number of columns
      try 
      {
         return metaData.getColumnCount(); 
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return 0; // if problems occur above, return 0 for number of columns
   } // end method getColumnCount

   // get name of a particular column in ResultSet
   public String getColumnName( int column ) throws IllegalStateException
   {    
      // ensure database connection is available
      //if ( !connectedToDatabase ) 
      //   throw new IllegalStateException( "Not Connected to Database" );

      // determine column name
      try 
      {
         return metaData.getColumnName( column + 1 );  
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string for column name
   } // end method getColumnName

   // return number of rows in ResultSet
   public int getRowCount() throws IllegalStateException
   {      
      // ensure database connection is available
     // if ( !connectedToDatabase ) 
     //    throw new IllegalStateException( "Not Connected to Database" );
 
      return numberOfRows;
   } // end method getRowCount

   // obtain value in particular row and column
   public Object getValueAt( int row, int column ) 
      throws IllegalStateException
   {
      // ensure database connection is available
     // if ( !connectedToDatabase ) 
     //    throw new IllegalStateException( "Not Connected to Database" );

      // obtain a value at specified ResultSet row and column
      try 
      {
		   resultSet.next();  /* fixes a bug in MySQL/Java with date format */
         resultSet.absolute( row + 1 );
         return resultSet.getObject( column + 1 );
      } // end try
      catch ( SQLException sqlException ) 
      {
         sqlException.printStackTrace();
      } // end catch
      
      return ""; // if problems, return empty string object
   } // end method getValueAt
   
   // set new database query string
   public void setQuery( String query ) 
      throws SQLException, IllegalStateException 
   {
      // ensure database connection is available
    //  if ( !connectedToDatabase ) 
     //    throw new IllegalStateException( "Not Connected to Database" );

      // specify query and execute it
      resultSet = GuiInterface.getStatement().executeQuery( query );

      // obtain meta data for ResultSet
      metaData = resultSet.getMetaData();

      // determine number of rows in ResultSet
      resultSet.last();                   // move to last row
      numberOfRows = resultSet.getRow();  // get row number      
      
      // notify JTable that model has changed
      fireTableStructureChanged();
   } // end method setQuery


// set new database update-query string
   public void setUpdate( String query ) 
      throws SQLException, IllegalStateException 
   {
	    int res;
      // ensure database connection is available
      //if ( !connectedToDatabase ) 
      //   throw new IllegalStateException( "Not Connected to Database" );

      // specify query and execute it
      res = GuiInterface.getStatement().executeUpdate( query );
/*
      // obtain meta data for ResultSet
      metaData = resultSet.getMetaData();

      // determine number of rows in ResultSet
      resultSet.last();                   // move to last row
      numberOfRows = resultSet.getRow();  // get row number      
  */    
      // notify JTable that model has changed
      fireTableStructureChanged();
   } // end method setUpdate




   // close Statement and Connection               
   public void disconnectFromDatabase()            
   {              
      //if ( !connectedToDatabase )                  
       //  return;

      // close Statement and Connection            
      try                                          
      {                                            
    	  GuiInterface.getStatement().close();                        
        //sqlClient.isConnectedToDatabaseclose();                       
      } // end try                                 
      catch ( SQLException sqlException )          
      {                                            
         sqlException.printStackTrace();           
      } // end catch                               
      finally  // update database connection status
      {                                            
         connectedToDatabase = false;              
      } // end finally                             
   } // end method disconnectFromDatabase          
}  // end class ResultSetTableModel




