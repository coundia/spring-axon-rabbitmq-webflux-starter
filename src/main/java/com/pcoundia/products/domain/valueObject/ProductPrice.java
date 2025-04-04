package com.pcoundia.products.domain.valueObject;


import java.io.Serializable;

public class ProductPrice implements Serializable {

private final double price;

public ProductPrice(double price) {
this.price = price;
}

public static ProductPrice create(double price) {
return new ProductPrice(price);
}

public double value() {
return this.price;
}

@Override
public boolean equals(Object o) {
if (this == o) return true;
if (!(o instanceof ProductPrice that)) return false;
return Double.compare(that.price, this.price) == 0;
}

@Override
public int hashCode() {
return Double.hashCode(price);
}

@Override
public String toString() {
return String.valueOf(price);
}
}
