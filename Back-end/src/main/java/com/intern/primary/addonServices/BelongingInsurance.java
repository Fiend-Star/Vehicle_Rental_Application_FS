package com.intern.primary.addonServices;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.RentalInsurance;

@Table("belonging_insurance")
public class BelongingInsurance extends RentalInsurance{
	

	
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
