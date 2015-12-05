//
// A control class to implement the 'Create Rental Location' use case
//
//

package edu.uga.cs.rentaride.logic.impl;


import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;



public class CreateRentalLocationCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateRentalLocationCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
    
    public long createRentalLocation( String name, String addr, int capacity )
            throws RARException
    { 
        RentalLocation 		  club  = null;
        RentalLocation      modelRentalLocation = null;
        Iterator<RentalLocation>      clubIter = null;
        Person              founder = null;
        Person              modelPerson = null;
        Iterator<Person>    personIter = null;

        // check if a club with this name already exists (name is unique)
        modelClub = objectLayer.createClub();
        modelClub.setName( club_name );
        clubIter = objectLayer.findClub( modelClub );
        if( clubIter.hasNext() )
            throw new ClubsException( "A club with this name already exists: " + club_name );

        // retrieve the founder person
        modelPerson = objectLayer.createPerson();
        modelPerson.setId( founderId );
        personIter = objectLayer.findPerson( modelPerson );
        while( personIter.hasNext() ) {
            founder = personIter.next();
        }

        // check if the person actually exists
        if( founder == null )
            throw new ClubsException( "A person with this id does not exist: " + founderId );

        // create the club
        estab = new Date();		// mark it as created now
        club = objectLayer.createClub( club_name, club_addr, estab, founder );
        objectLayer.storeClub( club );

        return club.getId();
    }
}
