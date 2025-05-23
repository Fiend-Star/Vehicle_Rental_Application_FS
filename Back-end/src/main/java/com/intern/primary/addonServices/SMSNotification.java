package com.intern.primary.addonServices;

import com.intern.carRental.primary.abstrct.Notification;
import com.intern.primary.simplePOJO.Location;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("sms_notification")
public class SMSNotification extends Notification {



    private String phonenum;
    private Location address;

    @Override
    public Boolean sendNotification(String Subject, String Body) {
        // In a real implementation, this would use a SMS service provider
        // Here we just simulate successful sending
        if (this.getPhonenum() != null && !this.getPhonenum().isEmpty()) {
            this.setContent(Body);
            return true;
        }
        return false;
    }
}
