package edu.uga.cs.rentaride.entity.impl;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.persistence.Persistable;
import edu.uga.cs.rentaride.entity.HourlyPrice;
import edu.uga.cs.rentaride.entity.VehicleType;

/** This class represents an hourly price set for a specific time interval.
 * The price value is represented in Cents, not Dollars.
 *
 */
public HourlyPriceImpl
    extends Persistent 
    implements HourlyPrice
{
    private int minHours;
    private int maxHours;
    private int price;
    private VehicleType vehicleType;
    
    public HourlyPriceImpl(int minHours, int maxHours, int price, VehicleType vehicleType) {
        this.minHours = minHours;
        this.maxHours = maxHours;
        this.price = price;
        this.vehicleType = vehicleType;
    }
    
    /** Return the minimum hours for this price setting.
     * @return the minimum hours for this price setting
     */
    public int getMinHours()
    {
        return minHours;
    }
    
    /** Set the new minimum hours for this price setting.
     * @param minHours the new minimum hours for this price setting
     * @throws RARException in case the minHours value is not positive
     */
    public void setMinHours( int minHours ) throws RARException
    {
        this.minHours = minHours;
    }
    
    /** Return the maximum hours for this price setting.
     * @return the maximum hours for this price setting
     */
    public int getMaxHours()
    {
        return maxHours;
    }
    
    /** Set the new maximum hours for this price setting.
     * @param maxHours the new maximum hours for this price setting
     * @throws RARException in case the maxHours value is not positive or is not greater than minHours
     */
    public void setMaxHours( int maxHours ) throws RARException
    {
        this.maxHours = maxHours;
    }
    
    /** Return the price for this hourly price setting (value is in Cents).
     * @return the price of this hourly price setting
     */
    public int getPrice()
    {
        return price;
    }
    
    /** Set the new price for this hourly price setting (must be in Cents).
     * @param price the new price of this hourly setting (in Cents)
     * @throws RARException in case the price value is not positive
     */
    public void setPrice( int price ) throws RARException
    {
        this.price = price;
    }
}
