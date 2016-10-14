//employee object//

import java.sql.*;

import javax.swing.plaf.synth.SynthSeparatorUI;
public class employee {
	/* Employee object attributes
	 * to be filled in by the 
	 * manager or administrator
	 */
	private String FirstName;
	private String LastName;
	private String Username;
	private String Password;
	private Boolean isAdmin; //note: boolean type//
	/* to be generated by the system */
	private String UserID; //revised to a String type//
	
	public employee(String first, String last, String user, String p, Boolean isAd ){
		this.FirstName = first;
		this.LastName = last;
		this.Username = user;
		this.Password = p;
		this.isAdmin = isAd;
	}
	/* Creates an Employee object to be
	 * sent to the database.
	 * 
	 * @precondition:
	 * FillInCheck(p)
	 * all relevant Employee 
	 * attributes like FirstName, LastName,
	 * etc. have to be provided as parameters.
	 * 
	 * @postcondition:
	 * Employee object is created and sent to 
	 * the database.
	 */

	
	//private void Create() {...}
	
	/* Deletes an existing Employee
	 * from the database.
	 * 
	 * @precondition:
	 * CheckIfExists()
	 * The relevant Employee has to
	 * exist within the database.
	 * 
	 * @postcondition:
	 * Employee object is created and sent to 
	 * the database
	 */
	
	//private void Delete(Employee e) {...}
	
	/* Edits an existing Employee's
	 * information in the database.
	 * 
	 * @precondition:
	 * CheckIfExists()
	 * The relevant Employee has to
	 * exist within the database.
	 * 
	 * @postcondition:
	 * Employee object is updated 
	 * within the database.
	 */
	
	public static void main( String args[] )
	{
	  Connection c = null;
	  Statement stmt = null;
	  
	  try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:PhoenixHotel.db");
	    c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	  
	      
	      stmt = c.createStatement();
	      String sql = "DELETE from HOTELROOM where ID=2;";
	      stmt.executeUpdate(sql);
	      c.commit();
	     /* ResultSet rs = stmt.executeQuery( "SELECT * FROM EMPLOYEE;" );
	      while ( rs.next() ) {
	    	  String firstName= rs.getString("FIRSTNAME");
	    	  String lastName= rs.getString("LASTNAME");
	    	  String userName= rs.getString("USERNAME");
	    	  String password= rs.getString("PASSWORD");
	    	  String uid= rs.getString("UID");
	    	  int isAdmin= rs.getInt("ISADMIN");
	    	  
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	    	  System.out.println("FIRSTNAME: " + firstName);
	    	  
	    	  System.out.println("LASTNAME: " + lastName);
	      }
	      rs.close(); */
	      
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM HOTELROOM;" );
	      while ( rs.next() ) {
	         int id = rs.getInt("id");
	         String  name = rs.getString("name");
	         int age  = rs.getInt("age");
	         String  address = rs.getString("address");
	         float salary = rs.getFloat("salary");
	         System.out.println( "ID = " + id );
	         System.out.println( "NAME = " + name );
	         System.out.println( "AGE = " + age );
	         System.out.println( "ADDRESS = " + address );
	         System.out.println( "SALARY = " + salary );
	         System.out.println();
	      }
	      rs.close();
	      stmt.close(); 

	    
	    
	      c.close();
	  
	  } catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	  }
	  System.out.println("Records created successfully");
	}
	
	
	public String getFirstName() {
		return FirstName;
	}


	public String getLastName() {
		return LastName;
	}
	
	public String getUsername() {
		return Username;
	}


	public String getPassword() {
		return Password;
	}


	public Boolean getIsAdmin() {
		return isAdmin;
	}


	public String getUserID() {
		return UserID;
	}


	public void setFirstName(String firstName) {
		FirstName = firstName;
	}


	public void setLastName(String lastName) {
		LastName = lastName;
	}


	public void setUsername(String username) {
		Username = username;
	}


	public void setPassword(String password) {
		Password = password;
	}


	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}


	//done later//
	//private void Edit(String[] params) {...}
	
	/* Other methods omitted */
	























	
	
}
