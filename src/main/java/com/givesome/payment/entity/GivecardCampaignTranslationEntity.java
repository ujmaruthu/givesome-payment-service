package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "givesome_givecardcampaign_translation", schema = "public")
public class GivecardCampaignTranslationEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "message")
	private String message;

}