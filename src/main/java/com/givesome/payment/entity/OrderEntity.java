package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "shuup_order", schema = "public")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "identifier")
	private Integer identifier;

	@Column(name = "label")
	private String label;

	@Column(name = "created_on")
	private Instant createdOn;

	@Column(name = "modified_on")
	private Instant modifiedOn;

	@Column(name = "currency")
	private String currency;

	@Column(name = "display_currency")
	private String displayCurrency;

	@Column(name = "payment_method_name")
	private String paymentMethodName;

	@Column(name = "taxful_total_price_value")
	private BigDecimal taxfulTotalPriceValue;

	@Column(name = "taxless_total_price_value")
	private BigDecimal taxlessTotalPriceValue;

	@Column(name = "status_id")
	private Integer statusId;

	@Column(name = "shop_id")
	private Integer shopId;

	@Column(name = "deleted")
	private Boolean deleted;

	@Column(name = "payment_status")
	private Integer paymentStatus;

}