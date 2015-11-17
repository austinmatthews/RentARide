

package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.object.ObjectLayer;


class ReservationManager
{
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;
    
    public ReservationManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(Reservation reservation) 
            throws RARException
    {
        String               insertRSql = "insert into Reservations ( customer, pickupTime, rental, rentalDuration, rentalLocation, vehicleType, reservationID ) values ( ?, ?, ?, ?, ?, ? )";
        String               updateRSql = "update Reservation set customer = ?, pickupTime = ?, rental = ?, rentalDuration = ?, vehicleType = ?, reservationID = ? where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 RId;

        /*
        if( club.getFounderId() == -1 )
            throw new ClubsException( "ClubManager.save: Attempting to save a Club without a founder" );
            */
                 
        try {

            if( !reservation.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertRSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateRSql );

            if( reservation.getCustomer() != null ) // name is unique unique and non null
                stmt.setLong( 1, reservation.getCustomer().getId() );  // should we use long customerId instead of customer name string?
            else 
                throw new RARException( "ReservationManager.save: can't save a Reservation: Customer undefined" );

            if( reservation.getPickupTime() != null )
                stmt.setDate( 2, (Date) reservation.getPickupTime() );   // I thought this was already a Date?
            else
                stmt.setNull( 2, java.sql.Types.DATE );
            
            if( reservation.getRental() != null )
                stmt.setLong( 3, reservation.getRental().getId() );   
            else
                stmt.setNull( 3, java.sql.Types.INTEGER );
            
            if( reservation.getRentalDuration() != 0 )
                stmt.setInt( 4, reservation.getRentalDuration() );   
            else
                stmt.setNull( 4, java.sql.Types.INTEGER );
            
            if( reservation.getPickupTime() != null )
                stmt.setDate( 2, (Date) reservation.getPickupTime() );  
            else
                stmt.setNull( 2, java.sql.Types.DATE );
            
            if( reservation.getPickupTime() != null )
                stmt.setDate( 2, (Date) reservation.getPickupTime() );  
            else
                stmt.setNull( 2, java.sql.Types.DATE );

            if( reservation.getEstablishedOn() != null ) {
                java.util.Date jDate = reservation.getEstablishedOn();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 3,  sDate );
            }
            else
                stmt.setNull(3, java.sql.Types.DATE);

            if( reservation.getFounder() != null && reservation.getFounder().isPersistent() )
                stmt.setLong( 4, club.getFounder().getId() );
            else 
                throw new ClubsException( "ClubManager.save: can't save a Club: founder is not set or not persistent" );
            
            if( club.isPersistent() )
                stmt.setLong( 5, club.getId() );

            inscnt = stmt.executeUpdate();

            if( !club.isPersistent() ) {
                if( inscnt >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            clubId = r.getLong( 1 );
                            if( clubId > 0 )
                                club.setId( clubId ); // set this person's db id (proxy object)
                        }
                    }
                }
                else
                    throw new ClubsException( "ClubManager.save: failed to save a Club" );
            }
            else {
                if( inscnt < 1 )
                    throw new ClubsException( "ClubManager.save: failed to save a Club" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new ClubsException( "ClubManager.save: failed to save a Club: " + e );
        }
    }

    public Iterator<Club> restore(Club club) 
            throws ClubsException
    {
        //String       selectClubSql = "select id, name, address, established, founderid from club";
        String       selectClubSql = "select c.id, c.name, c.address, c.established, p.id, " +
                                      "p.username, p.userpass, p.email, p.firstname, p.lastname, p.address, " +
                                      "p.phone from club c, person p where c.founderid = p.id";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Club object instance
        query.append( selectClubSql );
        
        if( club != null ) {
            if( club.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and id = " + club.getId() );
            else if( club.getName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and name = '" + club.getName() + "'" );
            else {

                if( club.getAddress() != null )
                    condition.append( " and address = '" + club.getAddress() + "'" );   

                if( club.getEstablishedOn() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " established = '" + club.getEstablishedOn() + "'" );
                }
                /*
                if( condition.length() > 0 ) {
                    query.append(  " where " );
                    query.append( condition );
                }
                */
            }
        }
        
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new ClubIterator( r, objectLayer );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new ClubsException( "ClubManager.restore: Could not restore persistent Club object; Root cause: " + e );
        }

        throw new ClubsException( "ClubManager.restore: Could not restore persistent Club object" );
    }

    public Person restoreEstablishedBy( Club club ) 
            throws ClubsException
    {
        String       selectPersonSql = "select p.id, p.username, p.userpass, p.email, p.firstname, p.lastname, p.address, p.phone from person p, club c where p.id = c.founderid";              
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Person object instance
        query.append( selectPersonSql );
        
        if( club != null ) {
            if( club.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and c.id = " + club.getId() );
            else if( club.getName() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and c.name = '" + club.getName() + "'" );
            else {

                if( club.getAddress() != null )
                    condition.append( " and c.address = '" + club.getAddress() + "'" );   

                if( club.getEstablishedOn() != null ) {
                    condition.append( " and c.established = '" + club.getEstablishedOn() + "'" );
                }

                if( condition.length() > 0 ) {
                    query.append( condition );
                }
            }
        }
                
        try {

            stmt = conn.createStatement();

            // retrieve the persistent Person object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                Iterator<Person> personIter = new PersonIterator( r, objectLayer );
                if( personIter != null && personIter.hasNext() ) {
                    return personIter.next();
                }
                else
                    return null;
            }
        }
        catch( Exception e ) {      // just in case...
            throw new ClubsException( "ClubManager.restoreEstablishedBy: Could not restore persistent Person object; Root cause: " + e );
        }

        // if we reach this point, it's an error
        throw new ClubsException( "ClubManager.restoreEstablishedBy: Could not restore persistent Person object" );
    }
    
    public void delete(Club club) 
            throws ClubsException
    {
        String               deleteClubSql = "delete from club where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
             
        if( !club.isPersistent() ) // is the Club object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteClubSql );         
            stmt.setLong( 1, club.getId() );
            inscnt = stmt.executeUpdate();          
            if( inscnt == 1 ) {
                return;
            }
            else
                throw new ClubsException( "ClubManager.delete: failed to delete a Club" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new ClubsException( "ClubManager.delete: failed to delete a Club: " + e );        }
    }
}
