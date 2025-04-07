package com.pcoundia.sales.infrastructure.repository;

import com.pcoundia.sales.infrastructure.entity.Sale;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SaleRepository extends ReactiveCrudRepository<Sale, String> {

@Query("SELECT * FROM sales LIMIT :limit OFFSET :offset")
Flux<Sale> findAllByPage(int limit, int offset);

@Query("INSERT INTO sales ( id ,   quantity ,   total_price  ) VALUES ( :id ,   :quantity ,   :total_price  ) RETURNING *")
Mono<Sale> insert( String id ,   Integer quantity ,   Double total_price  );

@Query("UPDATE sales SET  quantity = :quantity,   total_price = :total_price  WHERE id = :id")
Mono<Integer> updateAllById(String id ,  Integer quantity ,  Double total_price  );

@Query("SELECT * FROM sales where id = :id")
Flux<Sale> getById(String id);


}
