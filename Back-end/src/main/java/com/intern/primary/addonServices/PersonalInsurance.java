package com.intern.primary.addonServices;

import com.intern.carRental.primary.abstrct.RentalInsurance;
import org.springframework.data.relational.core.mapping.Table;

@Table("personal_insurance")
public class PersonalInsurance extends RentalInsurance {


    @Override
    public Boolean addInsurance() {
        // Check that insurance is not already added to a reservation
        if (this.getVehicleReservationId() != null) {
            // Insurance is already associated with a reservation
            return true;
        }
        return false;
    }

}
