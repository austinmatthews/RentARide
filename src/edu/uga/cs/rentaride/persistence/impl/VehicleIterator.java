package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.Reservation;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.Rental;
import edu.uga.clubs.object.ObjectLayer;


public class VehicleIterator
    implements Iterator<Vehicle>
{
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;

    // these two will be used to create a new object
    //
    public VehicleIterator( ResultSet rs, ObjectLayer objectLayer )
            throws RARException
    { 
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  // just in case...
            throw new RARException( "VehicleIterator: Cannot create an iterator; root cause: " + e );
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public Vehicle next() 
    {
        
        VehicleCondition condition = null;
        Date lastServiced;
        String make;
        int mileage;
        String model;
        String registrationTag;
        RentalLocation rentalLocation = null;
        VehicleStatus vehicleStatus = null;
        VehicleType vehicleType = null;
        int year;
        
        
        if( more ) {

            try {
                registrationTag = rs.getString( "registrationTag" );
                lastServiced = rs.getDate( "lastService" );
                make = rs.getString( "make" );
                mileage = rs.getInteger( "mileage");
                model = rs.getString( "model" );
                rentalLocation = rs.getString( "rentalLocation" );
                vehicleStatus = rs.getLong( "status" );
                vehicleType = rs.getDate( "vehicleType" );
                year = rs.getString( "vehicleYear" );
            
                more = rs.next();
            }
            catch( Exception e ) {      // just in case...
                throw new NoSuchElementException( "PersonIterator: No next Person object; root cause: " + e );
            }
            
            person = objectLayer.createPerson( userName, password, email, firstName, lastName, personaddress, phone );
            person.setId( personid );
            founder = objectLayer.createPerson( null, null, null, null, null, null, null );
            founder.setId( clubFounderId );
            Club club = null;
            try {
                club = objectLayer.createClub( clubname, clubaddress, establishedOn, founder );
                club.setId( clubid );
                membership = objectLayer.createMembership( person, club, joined );
                membership.setId( id );
            }
            catch( ClubsException ce ) {
                ce.printStackTrace();
                System.out.println( ce );
            }

            return membership;
        }
        else {
            throw new NoSuchElementException( "PersonIterator: No next Person object" );
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}
