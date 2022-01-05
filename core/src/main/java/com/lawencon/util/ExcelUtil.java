package com.lawencon.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author lawencon05
 */
public class ExcelUtil {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private XSSFCell cell;
	private ByteArrayOutputStream bos;

	public void initRead(String sheetName, InputStream file) throws Exception {
		if (workbook == null)
			workbook = new XSSFWorkbook(file);

		sheet = workbook.getSheet(sheetName);
	}

	public void initWrite(String... sheetNames) throws Exception {
		workbook = new XSSFWorkbook();
		for (String sheetName : sheetNames) {
			workbook.createSheet(sheetName);
		}
		bos = new ByteArrayOutputStream();
	}
	

	public String getCellData(int rowNumber, int cellNumber) {
		try {
			cell = sheet.getRow(rowNumber).getCell(cellNumber);
			if(cell != null) {
				CellType type = cell.getCellType();
				if (type == CellType.STRING) {
					return cell.getRichStringCellValue().toString();
				} else if (type == CellType.NUMERIC) {
					return String.valueOf(cell.getNumericCellValue());
				} else if (type == CellType.BOOLEAN) {
					return String.valueOf(cell.getBooleanCellValue());
				} else if (type == CellType.BLANK) {
					return null;
				}
				
			} else {
				return null;
			}

			return cell.getStringCellValue();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getRowCountInSheet() {
		return sheet.getLastRowNum() + 1;
	}

	public int getColCountInRow(int row) {
		return sheet.getRow(row).getLastCellNum();
	}

	public void setCellValue(int rowNumber, List<String> cellValue) throws Exception {
		Row row = sheet.createRow(rowNumber);
		for (int i = 0; i < cellValue.size(); i++) {
			row.createCell(i).setCellValue(cellValue.get(i));
		}
	}

	public void setCellValue(String sheetName, int rowNumber, List<String> cellValue) throws Exception {
		sheet = workbook.getSheet(sheetName);
		setCellValue(rowNumber, cellValue);
	}

	public byte[] getByteArrayFile() throws Exception {
		workbook.write(bos);
		bos.close();
		return bos.toByteArray();
	}

}