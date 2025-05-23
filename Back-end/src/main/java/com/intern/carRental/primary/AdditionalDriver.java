package com.intern.carRental.primary;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.intern.primary.simplePOJO.Person;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("additional_driver")
public class AdditionalDriver {

	@Id
	private Long id;
	
	@Column("driver_id")
	private String driverID;
	
	@Column("vehicle_reservation_id") 
	private Long vehicleReservationId;
	
	// Person fields directly stored in AdditionalDriver table
	private String name;
	private String email;
	private String phone;
	
	// Address fields
	@Column("street_address")
	private String streetAddress;
	
	private String city;
	private String state;
	private String zipcode;
	private String country;
	
	@Transient
	@JsonBackReference(value = "addDriver")
	private VehicleReservation vehicleReservation;
	
	@Transient
	private Person person;
	
	// Helper methods to handle Person object
	public Person getPerson() {
		if (this.person == null) {
			this.person = new Person();
		}
		this.person.setName(this.name);
		this.person.setEmail(this.email);
		this.person.setPhone(this.phone);
		this.person.setStreetAddress(this.streetAddress);
		this.person.setCity(this.city);
		this.person.setState(this.state);
		this.person.setZipcode(this.zipcode);
		this.person.setCountry(this.country);
		return this.person;
	}
	
	public void setPerson(Person person) {
		if (person != null) {
			this.name = person.getName();
			this.email = person.getEmail();
			this.phone = person.getPhone();
			this.streetAddress = person.getStreetAddress();
			this.city = person.getCity();
			this.state = person.getState();
			this.zipcode = person.getZipcode();
			this.country = person.getCountry();
			this.person = person;
		}
	}
}
