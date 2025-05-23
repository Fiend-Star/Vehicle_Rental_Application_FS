package com.intern.primary.addonServices;

import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Payment;
import com.intern.primary.enums.PaymentStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("credit_card_transaction")
public class CreditCardTransaction extends Payment{
	

	
	private String nameOnCard;

	@Override
	public boolean initiateTransaction() {
		// Check if name on card is valid
		if (this.getNameOnCard() != null && !this.getNameOnCard().isEmpty()) {
			// In a real implementation, this would verify the credit card with a payment processor
			
			// Update status to COMPLETED
			this.setStatus(PaymentStatus.COMPLETED.name());
			return true;
		}
		
		// If card information is invalid, payment fails
		this.setStatus(PaymentStatus.FAILED.name());
		return false;
	}
}
