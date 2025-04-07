package com.pcoundia.sales.application.mapper;

import com.pcoundia.sales.application.dto.*;
import com.pcoundia.sales.domain.valueObject.*;
import com.pcoundia.sales.domain.*;
import com.pcoundia.sales.infrastructure.entity.*;
import com.pcoundia.sales.application.command.*;

public class SaleMapper {

public static SaleResponse toResponse(Sale entity) {
return new SaleResponse(
             entity.getId(),
             entity.getQuantity(),
             entity.getTotal_price()
);
}

public static Sale toEntity(SaleRequest request) {
return new Sale(
            request.getId(),
            request.getQuantity(),
            request.getTotal_price()
);
}

public static CreateSaleCommand toCommand(SaleRequest request) {
return new CreateSaleCommand(
            SaleId.create(request.getId()),
            SaleQuantity.create(request.getQuantity()),
            SaleTotal_price.create(request.getTotal_price())
);
}

public static UpdateSaleCommand toUpdateCommand(SaleRequest request) {
return new UpdateSaleCommand(
            SaleId.create(request.getId()),
            SaleQuantity.create(request.getQuantity()),
            SaleTotal_price.create(request.getTotal_price())
);
}


public static DeleteSaleCommand toDeleteCommand(SaleId id) {
return new DeleteSaleCommand(id);
}

}
