
//
// A control class to implement the 'View customer info' use case
//
//


package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.uga.cs.RARException;
import edu.uga.uga.cs.entity.Customer;
import edu.uga.uga.cs.object.ObjectLayer;


public class ViewCustomerInfoCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public ViewCustomerInfoCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }

    public List<User> ViewCustomerInfo(String username)
            throws RARException
    {
        List<Customer> 	    customers  = null;
        Iterator<Customer> 	customerIter = null;
        Customer     	    customer = null;

        customers = new LinkedList<Customer>();
        
        // retrieve all Customer objects
        //
        Customer modelCustomer = objectLayer.createCustomer(null,null,null,null,null,null,null,null,null,username,null,null,null);
        customerIter = objectLayer.findCustomer( modelCustomer );
        while( customerIter.hasNext() ) {
            customer = customerIter.next();
            System.out.println( customer);
            customers.add( customer );
        }

        return customers;
    }
}
