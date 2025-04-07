package com.pcoundia.sales.domain.valueObject;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Builder
public class SaleTotal_price implements Serializable {

private Double total_price;

public SaleTotal_price(Double total_price) {
this.total_price = total_price;
}

public static SaleTotal_price create(Double total_price) {
return new SaleTotal_price(total_price);
}

public Double value() {
return this.total_price;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof SaleTotal_price that)) return false;
return this.total_price.equals(that.total_price);
}

@Override
public int hashCode() {
return java.util.Objects.hash(total_price);
}

@Override
public String toString() {
return String.valueOf(total_price);
}
}
