
/** Java class "Player.java" generated from Poseidon for UML.
 *  Poseidon for UML is developed by <A HREF="http://www.gentleware.com">Gentleware</A>.
 *  Generated with <A HREF="http://jakarta.apache.org/velocity/">velocity</A> template engine.
 */
package py.edu.uca.fcyt.game;

import py.edu.uca.fcyt.toluca.*;

import java.util.*;

//package py.edu.uca.fcyt.toluca;
public class Player {

  ///////////////////////////////////////
  // attributes

    protected String name; 
    private String fullName; 
    private String pass; 
    private String email; 
    protected int rating; 

  ///////////////////////////////////////
  // operations


	public Player(String name, int rating) 
	{
		this.name = name;
		this.rating = rating;
	}
	
    public Player (){}
    public Player (String name){ this.name = name; }
    public String getName() { return name; } // end getName        
    public void setName(String _name) {        
        name = _name;
    } // end setName        

    public String getFullName() {        
        return fullName;
    } // end getFullName        

    public void setFullName(String _fullName) {        
        fullName = _fullName;
    } // end setFullName        

    public String getPass() {        
        return pass;
    } // end getPass        

    public void setPass(String _pass) {        
        pass = _pass;
    } // end setPass        

    public String getEmail() {        
        return email;
    } // end getEmail        

    public void setEmail(String _email) {        
        email = _email;
    } // end setEmail        
    
    public int getRating() { return rating; }
    
    public void setRating(int rating) 
    { 
    	this.rating = rating;
    }

} // end Player



