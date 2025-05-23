package com.intern.carRental.primary;

import com.intern.carRental.primary.abstrct.Account;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table("account")
public class Member extends Account {

    @Column("driver_license_number")
    private String driverLicenseNumber;
    
    @Column("driver_license_expiry")
    private Long driverLicenseExpiry;

    public List<VehicleReservation> getReservations() {
        //TODO getreservation
        return null;
    }
    
    // Helper method for Date conversion
    public Date getDriverLicenseExpiryAsDate() {
        return driverLicenseExpiry != null ? new Date(driverLicenseExpiry) : null;
    }
    
    public void setDriverLicenseExpiryFromDate(Date date) {
        this.driverLicenseExpiry = date != null ? date.getTime() : null;
    }

    @Override
    public Boolean resetPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<String> searchByType(String type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<String> searchByModel(String model) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean isAccActive() {
        // TODO Auto-generated method stub
        return this.getAccActive();
    }
}