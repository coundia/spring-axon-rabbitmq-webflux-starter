package com.pcoundia.sales.application.queryHandler;

import com.pcoundia.sales.application.mapper.*;
import com.pcoundia.sales.domain.valueObject.*;
import reactor.core.publisher.Flux;
import com.pcoundia.sales.infrastructure.repository.SaleRepository;
import com.pcoundia.sales.application.query.FindByIdSaleQuery;
import com.pcoundia.sales.application.dto.SaleResponse;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import java.util.concurrent.CompletableFuture;
import java.util.List;

@Component
public class FindByIdSaleHandler {

private final SaleRepository repository;

public FindByIdSaleHandler(SaleRepository repository) {
	this.repository = repository;
}

@QueryHandler
public CompletableFuture<List<SaleResponse>> handle(FindByIdSaleQuery query) {
		return repository
		.getById(query.getId().value())
		.map(SaleMapper::toResponse)
		.collectList()
		.toFuture();

	}
}
