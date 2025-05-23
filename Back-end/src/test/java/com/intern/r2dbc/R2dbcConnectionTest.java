package com.intern.r2dbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class R2dbcConnectionTest {

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    public void testR2dbcConnection() {
        Mono<Integer> result = databaseClient.sql("SELECT 1")
                .map(row -> row.get(0, Integer.class))
                .first();

        StepVerifier.create(result)
                .expectNext(1)
                .verifyComplete();
    }
}