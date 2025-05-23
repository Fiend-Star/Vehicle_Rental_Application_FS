package com.intern.carRental.primary;
import java.util.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

@Getter
@Setter
@Table("bill")
public class Bill {
	
	@Id
	private Long id;
	
	@Column("amount")
	private double totalAmount;
	
	@Column("creation_date")
	private Long creationDate;
	
	@Column("status")
	private String status;
	
	@Column("reservation_id")
	private Long reservationId;
	
	@Transient
	@JsonManagedReference(value = "billItem")
	private List<BillItem> billitem;
	
	// Helper methods for Date conversion
	public Date getCreationDateAsDate() {
		return creationDate != null ? new Date(creationDate) : null;
	}
	
	public void setCreationDateFromDate(Date date) {
		this.creationDate = date != null ? date.getTime() : null;
	}
	
	public Boolean addBillItem() {
		//TODO addBillItem
		return null;
	}
}
