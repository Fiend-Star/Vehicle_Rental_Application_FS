package com.intern.carRental.primary;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Table("parking_stall")
@ToString
public class ParkingStall {
	
	@Id
	private Long id;
	
	@Column("stall_number")
	private String stallNumber;
	
	@Column("location_identifier")
	private String locationIdentifier;
}