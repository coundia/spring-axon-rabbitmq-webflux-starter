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
public class SaleId implements Serializable {

private String id;

public SaleId(String id) {
this.id = id;
}

public static SaleId create(String id) {
return new SaleId(id);
}

public String value() {
return this.id;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof SaleId that)) return false;
return this.id.equals(that.id);
}

@Override
public int hashCode() {
return java.util.Objects.hash(id);
}

@Override
public String toString() {
return String.valueOf(id);
}
}
