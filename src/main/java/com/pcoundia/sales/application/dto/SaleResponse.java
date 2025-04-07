package com.pcoundia.sales.application.dto;

import com.pcoundia.sales.domain.valueObject.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "SaleResponse", description = "Response payload for Sale")
public class SaleResponse implements Serializable {

	@Schema(description = "", example = "")
	private String id;
	@Schema(description = "", example = "")
	private Integer quantity;
	@Schema(description = "", example = "")
	private Double total_price;

}
