package com.intern.carRental.primary.abstrct;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.intern.carRental.primary.Bill;
import com.intern.carRental.primary.VehicleReservation;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("notification")
public abstract class Notification {

    @Id
    private Long id;

    @Column("created_on")
    private Long createdOn;
    
    private String content;

    @Column("phone_number")
    private String phoneNumber;

    @Column("bill_id")
    private Long billId;
    
    @Column("vehicle_reservation_id")
    private Long vehicleReservationId;

    @Transient
    private Bill bill;

    @Transient
    @JsonManagedReference(value = "Notif")
    private VehicleReservation vehiclereservation;

    public abstract Boolean sendNotification(String str1, String str2);
    
    // Helper methods for Date conversion
    public java.util.Date getCreatedOnAsDate() {
        return createdOn != null ? new java.util.Date(createdOn) : null;
    }
    
    public void setCreatedOnFromDate(java.util.Date date) {
        this.createdOn = date != null ? date.getTime() : null;
    }
}
    