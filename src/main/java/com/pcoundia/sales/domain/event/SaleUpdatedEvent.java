package com.pcoundia.sales.domain.event;

	import com.pcoundia.sales.domain.valueObject.*;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleUpdatedEvent implements Serializable {

	private SaleId id;
	private SaleQuantity quantity;
	private SaleTotal_price total_price;



}
