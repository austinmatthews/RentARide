//
// A control class to implement the 'List club membership' use case
//
//


package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.object.ObjectLayer;




public class FindVehicleTypeCtrl 
{
    private ObjectLayer objectLayer = null;
    
    public FindVehicleTypeCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
    
    public List<VehicleType> findVehicleType( String type )
            throws RARException
    {
        VehicleType                vehicleType = null;
        VehicleType                modelVehicleType = null;
        Iterator<VehicleType>      vehicleTypeIter = null;
        List<VehicleType>        vehicleTypeList  = null;

        vehicleTypeList = new LinkedList<VehicleType>();

        // find the club object
        modelVehicleType = objectLayer.createVehicleType();
        modelVehicleType.setType( type );
        vehicleTypeIter = objectLayer.findVehicleType( modelVehicleType );
        while( vehicleTypeIter.hasNext() ) {
            vehicleType = vehicleTypeIter.next();
            if (vehicleType == modelVehicleType)
            	vehicleTypeList.add(vehicleType);
        }
        if( vehicleType == null )
            throw new RARException( "A Vehicle Type with this name does not exist: " + type );

        return vehicleTypeList;
    }
  
}
