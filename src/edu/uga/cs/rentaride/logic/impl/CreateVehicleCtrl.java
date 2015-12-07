//
// A control class to implement the 'Create Vehicle' use case
//
//

package edu.uga.cs.rentaride.logic.impl;


import java.util.Date;
import java.util.Iterator;

import edu.uga.cs.rentaride.RARException;
import edu.uga.cs.rentaride.entity.Vehicle;
import edu.uga.cs.rentaride.entity.VehicleType;
import edu.uga.cs.rentaride.entity.User;
import edu.uga.cs.rentaride.object.ObjectLayer;



public class CreateVehicleCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public CreateVehicleCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }
    
    public long createVehicle( String registrationTag, Date lastService, String make, 
                               int mileage, String model, String rl_str, String status_str, 
                               String vehicleType_str, int vehicleYear, String vehicleCondition_str)
            throws RARException
    { 
        Vehicle 	       vehicle  = null;
        Vehicle            modelVehicle = null;
        Iterator<Vehicle>  vehicleIter = null;
        RentalLocation  rentalLocation  = null;
        Status          status          = null;
        VehicleType     vehicleType     = null;
        VehicleCondition vehicleCondition = null;

        // check if a vehicle with this name already exists (name is unique)
        modelVehicle = objectLayer.createVehicle();
        modelVehicle.setName( registrationTag );
        vehicleIter = objectLayer.findVehicle( modelVehicle );
        if( vehicleIter.hasNext() )
            throw new RARException( "A vehicle with this registration tag already exists: " + registrationTag );

        // create the vehicle
        rentalLocation = objectLayer.createRentalLocation("rl_str");
        status = objectLayer.createStatus("status_str");
        vehicleType = objectLayer.createVehicleType("vehicleType_str");
        vehicleCondition = objectLayer.createVehicleContion("vehicleCondition_str");
        
        vehicle = objectLayer.createVehicle( registrationTag, lastService, make, mileage, model, rentalLocation, status, vehicleType,
                                              vehicleYear, vehicleCondition);
        objectLayer.storeVehicle( vehicle );

        return vehicle.getId();
    }
}
