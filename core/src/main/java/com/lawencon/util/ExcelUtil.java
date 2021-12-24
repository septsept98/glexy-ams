package com.lawencon.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author lawencon05
 */
public class ExcelUtil {

	private HSSFWorkbook workbook;
	private HSSFSheet sheet;
	private HSSFCell cell;
	private ByteArrayOutputStream bos;

	/**
	 * Empty InputStream means Create New Workbook
	 * 
	 * @param sheetName
	 * @param file
	 * @throws Exception
	 */
	public void init(String sheetName, InputStream file) throws Exception {
		if (file != null) {
			workbook = new HSSFWorkbook(file);
			sheet = workbook.getSheet(sheetName);
		} else {
			workbook = new HSSFWorkbook();
			sheet = workbook.createSheet(sheetName);
			bos = new ByteArrayOutputStream();
		}
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

	public void setCellValue(int rowNumber, String[] cellValue) throws Exception {
		Row row = sheet.createRow(rowNumber);
		for (int i = 0; i < cellValue.length; i++) {
			row.createCell(i).setCellValue(cellValue[i]);
		}

		workbook.write(bos);
	}

	public byte[] getByteArrayFile() throws Exception {
		bos.close();
		return bos.toByteArray();
	}

}