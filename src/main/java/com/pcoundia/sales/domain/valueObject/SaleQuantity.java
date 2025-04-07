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
public class SaleQuantity implements Serializable {

private Integer quantity;

public SaleQuantity(Integer quantity) {
this.quantity = quantity;
}

public static SaleQuantity create(Integer quantity) {
return new SaleQuantity(quantity);
}

public Integer value() {
return this.quantity;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof SaleQuantity that)) return false;
return this.quantity.equals(that.quantity);
}

@Override
public int hashCode() {
return java.util.Objects.hash(quantity);
}

@Override
public String toString() {
return String.valueOf(quantity);
}
}
