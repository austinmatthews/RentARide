package edu.uga.cs.rentaride.entity.impl;

import java.util.Date;
import edu.uga.cs.rentaride.entity.Customer;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.RARException;


/** This class represents information about a registered customer of Rent-A-Ride.
 *
 */
public CustomerImpl
    extends User
    implements Customer
{
    //attributes
    private Date    membershipExpiration;
    private String  licenseState;
    private String  licenseNumber;
    private String  residenceAddress;
    private String  cardNumber;
    private Date    cardExpiration;
    
    public CustomerImpl(Date membershipExpiration, String licenseState, String licenseNumber, 
                        String residenceAddress, String cardNumber, Date cardExpiration)
    {
        //super(-1); ??
        //need this to intantiate the User variables as well
        //perhaps use the setters from the User class bc the variables are private
        this.membershipExpiration = membershipExpiration;
        this.licenseState = licenseState;
        this.licenseNumber = licenseNumber;
        this.residenceAddress = residenceAddress;
        this.cardNumber = cardNumber;
        this.cardExpiration = cardExpiration;
    }
    
    /** Return the expiration Date of this Customer's membership in Rent-A-Ride.
     * @return the membership expiration Date for this customer 
     */
    public Date   getMembershipExpiration()
    {
        return membershipExpiration;
    }
    
    /** Set the expiration Date of this Customer's membership in Rent-A-Ride.
     * @param membershipExpiration the new expiration Date for this customer
     * @throws RARException in case the membership date is in the past
     */
    public void   setMembershipExpiration( Date membershipExpiration ) throws RARException
    {
        //check for RARException
        this.membershipExpiration = membershipExpiration
    }
    
    /** Return the license state for this customer.
     * @return the String representing the state of the customer's license
     */
    public String getLicenseState()
    {
        return licenseState;
    }
    
    /** Set the new license state for this customer.
     * @param state the new state of this customer's license
     */
    public void   setLicenseState( String state )
    {
        this.licenseState = state;
    }
    
    /** Return the license number of this customer.
     * @return the license number of this customer
     */
    public String getLicenseNumber()
    {
        return licenseNumber;
    }
    
    /** Set the new license number of this customer.
     * @param licenseNumber the new license number of this customer
     */
    public void   setLicenseNumber( String licenseNumber )
    {
        this.licenseNumber = licenseNumber;
    }
    
    /** Return the residence address of this customer.
     * @return the address of this customer's residence
     */
    public String getResidenceAddress()
    {
        return residenceAddress;
    }
    
    /** Set the new residence address of this customer.
     * @param residenceAddress the new residence address of this customer
     */
    public void   setResidenceAddress( String residenceAddress )
    {
        this.residenceAddress = residenceAddress;
    }
    
    /** Return the credit card number of this customer.
     * @return the credit card number of this customer
     */
    public String getCreditCardNumber()
    {
        return cardNumber;
    }
    
    /** Set the new credit card number of this customer.
     * @param cardNumber the new credit card number of this customer
     */
    public void   setCreditCardNumber( String cardNumber )
    {
        this.cardNumber = cardNumber;
    }
    
    /** Return the expiration date of this customer's credit card.
     * @return the expiration date of this customer's credit card
     */
    public Date   getCreditCardExpiration()
    {
        return cardExpiration;
    }
    
    /** Set the new expiration date of this customer's credit card.
     * @param cardExpiration the new expiration date of this customer's credit card
     */
    public void   setCreditCardExpiration( Date cardExpiration)
    {
        this.cardExpiration = cardExpiration;
    }
}
