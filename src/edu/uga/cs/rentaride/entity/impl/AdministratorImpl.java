package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.entity.RARException;
import edu.uga.cs.rentaride.entity.Administrator;
import edu.uga.cs.rentaride.entity.User;

/** This class represents an Administrator user.  It has no additional attributes, and all are inherited from User.
 *
 */
public class AdministratorImpl
    extends User      //Dont know if this is right becuase Admin etends User
    implements Administrator
{
    //still need to implement the constructor!!
    //use the setters from the User class
    //this class does not extend persistent so figure out how to set persistence to (-1)
}
