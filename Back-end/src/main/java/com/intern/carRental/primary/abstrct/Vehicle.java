package com.intern.carRental.primary.abstrct;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.intern.carRental.primary.CarRentalLocation;
import com.intern.carRental.primary.ParkingStall;
import com.intern.carRental.primary.VehicleLog;
import com.intern.carRental.primary.VehicleReservation;
import com.intern.primary.enums.VehicleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("vehicle")
@JsonIdentityInfo(
		  generator = ObjectIdGenerators.PropertyGenerator.class, 
		  property = "id")
public abstract class Vehicle {

	public Vehicle() {
		super();
	}

	@Id
	private Long id;
	
	@Column("number_plate")
	private String numberPlate;
	
	@Column("stock_number")
	private String stockNumber;
	
	@Column("passenger_capacity")
	private int passengerCapacity;

	@Column("has_sunroof")
	private Boolean hasSunroof;

	private String model;	
	
	private String make;
	
	@Column("manufacturing_year")
	private int manufacturingYear;
	
	private int mileage;
	
	private String barcode;
	
	private String status; // Stored as string representation of VehicleStatus enum
	
	@Column("car_rental_location_id")
	private Long carRentalLocationId;
	
	@Column("parking_stall_id")
	private Long parkingStallId;
	
	// These fields are not stored directly in the database but loaded by service layer
	@Transient
	private CarRentalLocation carRentalLocation;
	
	@Transient
	private List<VehicleLog> vehicle_log;
	
	@Transient
	private List<VehicleReservation> vehiclereservation;
	
	@Transient
	private ParkingStall parkingstall;
	
	// Helper methods to get/set enum status
	public VehicleStatus getStatusEnum() {
	    return status != null ? VehicleStatus.valueOf(status) : null;
	}
	
	public void setStatusEnum(VehicleStatus statusEnum) {
	    this.status = statusEnum != null ? statusEnum.toString() : null;
	}
	
	public abstract Boolean reserveVehicle();
	
	public abstract Boolean returnVehicle();

	@Override
	public String toString() {
		return "Vehicle [id=" + id + ", numberPlate=" + numberPlate + ", stockNumber=" + stockNumber
				+ ", passengerCapacity=" + passengerCapacity + ", hasSunroof=" + hasSunroof + ", model=" + model
				+ ", make=" + make + ", manufacturingYear=" + manufacturingYear + ", mileage=" + mileage + ", barcode="
				+ barcode + ", status=" + status + ", carRentalLocation=" + carRentalLocation + ", vehicle_log="
				+ vehicle_log + ", vehiclereservation=" + vehiclereservation + ", parkingstall=" + parkingstall + "]";
	}
	
}
