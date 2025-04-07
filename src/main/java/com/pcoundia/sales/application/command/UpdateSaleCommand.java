package com.pcoundia.sales.application.command;

import com.pcoundia.sales.domain.valueObject.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.util.Date;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSaleCommand implements Serializable{
@TargetAggregateIdentifier
	private SaleId id;
	private SaleQuantity quantity;
	private SaleTotal_price total_price;



}
