

package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.VehicleCondition;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.object.ObjectLayer;


class VehicleManager
{
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;
    
    public VehicleManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }
    
    public void save(Vehicle vehicle) 
            throws RARException
    {
        String               insertVSql = "insert into Vehicle ( registrationTag, lastService, make, mileage, model, rentalLocation, status, " + 
        								  " vehicleType, vehicleYear, vehicleCondition, vehicleID ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
        String               updateVSql = "update Vehicle set registrationTag = ?, lastService = ?, make = ?, mileage = ?, model = ?, " + 
        								  " rentalLocation = ?, status = ?, vehicleType = ?, vehicleYear = ?, vehicleCondition = ? where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 VId;

        /*
        if( club.getFounderId() == -1 )
            throw new ClubsException( "ClubManager.save: Attempting to save a Club without a founder" );
            */
                 
        try {

            if( !vehicle.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertVSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateVSql );

            if( vehicle.getRegistrationTag() != null ) // name is unique unique and non null
                stmt.setString( 1, vehicle.getRegistrationTag() );
            else 
                throw new RARException( "VenhicleManager.save: can't save a Vehicle: name undefined" );

            if( vehicle.getLastServiced() != null )
                stmt.setDate( 2, (Date) vehicle.getLastServiced() );
            else
                stmt.setNull( 2, java.sql.Types.DATE );
            
            if( vehicle.getMake() != null )
                stmt.setString( 3, vehicle.getMake() );
            else
                stmt.setNull( 3, java.sql.Types.VARCHAR );
            
            if( vehicle.getMileage() != 0 )
                stmt.setInt( 4, vehicle.getMileage() );
            else
                stmt.setNull( 4, java.sql.Types.INTEGER );
            
            if( vehicle.getModel() != null )
                stmt.setString( 5, vehicle.getModel() );
            else
                stmt.setNull( 5, java.sql.Types.VARCHAR );
            
            if( vehicle.getRentalLocation() != null )
                stmt.setString( 6, vehicle.getRentalLocation().getName() );
            else
                stmt.setNull( 6, java.sql.Types.VARCHAR );
            
            if( vehicle.getStatus() != null )
                stmt.setString( 7, vehicle.getStatus().toString() );
            else
                stmt.setNull( 7, java.sql.Types.VARCHAR );
            
            if( vehicle.getVehicleType() != null )
                stmt.setString( 8, vehicle.getVehicleType().getType() );
            else
                stmt.setNull( 8, java.sql.Types.VARCHAR );
            
            if( vehicle.getYear() != 0 )
                stmt.setInt( 9, vehicle.getYear() );
            else
                stmt.setNull( 9, java.sql.Types.INTEGER );
            
            if( vehicle.getCondition() != null )
                stmt.setString( 10, vehicle.getCondition().toString() );
            else
                stmt.setNull( 10, java.sql.Types.DATE );

            /*
            if( vehicle.getEstablishedOn() != null ) {
                java.util.Date jDate = vehicle.getEstablishedOn();
                java.sql.Date sDate = new java.sql.Date( jDate.getTime() );
                stmt.setDate( 3,  sDate );
            }
            else
                stmt.setNull(3, java.sql.Types.DATE);

            if( club.getFounder() != null && club.getFounder().isPersistent() )
                stmt.setLong( 4, club.getFounder().getId() );
            else 
                throw new ClubsException( "ClubManager.save: can't save a Club: founder is not set or not persistent" );
            */
            
            if( vehicle.isPersistent() )
                stmt.setLong( 11, vehicle.getId() );

            inscnt = stmt.executeUpdate();

            if( !vehicle.isPersistent() ) {
                if( inscnt >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            VId = r.getLong( 1 );
                            if( VId > 0 )
                            	vehicle.setId( VId ); // set this person's db id (proxy object)
                        }
                    }
                }
                else
                    throw new RARException( "VehicleManager.save: failed to save a Vehicle" );
            }
            else {
                if( inscnt < 1 )
                    throw new RARException( "VehicleManager.save: failed to save a Vehicle" ); 
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new RARException( "VehicleManager.save: failed to save a Vehicle: " + e );
        }
    }

    public Iterator<Vehicle> restore(Vehicle vehicle) 
            throws RARException
    {
        //String       selectClubSql = "select id, name, address, established, founderid from club";
        String       selectClubSql = "select v.registrationTag, v.lastService, v.make, v.mileage, v.model, v.rentalLocation, " + 
        							 " v.status, v.vehicleType, v.vehicleYear, v.vehicleCondition, v.vehicleID, from Vehicle v" ;
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );
        
        // form the query based on the given Club object instance
        query.append( selectClubSql );
        
        if( vehicle != null ) {
            if( vehicle.getId() >= 0 ) // id is unique, so it is sufficient to get a person
                query.append( " and id = " + vehicle.getId() );
            else if( vehicle.getRegistrationTag() != null ) // userName is unique, so it is sufficient to get a person
                query.append( " and registrationTag = '" + vehicle.getRegistrationTag() + "'" );
            else {

                if( vehicle.getLastServiced() != null )
                    query.append( " and lastService = '" + vehicle.getLastServiced() + "'" );   
                
                if( vehicle.getMake() != null )
                    query.append( " and make = '" + vehicle.getMake() + "'" );   
                
                if( vehicle.getMileage() != 0 )
                    query.append( " and mileage = '" + vehicle.getMileage() + "'" );   
                
                if( vehicle.getModel() != null )
                    query.append( " and model = '" + vehicle.getModel() + "'" );   
                
                if( vehicle.getRentalLocation() != null )
                    query.append( " and rentalLocation = '" + vehicle.getRentalLocation() + "'" );   
                
                if( vehicle.getStatus() != null )
                    query.append( " and status = '" + vehicle.getStatus() + "'" );   
                
                if( vehicle.getVehicleType() != null )
                    query.append( " and vehicleType = '" + vehicle.getVehicleType() + "'" );   
                
                if( vehicle.getYear()!= 0 )
                    query.append( " and vehicleYear = '" + vehicle.getYear() + "'" );   
                
                if( vehicle.getCondition() != null )
                    query.append( " and vehicleCondition = '" + vehicle.getCondition() + "'" );  
                
                /*
                if( club.getEstablishedOn() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " established = '" + club.getEstablishedOn() + "'" );
                }
                */
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
                return new VehicleIterator( r, objectLayer );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new RARException( "VehicleManager.restore: Could not restore persistent Vehicle object; Root cause: " + e );
        }

        throw new RARException( "VehicleManager.restore: Could not restore persistent Vehicle object" );
    }

    /*
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
    */

    public void delete(Vehicle vehicle) 
            throws RARException
    {
        String               deleteVSql = "delete from Vehicle where id = ?";              
        PreparedStatement    stmt = null;
        int                  inscnt;
             
        if( !vehicle.isPersistent() ) // is the Club object persistent?  If not, nothing to actually delete
            return;
        
        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteVSql );         
            stmt.setLong( 1, vehicle.getId() );
            inscnt = stmt.executeUpdate();          
            if( inscnt == 1 ) {
                return;
            }
            else
                throw new RARException( "VehicleManager.delete: failed to delete a Vehicle" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new RARException( "VehicleManager.delete: failed to delete a Vehicle: " + e );        }
    }
}
