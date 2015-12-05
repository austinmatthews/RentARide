/**
 * 
 */
package edu.uga.cs.rentaride.logic.impl;

import java.sql.Connection;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.logic.LogicLayer;
import edu.uga.cs.rentaride.object.ObjectLayer;
import edu.uga.cs.rentaride.object.impl.ObjectLayerImpl;
import edu.uga.cs.rentaride.persistence.PersistenceLayer;
import edu.uga.cs.rentaride.persistence.impl.PersistenceLayerImpl;
import edu.uga.cs.rentaride.session.Session;
import edu.uga.cs.rentaride.session.SessionManager;

//$$$$$ ADD entity import statements when you add your usecases to this $$$$$$

/**
 * @author team 9
 *
 */
public class LogicLayerImpl 
    implements LogicLayer
{
    private ObjectLayer objectLayer = null;

    public LogicLayerImpl( Connection conn )
    {
        objectLayer = new ObjectLayerImpl();
        PersistenceLayer persistenceLayer = new PersistenceLayerImpl( conn, objectLayer );
        objectLayer.setPersistence( persistenceLayer );
        System.out.println( "LogicLayerImpl.LogicLayerImpl(conn): initialized" );
    }
    
    public LogicLayerImpl( ObjectLayer objectLayer )
    {
        this.objectLayer = objectLayer;
        System.out.println( "LogicLayerImpl.LogicLayerImpl(objectLayer): initialized" );
    }
/*
    public List<Club> findAllClubs() 
            throws ClubsException
    {
        FindAllClubsCtrl ctrlFindAllClubs = new FindAllClubsCtrl( objectLayer );
        return ctrlFindAllClubs.findAllClubs();
    }

    public List<Person> findAllPersons() 
            throws ClubsException
    {
        FindAllPersonsCtrl ctrlFindAllPersons = new FindAllPersonsCtrl( objectLayer );
        return ctrlFindAllPersons.findAllPersons();
    }

    public long joinClub(Person person, Club club) throws ClubsException
    {
        return 0;
    }

    public long joinClub(long personId, String clubName) throws ClubsException
    {
        JoinClubCtrl ctrlJoinClub = new JoinClubCtrl( objectLayer );
        return ctrlJoinClub.joinClub( personId, clubName );
    }

    public long createClub(String clubName, String address, long founderId)
            throws ClubsException
    {
        CreateClubCtrl ctrlCreateClub = new CreateClubCtrl( objectLayer );
        return ctrlCreateClub.createClub( clubName, address, founderId );
    }

    public long createPerson(String userName, String password, String email,
            String firstName, String lastName, String address, String phone)
            throws ClubsException
    {
        CreatePersonCtrl ctrlCreatePerson = new CreatePersonCtrl( objectLayer );
        return ctrlCreatePerson.createPerson( userName, password, email, firstName,
                                              lastName, address, phone );
    }

    public List<Person> findClubMembers(String clubName) throws ClubsException
    {
        FindClubMembersCtrl ctrlFindClubMembers = new FindClubMembersCtrl( objectLayer );
        return ctrlFindClubMembers.findClubMembers( clubName );
    }
    */
    
    public long createRentalLocation(String name, String addr, int capacity)
            throws RARException
    {
        CreateRentalLocationCtrl ctrlCreateRentalLocation = new CreateRentalLocationCtrl (objectLayer);
        return ctrlCreateRentalLocation.createRentalLocation( name, addr, capacity );
    }

    public void logout( String ssid ) throws RARException
    {
        SessionManager.logout( ssid );
    }

    public String login( Session session, String userName, String password ) 
            throws RARException
    {
        LoginCtrl ctrlVerifyPerson = new LoginCtrl( objectLayer );
        return ctrlVerifyPerson.login( session, userName, password );
    }
}
