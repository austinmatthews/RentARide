package edu.uga.cs.rentaride.entity;

import java.util.Date;

public interface EntityFactory
{
    public Administrator createAdministrator( String firstName, String lastName, String userName, 
                                              String emailAddress, String password, Date createDate, 
                                              String building );
    
    public Customer createCustomer( String firstName, String lastName, String userName, String emailAddress, 
                                    String password, Date createDate, Date membershipExpiration, String state, 
                                    String licenseNumber, String residenceAddress, String cardNumber, 
                                    Date cardExpiration );
    
    public Comment createComment( String comment, Rental rental );
    
    public HourlyPrice createPricePoint( int minHrs, int maxHrs, int price, VehicleType vehicleType );
    
    public Reservation createReservation( VehicleType vehicleType, RentalLocation rentalLocation, Customer customer,
                                          Rental rental, Date pickupTime, int rentalDuration );
    
    public Rental createRental( Reservation reservation, Customer customer, Vehicle vehicle,
                                Date pickupTime );
    
    public VehicleType createVehicleType( String type );
    
    public Vehicle createVehicle( VehicleType vehicleType, String make, String model,
                                  int year, String registrationTag, int mileage, Date lastServiced, String condition );
}
