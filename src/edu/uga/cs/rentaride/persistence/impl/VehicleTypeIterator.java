
package edu.uga.cs.rentaride.persistence.impl;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;


public class VehicleTypeIterator
    implements Iterator<VehicleType>
{
    private ResultSet   rs = null;
    private ObjectLayer objectLayer = null;
    private boolean     more = false;

    // these two will be used to create a new object
    //
    public VehicleTypeIterator( ResultSet rs, ObjectLayer objectLayer )
            throws RARException
    { 
        this.rs = rs;
        this.objectLayer = objectLayer;
        try {
            more = rs.next();
        }
        catch( Exception e ) {  // just in case...
            throw new RARException( "VehicleTypeIterator: Cannot create an iterator; root cause: " + e );
        }
    }

    public boolean hasNext() 
    { 
        return more; 
    }

    public VehicleType next() 
    {
    	String type;
    	VehicleType VT = null;
        
        
        if( more ) {

            try {
                type = rs.getString("TypeName");
                more = rs.next();
            }
            catch( Exception e ) {      // just in case...
                throw new NoSuchElementException( "VehicleTypeIterator: No next VehicleType object; root cause: " + e );
            }
            

            //try {
            VT = objectLayer.createVehicleType(type); 
            //}
            //catch( RARException ce ) {
            //   ce.printStackTrace();
            //    System.out.println( ce );
            //}

            return VT;
        }
        else {
            throw new NoSuchElementException( "VehicleTypeIterator: No next VehicleType object" );
        }
    }

    public void remove()
    {
        throw new UnsupportedOperationException();
    }

}
