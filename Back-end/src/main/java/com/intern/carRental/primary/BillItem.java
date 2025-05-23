package com.intern.carRental.primary;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Getter
@Setter
@Table("bill_item")
public class BillItem {
	
	@Id
	private Long id;
	
	@Column("amount")
	private double amount;
	
	@Column("type")
	private String service;
	
	@Column("bill_id")
	private Long billId;
	
	@Transient
	@JsonBackReference(value = "billItem")
	private Bill bill;
}
