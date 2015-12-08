
//
// A control class to implement the 'View customer info' use case
//
//


package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.uga.cs.RARException;
import edu.uga.uga.cs.entity.RentalLocation;
import edu.uga.uga.cs.object.ObjectLayer;


public class ViewCustomerInfoCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public ViewCustomerInfoCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }

    public List<User> ViewCustomerInfo()
            throws RARException
    {
        List<User> 	    users  = null;
        Iterator<User> 	userIter = null;
        User     	      user = null;

        users = new LinkedList<User>();
        
        // retrieve all User objects
        //
        userIter = objectLayer.ViewCustomerInfo( null );
        while( userIter.hasNext() ) {
            user = userIter.next();
            System.out.println( user);
            users.add( user );
        }

        return users;
    }
}
