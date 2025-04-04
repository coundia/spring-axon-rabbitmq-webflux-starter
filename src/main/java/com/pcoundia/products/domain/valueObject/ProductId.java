package com.pcoundia.products.domain.valueObject;


import java.io.Serializable;

public class ProductId implements Serializable {

private final String id;

public ProductId(String id) {
this.id = id;
}

public static ProductId create(String id) {
return new ProductId(id);
}

public String value() {
return this.id;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof ProductId that)) return false;
return id.equals(that.id);
}

@Override
public int hashCode() {
return id.hashCode();
}

@Override
public String toString() {
return String.valueOf(id);
}
}
