package com.intern.carRental.primary.abstrct;

import com.intern.carRental.primary.VehicleReservation;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("equipment")
public abstract class Equipment {

    @Id
    private Long id;

    @Column("equipment_id")
    private String equipmentId;
    
    @Column("vehicle_reservation_id")
    private Long vehicleReservationId;

    @Transient
    private VehicleReservation vehiclereservation;

    public abstract Boolean addEquipment();
}
