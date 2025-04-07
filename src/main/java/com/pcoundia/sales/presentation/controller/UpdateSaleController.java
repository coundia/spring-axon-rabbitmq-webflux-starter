package com.pcoundia.sales.presentation.controller;

import com.pcoundia.sales.application.mapper.SaleMapper;
import com.pcoundia.sales.application.command.UpdateSaleCommand;
import com.pcoundia.sales.application.dto.SaleRequest;
import com.pcoundia.sales.application.dto.SaleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/commands/sale")
@Tag(name = "Sale commands", description = "Endpoints for managing sales")
@Slf4j
public class UpdateSaleController {

private final CommandGateway commandGateway;

public UpdateSaleController(@Lazy CommandGateway commandGateway) {
this.commandGateway = commandGateway;
}

@PutMapping("/{id}")
@Operation(
summary = "Update an existing sale",
description = "Updates an existing sale by ID and returns the updated entity"
)
@ApiResponses(value = {
@ApiResponse(
responseCode = "200",
description = "Sale updated successfully",
content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleResponse.class))
),
@ApiResponse(
responseCode = "400",
description = "Invalid input data",
content = @Content
),
@ApiResponse(
responseCode = "500",
description = "Internal server error",
content = @Content
)
})
public CompletableFuture<ResponseEntity<SaleResponse>> updateSale(
	@Parameter(description = "The ID of the sale to update", required = true)
	@PathVariable String id,

	@io.swagger.v3.oas.annotations.parameters.RequestBody(
	description = "Sale data to update",
	required = true,
	content = @Content(schema = @Schema(implementation = SaleRequest.class))
	)
	@RequestBody SaleRequest saleRequest
	) {
	UpdateSaleCommand updateSaleCommand = SaleMapper.toUpdateCommand(saleRequest);

	return commandGateway.send(updateSaleCommand)
	.thenApply(res -> {
	log.info("Sale updated successfully with ID: {}", id);
	SaleResponse saleResponse = new SaleResponse(
		saleRequest.getId(),
		saleRequest.getQuantity(),
		saleRequest.getTotal_price()
	);
	return ResponseEntity.ok(saleResponse);
	})
	.exceptionally(ex -> {
	log.error("Failed to update sale: {}", ex.getMessage());
	return ResponseEntity.internalServerError().build();
	});
	}
}
