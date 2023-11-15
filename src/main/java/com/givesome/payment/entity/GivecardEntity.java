package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "givesome_givecard", schema = "public")
public class GivecardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "balance")
	private Integer balance;

	@Column(name = "code")
	private String code;

	@Column(name = "redeemed_on")
	private Instant redeemedOn;

	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "batch_id")
	private Integer batchId;

}