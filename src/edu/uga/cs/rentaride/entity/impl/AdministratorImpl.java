package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.entity.impl.*;
import edu.uga.cs.rentaride.entity.*;

import java.util.Date;

/** This class represents an Administrator user.  It has no additional attributes, and all are inherited from User.
 *
 */
public class AdministratorImpl
    extends UserImpl      
    implements Administrator
{
    public AdministratorImpl(String fName, String lName, String uName, 
                    String email, String password, Date createDate, String userStatus,String address){
         
    		super(fName, lName, uName, email, password, createDate, userStatus,address);
     }
}
