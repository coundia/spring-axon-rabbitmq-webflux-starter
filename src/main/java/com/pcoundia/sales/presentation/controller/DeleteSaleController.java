package com.pcoundia.sales.presentation.controller;

import com.pcoundia.sales.application.mapper.*;
import com.pcoundia.sales.domain.valueObject.*;
import com.pcoundia.sales.domain.exception.*;
import com.pcoundia.sales.application.dto.*;
import com.pcoundia.sales.application.query.*;

import com.pcoundia.sales.application.command.DeleteSaleCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/commands/sale")
@Tag(name = "Sale commands", description = "Endpoints for managing sales")
@Slf4j
public class DeleteSaleController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public DeleteSaleController(@Lazy CommandGateway commandGateway, @Lazy QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a sale",
            description = "Deletes a sale based on the provided identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sale not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    public CompletableFuture<ResponseEntity<String>> deleteSale(
            @Parameter(description = "ID of the sale to delete", required = true)
            @PathVariable String id
    ) {
        if (id == null || id.isEmpty()) {
            return CompletableFuture.completedFuture(ResponseEntity.badRequest().body("Invalid id"));
        }

        log.info("Deleting Sale with id: {}", id);
        SaleId idVo = SaleId.create(id);

        return Mono.fromFuture(() -> queryGateway.query(
                                new FindByIdSaleQuery(idVo),
                                org.axonframework.messaging.responsetypes.ResponseTypes.multipleInstancesOf(SaleResponse.class)
                        )
                )
                .subscribeOn(Schedulers.boundedElastic()

                )
                .flatMap(foundSale -> {
                    if (foundSale == null) {
                        return Mono.error(new SaleNotFoundException(idVo));
                    }
                    return Mono.fromFuture(() ->
                                    commandGateway.send(new DeleteSaleCommand(idVo)))
                            .subscribeOn(Schedulers.boundedElastic());
                })
                .map(result -> ResponseEntity.ok("Sale deleted successfully"))
                .onErrorResume(SaleNotFoundException.class, ex -> {
                    log.info("Sale not found: {}", ex.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage()));
                })
                .onErrorResume(ex -> {
                    log.error("Error deleting Sale with id {}: {}", id, ex.getMessage(), ex);
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Error deleting Sale: " + ex.getMessage()));
                })
                .toFuture();
    }
}
