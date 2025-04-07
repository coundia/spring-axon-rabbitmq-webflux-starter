package com.pcoundia.sales.presentation.controller;

import com.pcoundia.sales.application.mapper.SaleMapper;
import com.pcoundia.sales.application.command.CreateSaleCommand;
import com.pcoundia.sales.application.dto.SaleRequest;
import com.pcoundia.sales.application.dto.SaleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
public class AddSaleController {

private final CommandGateway commandGateway;

public AddSaleController(@Lazy CommandGateway commandGateway) {
this.commandGateway = commandGateway;
}

@PostMapping
@Operation(
summary = "Create a new sale",
description = "Creates a new sale and returns the created entity",
requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
description = "Sale request payload",
required = true,
content = @Content(schema = @Schema(implementation = SaleRequest.class))
)
)
@ApiResponses(value = {
@ApiResponse(responseCode = "200", description = "Successfully created",
content = @Content(schema = @Schema(implementation = SaleResponse.class))),
@ApiResponse(responseCode = "500", description = "Internal server error",
content = @Content(schema = @Schema()))
})
public CompletableFuture<ResponseEntity<SaleResponse>> addSale(@RequestBody SaleRequest saleRequest) {
	CreateSaleCommand createSaleCommand = SaleMapper.toCommand(saleRequest);
	return commandGateway.send(createSaleCommand)
	.thenApply(id -> {
	log.info("Sale created successfully with ID: {}", id);
	SaleResponse saleResponse = new SaleResponse(
		saleRequest.getId(),
		saleRequest.getQuantity(),
		saleRequest.getTotal_price()
	);
	return ResponseEntity.ok(saleResponse);
	})
	.exceptionally(ex -> {
	log.error("Failed to create sale: {}", ex.getMessage());
	return ResponseEntity.internalServerError().build();
	});
	}
	}
