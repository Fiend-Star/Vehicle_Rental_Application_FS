package com.intern.carRental.primary;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.primary.simplePOJO.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("car_rental_location")
public class CarRentalLocation {
	
	@Id
	private Long id;
	
	private String name;
	
	// Location is stored as separate columns
	@Column("street_address")
	private String streetAddress;
	
	private String city;
	
	private String state;
	
	private String zipcode;
	
	private String country;
	
	@Column("car_rental_system_id")
	private Long carRentalSystemId;
	
	// These fields are not stored directly in the database but loaded by service layer
	@Transient
	private Location address;
	
	@Transient
	@JsonManagedReference(value="CRLocation") 
	private List<Vehicle> vehicle;
	
	@Transient
	@JsonBackReference(value="CRSystem")
	private CarRentalSystem carRentalSystem;
	
	// Helper methods to handle the Location object
	public void setAddress(Location address) {
	    this.address = address;
	    if (address != null) {
	        this.streetAddress = address.getStreetAddress();
	        this.city = address.getCity();
	        this.state = address.getState();
	        this.zipcode = address.getZipcode();
	        this.country = address.getCountry();
	    }
	}
	
	public Location getAddress() {
	    if (address == null && (streetAddress != null || city != null || state != null || zipcode != null || country != null)) {
	        address = new Location(streetAddress, city, state, zipcode, country);
	    }
	    return address;
	}
}