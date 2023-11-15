package com.demosp3security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file_upload", schema = "file_upload")
public class FileModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="file_id")
	private Long fileId;

	@Column(name="file_name")
	private String fileName;

	@Column(name="file_type")
	private String fileType;

//	@Lob //(No need for pgAdmin)
	@Column(name="file_data")
	private byte[] fileData; 

}
