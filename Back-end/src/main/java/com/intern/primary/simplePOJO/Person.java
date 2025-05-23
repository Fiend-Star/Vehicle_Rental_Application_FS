package com.intern.primary.simplePOJO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Person {
	private String name;
	private String email;
	private String phone;
	
	// Address fields embedded directly
	private String streetAddress;
	private String city;
	private String state;
	private String zipcode;
	private String country;
	
	// Helper method to get/set address as an object
	public Location getAddress() {
		Location address = new Location();
		address.setStreetAddress(this.streetAddress);
		address.setCity(this.city);
		address.setState(this.state);
		address.setZipcode(this.zipcode);
		address.setCountry(this.country);
		return address;
	}
	
	public void setAddress(Location address) {
		if (address != null) {
			this.streetAddress = address.getStreetAddress();
			this.city = address.getCity();
			this.state = address.getState();
			this.zipcode = address.getZipcode();
			this.country = address.getCountry();
		}
	}
}