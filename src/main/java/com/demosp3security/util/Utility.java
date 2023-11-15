package com.demosp3security.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class Utility {

	public static ByteArrayOutputStream createExcel() throws IOException {
		SXSSFWorkbook workbook = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook = new SXSSFWorkbook();
		Sheet sheet = workbook.createSheet("Test Sheet");
		workbook.write(baos);
		workbook.close();
		return baos;
	}

	public static ByteArrayOutputStream createPdf() throws IOException, DocumentException {
		Rectangle pagesize = new Rectangle(1754, 1240);
		Document document = new Document(pagesize, 30, 30, 45, 30);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PdfWriter.getInstance(document, baos);
		document.open();
		document.add(new Paragraph("Test Pdf"));
		document.close();

		return baos;
	}

	public static ByteArrayOutputStream createZip() throws IOException, DocumentException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(baos))) {
			createFileToZip(zipOutputStream, "sample.xlsx", createExcel());
			createFileToZip(zipOutputStream, "sample.pdf", createPdf());
			zipOutputStream.finish();
		}

		return baos;
	}

	private static void createFileToZip(ZipOutputStream zipOutputStream, String fileName, ByteArrayOutputStream baos)
			throws IOException {
		if (baos != null) {
			// Add the byte array to the zip as an entry
			ZipEntry zipEntry = new ZipEntry(fileName);
			zipOutputStream.putNextEntry(zipEntry);
			zipOutputStream.write(baos.toByteArray());
			zipOutputStream.closeEntry();
		}
	}

	public static ByteArrayOutputStream pathToBos(String path) throws IOException {
		Path filePath = Paths.get(path);
		byte[] bytes = Files.readAllBytes(filePath);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(bytes);

		return outputStream;
	}

}
