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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean returnVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

}
