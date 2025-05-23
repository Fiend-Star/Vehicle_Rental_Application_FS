package com.intern.carRental.primary.vehicletypes;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.primary.enums.CarType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("car")
public class Car extends Vehicle {

	private String type; // Stored as string representation of CarType enum
	
	// Helper methods to get/set enum type
	public CarType getCarTypeEnum() {
		return type != null ? CarType.valueOf(type) : null;
	}
	
	public void setCarTypeEnum(CarType carType) {
		this.type = carType != null ? carType.toString() : null;
	}
	
	@Override
	public Boolean reserveVehicle() {
		// Check if the vehicle is available for reservation
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.AVAILABLE) {
			// Set status to Reserved
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.RESERVED);
			return true;
		}
		return false;
	}

	@Override
	public Boolean returnVehicle() {
		// Check if the vehicle is currently loaned/reserved
		if (this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.LOANED || 
			this.getStatusEnum() == com.intern.primary.enums.VehicleStatus.RESERVED) {
			// Set status back to available
			this.setStatusEnum(com.intern.primary.enums.VehicleStatus.AVAILABLE);
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Car [type=" + type + ", toString()=" + super.toString() + "]";
	}

	

}
