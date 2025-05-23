package com.intern.carRental.primary.abstrct;

import com.intern.carRental.primary.Bill;
import com.intern.primary.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Table("payment")
public abstract class Payment {
	
	@Id
	private Long id;
	
	@Column("creation_date")
	private Long creationDate;
	
	private double amount;
	
	@Column("status")
	private String status;
	
	@Column("bill_id")
	private Long billId;
	
	@Transient
	private Bill bill;
	
	// Helper methods for enum conversion
	public PaymentStatus getPaymentStatus() {
		return status != null ? PaymentStatus.valueOf(status) : null;
	}
	
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.status = paymentStatus != null ? paymentStatus.toString() : null;
	}
	
	// Helper methods for Date conversion
	public java.util.Date getCreationDateAsDate() {
		return creationDate != null ? new java.util.Date(creationDate) : null;
	}
	
	public void setCreationDateFromDate(java.util.Date date) {
		this.creationDate = date != null ? date.getTime() : null;
	}
	
	public abstract boolean initiateTransaction();
}