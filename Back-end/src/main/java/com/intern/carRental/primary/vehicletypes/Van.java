package com.intern.carRental.primary.vehicletypes;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.primary.enums.VanType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("van")
public class Van extends Vehicle {
	
	private String type; // Stored as string representation of VanType enum
	
	// Helper methods to get/set enum type
	public VanType getVanTypeEnum() {
		return type != null ? VanType.valueOf(type) : null;
	}
	
	public void setVanTypeEnum(VanType vanType) {
		this.type = vanType != null ? vanType.toString() : null;
	}

	@Override
	public Boolean reserveVehicle() {
		// Check if the Van is available for reservation
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.AVAILABLE) {
			// Set status to Reserved
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.RESERVED);
			return true;
		}
		return false;
	}

	@Override
	public Boolean returnVehicle() {
		// Check if the Van is currently loaned/reserved
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.LOANED || 
			this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.RESERVED) {
			// Set status back to available
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.AVAILABLE);
			return true;
		}
		return false;
	}

}
