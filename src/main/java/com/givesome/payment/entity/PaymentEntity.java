package com.givesome.payment.entity;



import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "payment_details_new", schema = "public")
public class PaymentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "payment_id")
	private String paymentId;

	@Column(name = "amount")
	private Long amount;

	@Column(name = "status")
	private String status;

	@Column(name = "amount_refunded")
	private Long amountRefunded;

	@Column(name = "captured")
	private Boolean captured;

	@Column(name = "created_at")
	private Instant createdAt;

	@Column(name = "currency")
	private String currency;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "description")
	private String description;

	@Column(name = "failure_code")
	private String failureCode;

	@Column(name = "failure_message")
	private String failureMessage;

	@Column(name = "livemode")
	private Boolean livemode;

	@Column(name = "balance_transaction")
	private String balanceTransaction;

	@Column(name = "payment_method")
	private String paymentMethod;

	@Column(name = "receipt_url")
	private String receiptUrl;

	@Column(name = "product_id")
	private Integer productId;

	@Column(name = "supplier_id")
	private Integer supplierId;

	@Column(name = "postal_code")
	private String postalCode;

}