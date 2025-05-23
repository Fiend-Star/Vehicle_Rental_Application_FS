package com.intern.carRental.primary;

import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.intern.carRental.primary.abstrct.Vehicle;
import com.intern.primary.enums.VehicleLogType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("vehicle_log")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class VehicleLog {
	
	@Id
	private Long id;
	
	private String type; // Stored as string representation of VehicleLogType enum
	
	private String description;
	
	@Column("creation_date")
	private Date creationDate;
	
	@Column("vehicle_id")
	private Long vehicleId;
	
	// This field is not stored directly in the database but loaded by service layer
	@Transient
	@JsonBackReference(value = "log")
	private Vehicle vehicle;
	
	// Helper methods to get/set enum type
	public VehicleLogType getTypeEnum() {
	    return type != null ? VehicleLogType.valueOf(type) : null;
	}
	
	public void setTypeEnum(VehicleLogType typeEnum) {
	    this.type = typeEnum != null ? typeEnum.toString() : null;
	}
	
	public ArrayList<VehicleLogType> searchByLogtype(){
		//TODO searchByLogType
		return null;
	}
}
