package com.demosp3security.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demosp3security.dao.FileModelDAO;
import com.demosp3security.dto.EmployeeDetailsDTO;
import com.demosp3security.dto.EmployeeSearch;
import com.demosp3security.dto.Result;
import com.demosp3security.model.FileModel;
import com.demosp3security.service.EmployeeService;
import com.demosp3security.util.Utility;
import com.itextpdf.text.DocumentException;

@RestController
@RequestMapping("/api/public")
public class TestTwoController {
	
	@Autowired
	private FileModelDAO fileModelDAO;
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/save-employee")
	public ResponseEntity<?> saveEmployees(@RequestBody EmployeeDetailsDTO details) {
		Result result = employeeService.saveEmployee(details);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/search-employees")
	public ResponseEntity<?> searchEmployee(@RequestBody EmployeeSearch search) {
		Result result = employeeService.searchEmployee(search);
		return ResponseEntity.ok(result);
	}

	@PostMapping(path = "/upload-file", consumes = { "application/json", "multipart/form-data" })
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

		FileModel fileModel = new FileModel();
//		fileModel.setFileData(Base64.encode(file.getBytes()));
		fileModel.setFileData(Base64.getEncoder().encode(file.getBytes()));
		fileModel.setFileName(file.getOriginalFilename());
		fileModel.setFileType(file.getContentType());

		fileModel = fileModelDAO.save(fileModel);

		String result = fileModel.getFileId() != null ? "File Uploaded" : "Failed";

		return ResponseEntity.ok(result);
	}

	@GetMapping("/getFileByName/{fileName}")
	public ResponseEntity<?> getRemittancePdf(@PathVariable("fileName") String fileName) throws IOException {

		FileModel fileModel = fileModelDAO.findByFileName(fileName);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(fileModel.getFileType()));
		headers.setContentDispositionFormData("attachment", fileModel.getFileName());

//		InputStreamResource inputStreamResource = new InputStreamResource(
//				new ByteArrayInputStream(Base64.decode(fileModel.getFileData())));
		InputStreamResource inputStreamResource = new InputStreamResource(
				new ByteArrayInputStream(Base64.getDecoder().decode(fileModel.getFileData())));

		return ResponseEntity.ok().headers(headers).body(inputStreamResource);
	}

	
	@GetMapping("/create-and-upload")
	public ResponseEntity<?> createAndUpload() throws IOException, DocumentException {

		List<FileModel> fileModels = new ArrayList<>();
		
		FileModel excelFileModel = new FileModel();
		//For Excel
//		excelFileModel.setFileData(Base64.encode(Utility.createExcel().toByteArray()));
		excelFileModel.setFileData(Base64.getEncoder().encode(Utility.createExcel().toByteArray()));
		excelFileModel.setFileName("sample1.xlsx");
		excelFileModel.setFileType(MediaType.APPLICATION_OCTET_STREAM.toString());

		fileModels.add(excelFileModel);
		
		FileModel pdfFileModel = new FileModel();
		//For Pdf
//		pdfFileModel.setFileData(Base64.encode(Utility.createPdf().toByteArray()));
		pdfFileModel.setFileData(Base64.getEncoder().encode(Utility.createPdf().toByteArray()));
		pdfFileModel.setFileName("sample1.pdf");
		pdfFileModel.setFileType(MediaType.APPLICATION_PDF.toString());
		
		fileModels.add(pdfFileModel);
		
		FileModel zipFileModel = new FileModel();
		
		//For Zip
//		zipFileModel.setFileData(Base64.encode(Utility.createZip().toByteArray()));
		zipFileModel.setFileData(Base64.getEncoder().encode(Utility.createZip().toByteArray()));
		zipFileModel.setFileName("sample1.zip");
		zipFileModel.setFileType(MediaType.APPLICATION_OCTET_STREAM.toString());
		
		fileModels.add(zipFileModel);
		
		fileModels = fileModelDAO.saveAll(fileModels);

		String result = !fileModels.isEmpty() ? "Files Uploaded" : "Failed";

		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/test-get-public")
	public ResponseEntity<?> testGet() {
		return ResponseEntity.ok("Test Get public");
	}
	
	@PostMapping("/test-post-public")
	public ResponseEntity<?> testPost() {
		return ResponseEntity.ok("Test Post public");
	}

}
