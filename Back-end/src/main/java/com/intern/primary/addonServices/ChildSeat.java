package com.intern.primary.addonServices;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Equipment;

@Table("child_seat")
public class ChildSeat extends Equipment{


	
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
