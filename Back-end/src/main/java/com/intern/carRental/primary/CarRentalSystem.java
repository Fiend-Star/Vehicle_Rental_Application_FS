package com.intern.carRental.primary;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Getter
@Setter
@Table("car_rental_system")
public class CarRentalSystem {
	@Id
	private Long id;
	
	private String name;
	
	// This field is not stored directly in the database but loaded by service layer
	@Transient
	@JsonManagedReference(value="CRSystem")
	private List<CarRentalLocation> carRentalLocation;

	public boolean addNewLocation() {
		//TODO addNewLocation
		return false;
	}
}
