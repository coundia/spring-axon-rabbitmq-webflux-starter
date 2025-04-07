package com.pcoundia.sales.domain.exception;

import com.pcoundia.sales.domain.valueObject.SaleId;

public class SaleNotFoundException extends RuntimeException {

public SaleNotFoundException(SaleId id) {
super("Sale with ID " + id + " not found");
}
}
