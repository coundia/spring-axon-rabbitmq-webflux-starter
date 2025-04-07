package com.pcoundia.sales.application.queryHandler;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import com.pcoundia.sales.application.dto.*;
import com.pcoundia.sales.infrastructure.repository.*;
import com.pcoundia.sales.application.query.*;
import com.pcoundia.sales.infrastructure.entity.*;
import com.pcoundia.sales.application.mapper.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class ListSaleQueryHandler {

private final SaleRepository saleRepository;

public ListSaleQueryHandler(SaleRepository saleRepository) {
this.saleRepository = saleRepository;
}

@QueryHandler
public CompletableFuture<SalePagedResponse> handle(ListSaleQuery query) {
	int limit = query.getLimit();
	int offset = (query.getPage() * query.getLimit());

	Mono<Long> totalElements = saleRepository.findAll().count();
	Mono<List<Sale>> items = saleRepository.findAllByPage(limit, offset).collectList();

		return Mono.zip(items, totalElements)
		.map(tuple -> {
		List<SaleResponse> responses = tuple.getT1().stream()
			.map(SaleMapper::toResponse)
			.toList();

			return SalePagedResponse.from(
			responses,
			query.getPage(),
			query.getLimit(),
			tuple.getT2(),
			tuple.getT2()/query.getLimit()
			);
	}).toFuture();
}
}