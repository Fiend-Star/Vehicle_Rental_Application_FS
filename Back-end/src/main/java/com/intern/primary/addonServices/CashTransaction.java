package com.intern.primary.addonServices;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Payment;
import com.intern.primary.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("cash_transaction")
public class CashTransaction extends Payment{
	

	
	private double cashTendered;

	@Override
	public boolean initiateTransaction() {
		// Check if cash tendered is sufficient
		if (this.getCashTendered() >= this.getAmount()) {
			// Update status to COMPLETED
			this.setStatus(PaymentStatus.COMPLETED.name());
			return true;
		}
		
		// If insufficient cash, payment fails
		this.setStatus(PaymentStatus.FAILED.name());
		return false;
	}

}
