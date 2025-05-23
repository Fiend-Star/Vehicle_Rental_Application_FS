package com.intern.carRental.primary;
import java.util.*;

import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.intern.carRental.primary.abstrct.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table("receptionist")
public class Receptionist extends Account {
	
	@Column("date_joined")
	private Long dateJoined; // Store as milliseconds since epoch
	
    private boolean active;	//security
    
    // Helper methods for Date conversion
    @Transient
    public Date getDateJoinedAsDate() {
        return dateJoined != null ? new Date(dateJoined) : null;
    }
    
    public void setDateJoinedFromDate(Date date) {
        this.dateJoined = date != null ? date.getTime() : null;
    }

	
	public void searchMember(String Member) {
		//TODO searchmember
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
		return active;
	}

}