package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "filer_file", schema = "public")
public class GivecardCampaignImageEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "file")
	private String file;

	@Column(name = "original_filename")
	private String originalFilename;
	
}