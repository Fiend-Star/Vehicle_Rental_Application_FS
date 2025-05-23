package com.intern.primary.addonServices;

import com.intern.carRental.primary.abstrct.Service;
import org.springframework.data.relational.core.mapping.Table;

@Table("wifi")
public class WiFi extends Service {


    @Override
    public Boolean addService() {
        // Check that service is not already added to a reservation
        if (this.getVehicleReservationId() != null) {
            // Service is already associated with a reservation
            return true;
        }
        return false;
    }

}
