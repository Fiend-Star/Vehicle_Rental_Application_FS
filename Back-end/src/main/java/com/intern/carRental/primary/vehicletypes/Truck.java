package com.intern.carRental.primary.vehicletypes;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Vehicle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("truck")
public class Truck extends Vehicle{
	
	private String type;
	
	@Override
	public Boolean reserveVehicle() {
		// Check if the truck is available for reservation
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.AVAILABLE) {
			// Set status to Reserved
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.RESERVED);
			return true;
		}
		return false;
	}

	@Override
	public Boolean returnVehicle() {
		// Check if the truck is currently loaned/reserved
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.LOANED || 
			this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.RESERVED) {
			// Set status back to available
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.AVAILABLE);
			return true;
		}
		return false;
	}

}
