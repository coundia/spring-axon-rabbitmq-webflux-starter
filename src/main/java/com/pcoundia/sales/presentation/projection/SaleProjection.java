package com.pcoundia.sales.presentation.projection;

import com.pcoundia.sales.domain.event.*;
import com.pcoundia.sales.infrastructure.repository.*;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SaleProjection {

private final SaleRepository repository;

public SaleProjection(SaleRepository repository) {
this.repository = repository;
}

@EventHandler
public void on(SaleCreatedEvent event) {
	 repository.insert(
            event.getId().value(),
            event.getQuantity().value(),
            event.getTotal_price().value()
	).subscribe(
		inserted -> log.info("Sale inserted: {}", inserted),
		error -> log.error("Error saving Sale: {}", error.getMessage(), error)
	);
	}

	@EventHandler
	public void on(SaleUpdatedEvent event) {
		 repository.updateAllById(
        event.getId().value(),
        event.getQuantity().value(),
        event.getTotal_price().value()
		).doOnSuccess(unused -> log.info("Sale updated successfully: {}", event.getId().value()))
		.doOnError(error -> log.error("Error updating Sale: {}", error.getMessage(), error))
		.subscribe();
	}


		@EventHandler
	public void on(SaleDeletedEvent event) {
		 repository.deleteById(event.getId().value())
			.doOnSuccess(unused -> log.info("Sale deleted successfully: {}", event.getId().value()))
			.doOnError(error -> log.error("Error deleting Sale: {}", error.getMessage(), error))
			.subscribe();
		}
}
