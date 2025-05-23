package com.intern.primary.addonServices;

import com.intern.carRental.primary.abstrct.Equipment;
import org.springframework.data.relational.core.mapping.Table;

@Table("navigation")
public class Navigation extends Equipment {



    @Override
    public Boolean addEquipment() {
        // Check that equipment is not already added to a reservation
        if (this.getVehicleReservationId() != null) {
            // Equipment is already associated with a reservation
            return true;
        }
        return false;
    }

}
