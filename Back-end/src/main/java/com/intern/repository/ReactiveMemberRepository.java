package com.intern.repository;

import com.intern.carRental.primary.Member;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive repository for Member entities.
 */
@Repository
public interface ReactiveMemberRepository extends ReactiveBaseRepository<Member, Long> {
    
    /**
     * Find members by driver license number
     * 
     * @param licenseNumber the driver license number
     * @return a Mono emitting the member with the specified license number
     */
    Mono<Member> findByDriverLicenseNumber(String licenseNumber);
    
    /**
     * Find members with expired driver licenses
     * 
     * @param currentDate current date as timestamp
     * @return a Flux emitting members with expired driver licenses
     */
    Flux<Member> findByDriverLicenseExpiryLessThan(Long currentDate);
    
    /**
     * Find members by username
     * 
     * @param username the username
     * @return a Mono emitting the member with the specified username
     */
    Mono<Member> findByUsername(String username);
    
    /**
     * Find members by email
     * 
     * @param email the email address
     * @return a Mono emitting the member with the specified email
     */
    Mono<Member> findByEmail(String email);
    
    /**
     * Find active members
     * 
     * @return a Flux emitting active members
     */
    Flux<Member> findByAccActiveTrue();
}
