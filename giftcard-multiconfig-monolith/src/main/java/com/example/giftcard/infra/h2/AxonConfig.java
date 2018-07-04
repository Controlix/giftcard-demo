package com.example.giftcard.infra.h2;

import com.example.giftcard.query.CardSummaryProjection;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.eventhandling.saga.repository.inmemory.InMemorySagaStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Simple Axon configuration built around an embedded H2 database.
 */
@Profile("h2")
@Configuration
public class AxonConfig {

    /* Using tracking processors for our read model, which will store there tokens. */
    @Autowired
    public void configure(EventHandlingConfiguration configuration) {
        configuration.registerTrackingProcessor(CardSummaryProjection.class.getPackage().getName());
    }

    /* A persistent event bus (event store) for event sourcing in our command model. */
    @Bean
    public EventBus eventBus(EventStorageEngine eventStorageEngine) {
        return new EmbeddedEventStore(eventStorageEngine);
    }

    /* We won't use Sagas. Configuring an in-mem sagastore to avoid auto-creation of a JPA-based instance. */
    @Bean
    public SagaStore sagaStore() {
        return new InMemorySagaStore();
    }
}


