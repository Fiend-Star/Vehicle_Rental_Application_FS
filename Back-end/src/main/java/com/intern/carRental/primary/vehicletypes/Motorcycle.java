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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean returnVehicle() {
		// TODO Auto-generated method stub
		return null;
	}

}
