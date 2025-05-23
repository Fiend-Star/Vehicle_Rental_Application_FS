package com.intern.controller;

import com.intern.carRental.primary.Member;
import com.intern.carRental.primary.VehicleReservation;
import com.intern.service.ReactiveMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Map;

/**
 * REST controller for managing Member resources reactively.
 */
@RestController
@RequestMapping("/api/reactive/members")
public class ReactiveMemberController {

    private final ReactiveMemberService memberService;

    @Autowired
    public ReactiveMemberController(ReactiveMemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * GET /api/reactive/members : Get all members.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of members
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Member> getAllMembers() {
        return memberService.findAll();
    }

    /**
     * GET /api/reactive/members/:id : Get member by id.
     *
     * @param id the id of the member to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the member,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Member>> getMember(@PathVariable Long id) {
        return memberService.findById(id)
                .map(member -> ResponseEntity.ok().body(member))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/reactive/members : Register a new member.
     *
     * @param member the member to register
     * @return the ResponseEntity with status 201 (Created) and with body the new member
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Member> registerMember(@RequestBody Member member) {
        return memberService.registerMember(member);
    }

    /**
     * PUT /api/reactive/members/:id : Update an existing member profile.
     *
     * @param id the id of the member to update
     * @param member the member to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated member,
     * or with status 404 (Not Found) if the member couldn't be updated
     */
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Member>> updateMember(
            @PathVariable Long id,
            @RequestBody Member member) {
        return memberService.updateProfile(id, member)
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/reactive/members/:id : Delete a member.
     *
     * @param id the id of the member to delete
     * @return the ResponseEntity with status 204 (NO_CONTENT)
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteMember(@PathVariable Long id) {
        return memberService.deleteById(id);
    }

    /**
     * GET /api/reactive/members/username/:username : Get member by username.
     *
     * @param username the username
     * @return the ResponseEntity with status 200 (OK) and with body the member,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Member>> getMemberByUsername(@PathVariable String username) {
        return memberService.findByUsername(username)
                .map(member -> ResponseEntity.ok().body(member))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/reactive/members/email/:email : Get member by email.
     *
     * @param email the email
     * @return the ResponseEntity with status 200 (OK) and with body the member,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Member>> getMemberByEmail(@PathVariable String email) {
        return memberService.findByEmail(email)
                .map(member -> ResponseEntity.ok().body(member))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/reactive/members/license/:licenseNumber : Get member by driver license number.
     *
     * @param licenseNumber the driver license number
     * @return the ResponseEntity with status 200 (OK) and with body the member,
     * or with status 404 (Not Found)
     */
    @GetMapping(value = "/license/{licenseNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Member>> getMemberByLicense(@PathVariable String licenseNumber) {
        return memberService.findByDriverLicenseNumber(licenseNumber)
                .map(member -> ResponseEntity.ok().body(member))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/reactive/members/active : Get all active members.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of active members
     */
    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Member> getActiveMembers() {
        return memberService.findActiveMembers();
    }

    /**
     * GET /api/reactive/members/expired-licenses : Get all members with expired licenses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of members with expired licenses
     */
    @GetMapping(value = "/expired-licenses", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<Member> getMembersWithExpiredLicenses() {
        return memberService.findMembersWithExpiredLicenses(new Date());
    }

    /**
     * GET /api/reactive/members/:id/reservations : Get all reservations for a member.
     *
     * @param id the id of the member
     * @return the ResponseEntity with status 200 (OK) and the list of reservations
     */
    @GetMapping(value = "/{id}/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<VehicleReservation> getMemberReservations(@PathVariable Long id) {
        return memberService.findReservationsForMember(id);
    }

    /**
     * PUT /api/reactive/members/:id/password : Change member password.
     *
     * @param id the id of the member
     * @param passwordData the password data containing old and new passwords
     * @return the ResponseEntity with status 200 (OK) and with body the updated member
     */
    @PutMapping(value = "/{id}/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Member>> changePassword(
            @PathVariable Long id,
            @RequestBody Map<String, String> passwordData) {
        return memberService.changePassword(id, passwordData.get("oldPassword"), passwordData.get("newPassword"))
                .map(result -> ResponseEntity.ok().body(result))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()));
    }

    /**
     * PUT /api/reactive/members/:id/activate : Activate or deactivate a member.
     *
     * @param id the id of the member
     * @param active whether to activate or deactivate
     * @return the ResponseEntity with status 200 (OK) and with body the updated member
     */
    @PutMapping(value = "/{id}/activate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Member>> activateMember(
            @PathVariable Long id,
            @RequestParam boolean active) {
        return memberService.setActiveStatus(id, active)
                .map(result -> ResponseEntity.ok().body(result))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
