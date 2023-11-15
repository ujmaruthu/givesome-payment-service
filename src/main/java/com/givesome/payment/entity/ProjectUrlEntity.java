package com.givesome.payment.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "givesome_projectextra", schema = "public")
public class ProjectUrlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column(name = "project_id")
	private Integer projectId;

	@Column(name = "exclusive_content_url")
	private String exclusiveContentUrl;

	@Column(name = "more_project_url")
	private String moreProjectUrl;


}