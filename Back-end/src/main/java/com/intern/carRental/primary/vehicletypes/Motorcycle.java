package com.intern.carRental.primary.vehicletypes;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Vehicle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("motorcycle")
public class Motorcycle extends Vehicle {
	
	private String type;
	
	@Override
	public Boolean reserveVehicle() {
		// Check if the motorcycle is available for reservation
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.AVAILABLE) {
			// Set status to Reserved
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.RESERVED);
			return true;
		}
		return false;
	}

	@Override
	public Boolean returnVehicle() {
		// Check if the motorcycle is currently loaned/reserved
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.LOANED || 
			this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.RESERVED) {
			// Set status back to available
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.AVAILABLE);
			return true;
		}
		return false;
	}

}
