package com.pcoundia.sales.presentation.controller;

import com.pcoundia.sales.domain.valueObject.*;
import com.pcoundia.sales.application.query.FindByIdSaleQuery;
import com.pcoundia.sales.application.dto.SaleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/queries/sale")
@Tag(name = "Sale Query ", description = "Endpoints for querying sales by id")
@Slf4j
public class FindByIdSaleController {

private final QueryGateway queryGateway;

public FindByIdSaleController(@Lazy QueryGateway queryGateway) {
this.queryGateway = queryGateway;
}

@GetMapping("/by-id")
@Operation(
summary = "Find sale by id",
description = "Returns a list of sales that match the given id"
)
@ApiResponses(value = {
@ApiResponse(responseCode = "200", description = "List of matching sales",
content = @Content(mediaType = "application/json",
schema = @Schema(implementation = SaleResponse.class))),
@ApiResponse(responseCode = "400", description = "Invalid parameter", content = @Content),
@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
})
public Mono<List<SaleResponse>> findById(
	@Parameter(description = "Value of the id to filter by", required = true)
	@RequestParam String id) {

	SaleId idVo = SaleId.create(id);
	FindByIdSaleQuery query = new FindByIdSaleQuery(idVo);

	return Mono.fromFuture(queryGateway.query(
	query,
	org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(SaleResponse.class)
	));
	}
	}
