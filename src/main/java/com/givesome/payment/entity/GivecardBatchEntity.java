package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "givesome_givecardbatch", schema = "public")
public class GivecardBatchEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "redemption_start_date")
	private Date redemptionStartDate;

	@Column(name = "redemption_end_date")
	private Date redemptionEndDate;

	@Column(name="value")
	private Integer value;

	@Column(name="amount")
	private Integer amount;

	@Column(name = "code")
	private String code;

	@Column(name = "supplier_id")
	private Integer supplierId;

	@Column(name = "restriction_type")
	private Integer restrictionType;

	@Column(name = "campaign_id")
	private Integer campaignId;

}