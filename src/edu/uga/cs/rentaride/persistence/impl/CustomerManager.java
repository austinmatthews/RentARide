
package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;


class CustomerManager
{
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;

    public CustomerManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void save(Customer customer)
            throws RARException
    {
        String               insertCSql = "insert into Customer ( creditCardExpiration, creditCardNumber, licenseNumber, licenseState, membershipExpiration) values ( ?, ?, ?, ?, ?)";
        String               updateCSql = "update Customer set creditCardExpiration = ?, creditCardNumber = ?, licenseNumber = ?, licenseState = ?, membershipExpiration = ? where id = ? ";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 CId;


        try {

            if( !customer.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertCSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateCSql );

            if( customer.getCreditCardExpiration() != null ) // type is unique and non null
            	// This is me setting the value in col typeName in table VehicleType to the provided value
            	stmt.setDate( 1, (Date) customer.getCreditCardExpiration());
            else
                throw new RARException( "Customer.save: can't save a Customer: Customer undefined" );

            if( customer.getCreditCardNumber() != null )
                stmt.setString( 2, customer.getCreditCardNumber() );
            else
                throw new RARException( "customer.save: can't save a customer: Credit Card Number is not set or not persistent" );

            if( customer.getLicenseNumber() != null )
                stmt.setString( 3, customer.getLicenseNumber() );
            else
            	 throw new RARException( "customer.save: can't save a customer: License Number is not set or not persistent" );
       
            if( customer.getLicenseState() != null )
                stmt.setString( 4, customer.getLicenseState() );
            else
            	 throw new RARException( "customer.save: can't save a customer: License State is not set or not persistent" );
       
            if( customer.getMembershipExpiration() != null )
                stmt.setDate( 5, (Date) customer.getMembershipExpiration() );
            else
            	 throw new RARException( "customer.save: can't save a customer: Membership Expiration is not set or not persistent" );
       
            
            if( customer.isPersistent() )
                stmt.setLong( 6, customer.getId() );

            inscnt = stmt.executeUpdate();

            if( !customer.isPersistent() ) {
                if( inscnt >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            CId = r.getLong( 1 );
                            if(CId > 0 )
                            	customer.setId( CId ); // set this vehicleType's db id (proxy object)
                        }
                    }
                }
                else
                    throw new RARException( "CustomerManager.save: failed to save a customer" );
            }
            else {
                if( inscnt < 1 )
                    throw new RARException( "CustomerManager.save: failed to save a customer" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new RARException( "CustomerManager.save: failed to save a Customer: " + e );
        }
    }

    public Iterator<Customer> restore(Customer customer)
            throws RARException
    {
        String       selectCSql = "select c.creditCardExpiration, c.creditCardNumber, c.licenseNumber, c.licenseState, c.membershipExpiration " +
                                      " from Customers c where ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        // form the query based on the given Customer object instance
        query.append( selectCSql );

        if( customer != null ) {
            if( customer.getId() >= 0 ){ // id is unique, so it is sufficient to get a Customer
                query.append( " and id = " + customer.getId() );
            } else { 
            	if( customer.getCreditCardExpiration() != null ) // Type is unique, so it is sufficient to get a Customer
            		condition.append( " and Credit Card Expiration = '" + customer.getCreditCardExpiration().toString() + "'" );
                        
                if( customer.getCreditCardNumber() != null )
                    condition.append( " and Credit Card Number = '" + customer.getCreditCardNumber() + "'" );

                if( customer.getLicenseNumber() != null )
                    condition.append( " and License Number = '" + customer.getLicenseNumber() + "'" );  
       
                if( customer.getLicenseState() != null ) {
                    condition.append( " License State = '" + customer.getLicenseState() + "'" );
                }
                
                if( customer.getMembershipExpiration() != null ) {
                    condition.append( " Membership Expiration = '" + customer.getMembershipExpiration().toString() + "'" );
                }
            }
        }

        try {

            stmt = conn.createStatement();

            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new CustomerIterator( r, objectLayer );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new RARException( "CustomerManager.restore: Could not restore persistent Customer object; Root cause: " + e );
        }

        throw new RARException( "CustomerManager.restore: Could not restore persistent Customer object" );
    }

    
    public void delete(Customer customer)
            throws RARException
    {
        String               deleteCSql = "delete from Customer where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;

        if( !customer.isPersistent() )
            return;

        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteCSql );
            stmt.setLong( 1, customer.getId() );
            inscnt = stmt.executeUpdate();
            if( inscnt == 1 ) {
                return;
            }
            else
                throw new RARException( "CustomerManager.delete: failed to delete a Customer" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new RARException( "CustomerManager.delete: failed to delete a Customer: " + e );        }
    }
}


