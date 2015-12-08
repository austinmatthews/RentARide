//
// A control class to implement the 'Create Customer' use case
//
//

package edu.uga.cs.rentaride.logic.impl;


import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;



public class CreateCustomerCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateCustomerCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
    
    public long createCustomer( String firstName, String lastName, String userName, String emailAddress, 
            String password, String licenseState, 
            String licenseNumber, String residenceAddress, String cardNumber, Date cardExpiration  )
            throws RARException
    { 
        Customer 	            customer  = null;
        Customer              modelCustomer = null;
        Iterator<Customer>    customerIter = null;

        // check if a customer with this name already exists (name is unique)
        modelCustomer = objectLayer.createCustomer();
        modelCustomer.setUserName( userName );
        customerIter = objectLayer.findCustomer( modelCustomer );
        if( customerIter.hasNext() )
            throw new RARException( "A customer with this name already exists: " + userName );

        Date createDate = new Date("12-08-2015");
        Date membershipExpiration = new Date("06-08-2016");
        
        // create the rentalLocation
        rentalLocation = objectLayer.createCustomer( String firstName, String lastName, String userName, String emailAddress, 
            String password, Date createDate, Date membershipExpiration, String licenseState, 
            String licenseNumber, String residenceAddress, String cardNumber, Date cardExpiration );
        objectLayer.storeCustomer( customer );

        return customer.getId();
    }
}
