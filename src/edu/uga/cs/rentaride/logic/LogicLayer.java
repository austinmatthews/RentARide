package edu.uga.cs.rentaride.logic;


import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.RentalLocation;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.session.Session;

public interface LogicLayer
{
   /* public List<Club>         findAllClubs() throws ClubsException;
    public List<Person>       findAllPersons() throws ClubsException;
    public long               joinClub( Person person, Club club ) throws ClubsException;
    public long               joinClub( long personId, String clubName ) throws ClubsException;
    public long               createClub( String clubName, String address, long founderId ) throws ClubsException;
    public long               createPerson( String userName, String password, String email, String firstName, 
                                            String lastName, String address, String phone ) throws ClubsException;
    public List<Person>       findClubMembers( String clubName ) throws ClubsException;
    public void               logout( String ssid ) throws ClubsException;
    public String             login( Session session, String userName, String password ) throws ClubsException;
    */
    public long               createRentalLocation(String name, String addr, int capacity) throws RARException;
}
