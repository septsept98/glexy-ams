package com.lawencon.util;

import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelUtil {

	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFCell cell;

	public void init(String sheetName, InputStream file) throws Exception {
		workbook = new HSSFWorkbook(file);
		sheet = workbook.getSheet(sheetName);
	}

	public String getCellData(int rowNumber, int cellNumber) {
		try {
			cell = sheet.getRow(rowNumber).getCell(cellNumber);

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

}