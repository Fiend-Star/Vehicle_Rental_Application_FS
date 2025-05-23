package com.intern.DAO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import com.intern.carRental.primary.abstrct.Account;
import reactor.core.publisher.Mono;

@Repository
@Deprecated // Use ReactiveAccountRepository from com.intern.repository package instead
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {
	
	Mono<Account> findByPersonEmail(String Email);
	
}