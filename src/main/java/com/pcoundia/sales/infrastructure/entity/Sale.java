package com.pcoundia.sales.infrastructure.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import com.pcoundia.sales.domain.valueObject.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("sales")
public class Sale{

@Id
private String id;
private   Integer quantity;
private   Double total_price;

}
