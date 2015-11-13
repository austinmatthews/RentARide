


package edu.uga.cs.rentaride.persistence.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;

import com.mysql.jdbc.PreparedStatement;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


class VehicleTypeManager
{
    private ObjectLayer objectLayer = null;
    private Connection   conn = null;

    public VehicleTypeManager( Connection conn, ObjectLayer objectLayer )
    {
        this.conn = conn;
        this.objectLayer = objectLayer;
    }

    public void save(VehicleType vehicleType)
            throws RARException
    {
        String               insertVTSql = "insert into VehicleType ( typeName  ) values ( ? )";
        String               updateVTSql = "update VehicleTye set typeName = ? where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;
        long                 VTId;

        /*
        if( club.getFounderId() == -1 )
            throw new ClubsException( "ClubManager.save: Attempting to save a Club without a founder" );
            */
        try {

            if( !vehicleType.isPersistent() )
                stmt = (PreparedStatement) conn.prepareStatement( insertVTSql );
            else
                stmt = (PreparedStatement) conn.prepareStatement( updateVTSql );

            if( vehicleType.getType() != null ) // type is unique and non null
            	// This is me setting the value in col typeName in table VehicleType to the provided value
            	stmt.setString( "typeName", vehicleType.getType() );
            else
                throw new RARException( "VehicleTypeManager.save: can't save a VehicleType: name undefined" );
/*
 * This is an example for when there are more parameters to deal with
            if( club.getAddress() != null )
                stmt.setString( 2, club.getAddress() );
            else
                stmt.setNull( 2, java.sql.Types.VARCHAR );

            if( club.getEstablishedOn() != null ) {
                java.util.Date jDate = club.getEstablishedOn();
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
            if( vehicleType.isPersistent() )
                stmt.setLong( 5, vehicleType.getId() );

            inscnt = stmt.executeUpdate();

            if( !vehicleType.isPersistent() ) {
                if( inscnt >= 1 ) {
                    String sql = "select last_insert_id()";
                    if( stmt.execute( sql ) ) { // statement returned a result

                        // retrieve the result
                        ResultSet r = stmt.getResultSet();

                        // we will use only the first row!
                        //
                        while( r.next() ) {

                            // retrieve the last insert auto_increment value
                            VTId = r.getLong( 1 );
                            if( VTId > 0 )
                                vehicleType.setId( VTId ); // set this vehicleType's db id (proxy object)
                        }
                    }
                }
                else
                    throw new RARException( "VehicleTypeManager.save: failed to save a VehicleType" );
            }
            else {
                if( inscnt < 1 )
                    throw new RARException( "VehicleTypeManager.save: failed to save a VehicleType" );
            }
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new RARException( "VehicleTypeManager.save: failed to save a VehicleType: " + e );
        }
    }

    public Iterator<VehicleType> restore(VehicleType vehicleType)
            throws RARException
    {
        //String       selectClubSql = "select id, name, address, established, founderid from club";
        String       selectVTSql = "select vt.id, vt.typeName " +
                                      " from VehicleType vt ";
        Statement    stmt = null;
        StringBuffer query = new StringBuffer( 100 );
        StringBuffer condition = new StringBuffer( 100 );

        condition.setLength( 0 );

        // form the query based on the given Club object instance
        query.append( selectVTSql );

        if( vehicleType != null ) {
            if( vehicleType.getId() >= 0 ) // id is unique, so it is sufficient to get a VehicleType
                query.append( " and id = " + vehicleType.getId() );
            else if( vehicleType.getType() != null ) // Type is unique, so it is sufficient to get a VehicleType
                query.append( " and name = '" + vehicleType.getType() + "'" );
            else {
            	/*
                if( club.getAddress() != null )
                    condition.append( " and address = '" + club.getAddress() + "'" );

                if( club.getEstablishedOn() != null ) {
                    if( condition.length() > 0 )
                        condition.append( " and" );
                    condition.append( " established = '" + club.getEstablishedOn() + "'" );
                }
                // This was already commented out
                //if( condition.length() > 0 ) {
                //    query.append(  " where " );
                //    query.append( condition );
                //}
                */
            }
        }

        try {

            stmt = conn.createStatement();

            // retrieve the persistent VehicleType object
            //
            if( stmt.execute( query.toString() ) ) { // statement returned a result
                ResultSet r = stmt.getResultSet();
                return new VehicleTypeIterator( r, objectLayer );
            }
        }
        catch( Exception e ) {      // just in case...
            throw new RARException( "VehicleTypeManager.restore: Could not restore persistent VehicleType object; Root cause: " + e );
        }

        throw new RARException( "ClubManager.restore: Could not restore persistent Club object" );
    }
    /*
     * I dont know what the equivalent of this would be for us
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
    public void delete(VehicleType vehicleType)
            throws RARException
    {
        String               deleteVTSql = "delete from VehicleType where id = ?";
        PreparedStatement    stmt = null;
        int                  inscnt;

        if( !vehicleType.isPersistent() ) // is the VehicleType object persistent?  If not, nothing to actually delete
            return;

        try {
            stmt = (PreparedStatement) conn.prepareStatement( deleteVTSql );
            stmt.setLong( 1, vehicleType.getId() );
            inscnt = stmt.executeUpdate();
            if( inscnt == 1 ) {
                return;
            }
            else
                throw new RARException( "VehicleTypeManager.delete: failed to delete a VehcleType" );
        }
        catch( SQLException e ) {
            e.printStackTrace();
            throw new RARException( "VehicleTypeManager.delete: failed to delete a VehicleType: " + e );        }
    }
}

