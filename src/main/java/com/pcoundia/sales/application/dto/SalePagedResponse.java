package com.pcoundia.sales.application.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "SalePagedResponse", description = "Paginated response for Sale results")
public class SalePagedResponse implements Serializable {

@Schema(description = "List of paginated Sale items")
private List<SaleResponse> content;

	@Schema(description = "Current page number", example = "0")
	private int page;

	@Schema(description = "Page size", example = "10")
	private int size;

	@Schema(description = "Total number of elements", example = "100")
	private long totalElements;

	@Schema(description = "Total number of pages", example = "10")
	private long totalPages;

	public static SalePagedResponse from(List<SaleResponse> content, int page, int size, long totalElements, long totalPages) {
		return SalePagedResponse.builder()
		.content(content)
		.page(page)
		.size(size)
		.totalElements(totalElements)
		.totalPages(totalPages)
		.build();
		}
}
