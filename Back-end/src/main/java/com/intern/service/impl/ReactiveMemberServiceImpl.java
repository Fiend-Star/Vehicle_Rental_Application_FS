package com.intern.service.impl;

import com.intern.carRental.primary.Member;
import com.intern.carRental.primary.VehicleReservation;
import com.intern.repository.ReactiveMemberRepository;
import com.intern.repository.ReactiveVehicleReservationRepository;
import com.intern.service.ReactiveMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Implementation of the ReactiveMemberService interface.
 */
@Service
public class ReactiveMemberServiceImpl implements ReactiveMemberService {

    private final ReactiveMemberRepository memberRepository;
    private final ReactiveVehicleReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ReactiveMemberServiceImpl(
            ReactiveMemberRepository memberRepository,
            ReactiveVehicleReservationRepository reservationRepository,
            PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Flux<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Mono<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Override
    public Mono<Member> save(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return memberRepository.deleteById(id);
    }

    @Override
    public Mono<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }

    @Override
    public Mono<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public Mono<Member> findByDriverLicenseNumber(String licenseNumber) {
        return memberRepository.findByDriverLicenseNumber(licenseNumber);
    }

    @Override
    public Flux<Member> findMembersWithExpiredLicenses(Date referenceDate) {
        return memberRepository.findByDriverLicenseExpiryLessThan(referenceDate.getTime());
    }

    @Override
    public Flux<Member> findActiveMembers() {
        return memberRepository.findByAccActiveTrue();
    }

    @Override
    public Mono<Member> registerMember(Member member) {
        // Encode password
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setAccActive(true);
        member.setStatus("ACTIVE");
        return memberRepository.save(member);
    }

    @Override
    public Mono<Member> updateProfile(Long id, Member member) {
        return memberRepository.findById(id)
                .flatMap(existingMember -> {
                    // Update fields but don't change password or status
                    member.setId(id);
                    member.setPassword(existingMember.getPassword());
                    member.setAccActive(existingMember.getAccActive());
                    member.setStatus(existingMember.getStatus());
                    return memberRepository.save(member);
                });
    }

    @Override
    public Flux<VehicleReservation> findReservationsForMember(Long memberId) {
        return reservationRepository.findByAccountId(memberId);
    }

    @Override
    public Mono<Member> changePassword(Long id, String oldPassword, String newPassword) {
        return memberRepository.findById(id)
                .flatMap(member -> {
                    if (passwordEncoder.matches(oldPassword, member.getPassword())) {
                        member.setPassword(passwordEncoder.encode(newPassword));
                        return memberRepository.save(member);
                    } else {
                        return Mono.error(new IllegalArgumentException("Incorrect old password"));
                    }
                });
    }

    @Override
    public Mono<Member> setActiveStatus(Long id, boolean active) {
        return memberRepository.findById(id)
                .flatMap(member -> {
                    member.setAccActive(active);
                    member.setStatus(active ? "ACTIVE" : "INACTIVE");
                    return memberRepository.save(member);
                });
    }
}
