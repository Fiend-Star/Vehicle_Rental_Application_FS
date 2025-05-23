package com.intern.carRental.primary;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.intern.carRental.primary.abstrct.Account;
import com.intern.carRental.primary.abstrct.Equipment;
import com.intern.carRental.primary.abstrct.Notification;
import com.intern.carRental.primary.abstrct.RentalInsurance;
import com.intern.carRental.primary.abstrct.Service;
import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.primary.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("vehicle_reservation")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public class VehicleReservation {
	
	@Id
	private Long id;
	private String reservationNumber;
	
	@Column("creation_date")
	private Long creationDate;
	
	@Column("status")
	private String status;
	
	@Column("due_date")
	private Long dueDate;
	
	@Column("return_date")
	private Long returnDate;
	
	@Column("pickup_location_name")
	private String pickupLocationName;
	
	@Column("return_location_name")
	private String returnLocationName;
	
	@Column("account_id")
	private Long accountId;
	
	@Column("vehicle_id")
	private Long vehicleId;
	
	@Column("pickup_location_id")
	private Long pickupLocationId;
	
	@Column("return_location_id")
	private Long returnLocationId;
	
	@Transient
	@JsonBackReference(value = "accVehicle")
	private Account account;
	
	@Transient
	@JsonManagedReference(value = "addDriver")
	private List<AdditionalDriver> additionaldriver;
	
	@Transient
	@JsonBackReference(value = "Vehicle")
	private Vehicle vehicle;
	
	@Transient
	private Bill bill;
	
	@Transient
	@JsonBackReference(value = "Notif")
	private List<Notification> notification;
	
	@Transient
	private List<Service> service;
	
	@Transient
	private List<RentalInsurance> rentalinsurance;
	
	@Transient
	private List<Equipment> equipment;
	
	// Helper methods for enum conversion
	public ReservationStatus getRSstatus() {
		return status != null ? ReservationStatus.valueOf(status) : null;
	}
	
	public void setRSstatus(ReservationStatus status) {
		this.status = status != null ? status.toString() : null;
	}
	
	// Helper methods for Date conversion
	public Date getCreationDateAsDate() {
		return creationDate != null ? new Date(creationDate) : null;
	}
	
	public void setCreationDateFromDate(Date date) {
		this.creationDate = date != null ? date.getTime() : null;
	}
	
	public Date getDueDateAsDate() {
		return dueDate != null ? new Date(dueDate) : null;
	}
	
	public void setDueDateFromDate(Date date) {
		this.dueDate = date != null ? date.getTime() : null;
	}
	
	public Date getReturnDateAsDate() {
		return returnDate != null ? new Date(returnDate) : null;
	}
	
	public void setReturnDateFromDate(Date date) {
		this.returnDate = date != null ? date.getTime() : null;
	}
	
	public VehicleReservation fetchDetails() {
		// TODO fetchDetails
		return null;
	}
}