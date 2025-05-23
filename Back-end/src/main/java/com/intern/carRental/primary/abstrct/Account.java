package com.intern.carRental.primary.abstrct;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.intern.carRental.primary.VehicleReservation;
import com.intern.carRental.primary.intrfces.Search;
import com.intern.primary.enums.AccountStatus;
import com.intern.primary.simplePOJO.Person;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("account")
public abstract class Account implements Search {
	
	@Id
	private Long id;
	
	private String password;
	
	@Column("active")
	private Boolean accActive;
    
    @Column("security_roles")
    private String securityRoles;
	
	@Column("status")
	private String status;
	
	// Person fields directly stored in Account table
	@Column("person_id")
	private Long personId;
	
	@Column("person_type")
	private String personType;
	
	@Column("street_address")
	private String streetAddress;
	
	@Column("city")
	private String city;
	
	@Column("state")
	private String state;
	
	@Column("zipcode")
	private String zipcode;
	
	@Column("country")
	private String country;
	
	@Column("license_number")
	private String licenseNumber;
	
	@Column("license_expiry")
	private Long licenseExpiry;
	
	@Transient
	private Person person;
	
	@Transient
	private Vehicle vehicle;
	
	@Transient
	@JsonManagedReference(value = "accVehicle")
	private List<VehicleReservation> vehiclereservation;
	
	// Helper method for enum conversion
	public AccountStatus getASstatus() {
		return status != null ? AccountStatus.valueOf(status) : null;
	}
	
	public void setASstatus(AccountStatus status) {
		this.status = status != null ? status.toString() : null;
	}
	
	// Helper methods for Date conversion for license expiry
	public Date getLicenseExpiryAsDate() {
		return licenseExpiry != null ? new Date(licenseExpiry) : null;
	}
	
	public void setLicenseExpiryFromDate(Date date) {
		this.licenseExpiry = date != null ? date.getTime() : null;
	}
	
	public abstract Boolean resetPassword();

	public abstract Boolean isAccActive();
}