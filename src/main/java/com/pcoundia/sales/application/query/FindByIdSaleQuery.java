package com.pcoundia.sales.application.query;

import com.pcoundia.sales.domain.valueObject.*;
import reactor.core.publisher.Flux;

public class FindByIdSaleQuery {

private final SaleId  id;

public FindByIdSaleQuery( SaleId id) {
	this.id = id;
}

public SaleId  getId() {
return id;
}
}
