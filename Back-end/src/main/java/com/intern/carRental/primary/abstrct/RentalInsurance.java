package com.intern.carRental.primary.abstrct;

import com.intern.carRental.primary.VehicleReservation;
import lombok.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("rental_insurance")
public abstract class RentalInsurance {
	
	@Id
	private Long id;
	
	@Column("insurance_id")
	private String insuranceId;
	
	@Column("vehicle_reservation_id")
	private Long vehicleReservationId;
	
	@Transient
	private VehicleReservation vehiclereservation;
	
	public abstract Boolean addInsurance();
}
