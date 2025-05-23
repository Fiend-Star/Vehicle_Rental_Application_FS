package com.intern.primary.addonServices;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Payment;
import com.intern.primary.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("check_transaction")
public class CheckTransaction extends Payment{
	

	
	private String bankName;
	private String checkNumber;
	
	@Override
	public boolean initiateTransaction() {
		// Check if bank name and check number are valid
		if (this.getBankName() != null && !this.getBankName().isEmpty() && 
			this.getCheckNumber() != null && !this.getCheckNumber().isEmpty()) {
			
			// Update status to COMPLETED
			this.setStatus(PaymentStatus.COMPLETED.name());
			return true;
		}
		
		// If information is incomplete, payment fails
		this.setStatus(PaymentStatus.FAILED.name());
		return false;
	}

}
