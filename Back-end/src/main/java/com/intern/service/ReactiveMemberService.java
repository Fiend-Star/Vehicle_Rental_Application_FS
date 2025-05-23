package com.intern.service;

import com.intern.carRental.primary.Member;
import com.intern.carRental.primary.VehicleReservation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Service interface for reactive operations on Member entities.
 */
public interface ReactiveMemberService {
    
    /**
     * Retrieve all members.
     * 
     * @return Flux of all members
     */
    Flux<Member> findAll();
    
    /**
     * Find a member by ID.
     * 
     * @param id the member ID
     * @return Mono containing the found member or empty
     */
    Mono<Member> findById(Long id);
    
    /**
     * Save a new or updated member.
     * 
     * @param member the member to save
     * @return Mono containing the saved member
     */
    Mono<Member> save(Member member);
    
    /**
     * Delete a member by ID.
     * 
     * @param id the member ID to delete
     * @return Mono completing when deletion is done
     */
    Mono<Void> deleteById(Long id);
    
    /**
     * Find a member by username.
     * 
     * @param username the username
     * @return Mono containing the found member
     */
    Mono<Member> findByUsername(String username);
    
    /**
     * Find a member by email.
     * 
     * @param email the email address
     * @return Mono containing the found member
     */
    Mono<Member> findByEmail(String email);
    
    /**
     * Find a member by driver license number.
     * 
     * @param licenseNumber the driver license number
     * @return Mono containing the found member
     */
    Mono<Member> findByDriverLicenseNumber(String licenseNumber);
    
    /**
     * Find members with expired driver licenses.
     * 
     * @param referenceDate the reference date
     * @return Flux of members with expired licenses
     */
    Flux<Member> findMembersWithExpiredLicenses(Date referenceDate);
    
    /**
     * Find active members.
     * 
     * @return Flux of active members
     */
    Flux<Member> findActiveMembers();
    
    /**
     * Register a new member.
     * 
     * @param member the member details
     * @return Mono containing the registered member
     */
    Mono<Member> registerMember(Member member);
    
    /**
     * Update member profile.
     * 
     * @param id the member ID
     * @param member the updated member details
     * @return Mono containing the updated member
     */
    Mono<Member> updateProfile(Long id, Member member);
    
    /**
     * Find reservations for a member.
     * 
     * @param memberId the member ID
     * @return Flux of reservations for the member
     */
    Flux<VehicleReservation> findReservationsForMember(Long memberId);
    
    /**
     * Change member password.
     * 
     * @param id the member ID
     * @param oldPassword the old password
     * @param newPassword the new password
     * @return Mono containing the updated member
     */
    Mono<Member> changePassword(Long id, String oldPassword, String newPassword);
    
    /**
     * Activate or deactivate a member account.
     * 
     * @param id the member ID
     * @param active the active status
     * @return Mono containing the updated member
     */
    Mono<Member> setActiveStatus(Long id, boolean active);
}
