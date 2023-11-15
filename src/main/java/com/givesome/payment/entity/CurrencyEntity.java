package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "currency_details", schema = "public")
public class CurrencyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "currency_code")
	private String currencyCode;

	@Column(name = "currency_name")
	private String currencyName;
	@Column(name = "currency_symbol")
	private String currencySymbol;



}