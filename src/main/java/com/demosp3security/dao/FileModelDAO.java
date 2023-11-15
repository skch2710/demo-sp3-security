package com.demosp3security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demosp3security.model.FileModel;

import jakarta.transaction.Transactional;

public interface FileModelDAO extends JpaRepository<FileModel, Long> {
	
	@Transactional
	FileModel findByFileName(String fileName);
}
