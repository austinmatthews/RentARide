//
// A control class to implement the 'List all vehicles' use case
//
//


package edu.uga.cs.rentaride.logic.impl;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import edu.uga.uga.cs.RARException;
import edu.uga.uga.cs.entity.RentalLocation;
import edu.uga.uga.cs.object.ObjectLayer;



public class FindAllVehiclesCtrl {
    
    private ObjectLayer objectLayer = null;
    
    public FindAllVehiclesCtrl( ObjectLayer objectModel )
    {
        this.objectLayer = objectModel;
    }

    public List<RentalLocation> findAllVehicless()
            throws RARException
    {
        List<Vehicle> 	    vehicles  = null;
        Iterator<Vehicle> 	vehiclesIter = null;
        Vehicle     	      vehicle = null;

        vehicles = new LinkedList<Vehicle>();
        
        // retrieve all Vehicle objects
        //
        vehicleIter = objectLayer.findVehicle( null );
        while( vehicleIter.hasNext() ) {
            vehilce = vehicleIter.next();
            System.out.println( vehicle);
            vehicles.add( vehicle );
        }

        return vehicles;
    }
}
