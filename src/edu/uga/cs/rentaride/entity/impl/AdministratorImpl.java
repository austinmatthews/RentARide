package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.entity.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.entity.UserStatus;

/** This class represents an Administrator user.  It has no additional attributes, and all are inherited from User.
 *
 */
public class AdministratorImpl
    extends User      
    implements Administrator
{
    public AdministratorImpl(String fName, String lName, String uName, 
                    String email, String password, Date createDate, UserStatus userStatus){
         super(fName, lName, uName, email, password, createDate, userStatus);
     }
}
