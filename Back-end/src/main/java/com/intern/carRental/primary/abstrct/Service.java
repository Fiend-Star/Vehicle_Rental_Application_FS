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
@Table("service")
public abstract class Service {

    @Id
    private Long id;

    @Column("service_id")
    private String serviceId;

    @Column("vehicle_reservation_id")
    private Long vehicleReservationId;
    
    @Transient
    private VehicleReservation vehiclereservation;

    public abstract Boolean addService();
}
