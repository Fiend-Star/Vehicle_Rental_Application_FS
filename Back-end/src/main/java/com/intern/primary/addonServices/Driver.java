package com.intern.primary.addonServices;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Service;

@Table("driver")
public class Driver extends Service {
	
	@Override
	public Boolean addService() {
		// Check that the driver service is not already added to a reservation
		if (this.getVehicleReservationId() != null) {
			// Service is already associated with a reservation
			return true;
		}
		return false;
	}

}
