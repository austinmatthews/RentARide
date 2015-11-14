package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.VehicleStatus;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class CommentIterator
    implements Iterator<Comment>
{
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;

    // these two will be used to create a new object
    //
    public CommentIterator( ResultSet rs, ObjectLayer objectLayer )
            throws RARException
    { 
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  // just in case...
            throw new RARException( "CommentIterator: Cannot create an iterator; root cause: " + e );
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Vehicle next() 
    {
    	Comment comment
    	Date commentDate;
    	String customerID;
    	int rentalNo;
    	String commentText;
    	Rental rental = null;
    	Customer customer =null;
    	
        
        
        if( more ) {

            try {
            	commentDate = rs.getDate("commentDate");
            	customerID = rs.getInt("customer");
            	rentalNo = rs.getInt("rental");
            	commentText = rs.getString("comment");
            	
                more = rs.next();
            }
            catch( Exception e ) {      // just in case...
                throw new NoSuchElementException( "CommentIterator: No next Comment object; root cause: " + e );
            }
            
            rental = objectLayer.createRental(rentalNo);
            customer = objectLayer.createCustomer(customerID);
            
            try {
                comment = objectLayer.createComment(commentText,rental,customer);
                
            }
            catch( RARException re ) {
                re.printStackTrace();
                System.out.println( re );
            }

            return comment;
        }
        else {
            throw new NoSuchElementException( "CommentIterator: No next Comment object" );
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}

