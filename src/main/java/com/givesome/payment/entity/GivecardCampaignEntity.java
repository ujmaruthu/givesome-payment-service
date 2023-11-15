package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "givesome_givecardcampaign", schema = "public")
public class GivecardCampaignEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "image_id")
	private Integer imageId;

	@Column(name = "identifier")
	private String identifier;

	@Column(name = "supplier_id")
	private Integer supplierId;

}