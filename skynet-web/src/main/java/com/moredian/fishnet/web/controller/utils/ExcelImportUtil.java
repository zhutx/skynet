package com.moredian.fishnet.web.controller.utils;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelImportUtil {

	public ExcelImportUtil() {
	}
	
	private static List<String[]> read(Workbook wb,int columns,boolean hasReadFirstRow){
		// 创建一个list 用来存储读取的内容
		List<String[]> list = new ArrayList<String[]>();
		
		// 获取文件的指定工作表 默认的第一个
		Sheet sheet = wb.getSheetAt(0);
		List<Integer> blackRowList = new ArrayList<Integer>();
		for (int i = (hasReadFirstRow?0:1); i < sheet.getPhysicalNumberOfRows(); i++) {
			//定义本行
			Row row = sheet.getRow(i);
			if(row==null){
				blackRowList.add(i);
				continue;
			}
			
			//定义本行的列数
			if(columns==-1){
				columns = row.getPhysicalNumberOfCells();
			}
			
			boolean isRowBlank = true;//行为空
			for (int j = 0; j < columns; j++) {
				Cell cell = row.getCell(j);
				String value = "";
	            if(cell != null){
	                switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                    value = cell.getStringCellValue();
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    value = String.valueOf((int) cell.getNumericCellValue());
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                    value = String.valueOf(cell.getBooleanCellValue());
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                    value = String.valueOf(cell.getCellFormula());
	                    break;
	                case Cell.CELL_TYPE_BLANK:
	                	value = "";
	                    break;
	                default:
	                    break;
	                }
	                
	                if(!value.trim().equals("")){
	                	isRowBlank = false;
	                    break;
	                }
	            }
			}
			if(isRowBlank){//记录空行索引
				blackRowList.add(i);
			}
		}
		
		// 行数(表头及介绍不需要，从1开始)
		for (int i = (hasReadFirstRow?0:1); i < sheet.getPhysicalNumberOfRows(); i++) {
			
			if(blackRowList.contains(i)) continue;
			//定义本行
			Row row = sheet.getRow(i);
			
			if(row.getCell(0)==null) continue;
			
			//定义本行的列数
			if(columns==-1){
				columns = row.getPhysicalNumberOfCells();
			}
			// 创建一个数组 用来存储每一列的值
			String[] str = new String[columns];
			
			// 列数
			for (int j = 0; j < columns; j++) {
				
				// 获取本行第j列的值
				Cell cell = row.getCell(j);
				if(cell==null){
					 str[j]="";
					 continue;
				}
				if(cell.getCellType() == Cell.CELL_TYPE_STRING){//字符串
					str[j] = cell.getStringCellValue();
				}else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){//数字,包含日期数据的单元格也将返回数字
					if(HSSFDateUtil.isCellDateFormatted(cell)){//判断出单元格的值是否为日期
						Date date = cell.getDateCellValue();
						String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
						str[j]=dateStr;
					}else{
						cell.setCellType(Cell.CELL_TYPE_STRING);
						str[j] = cell.getStringCellValue();
					}
				}else if(cell.getCellType()==Cell.CELL_TYPE_BOOLEAN){
					str[j] = cell.getBooleanCellValue()+"";
				}else if(cell.getCellType()==Cell.CELL_TYPE_FORMULA){
					str[j] = String.valueOf(cell.getNumericCellValue());					
				}

			}
			// 把刚获取的列存入list
			list.add(str);
		}

		// 返回值集合
		return list;
    }
	
//	public static List<String[]> read(InputStream inputStream,boolean isExcel2003,int columns,boolean hasReadFirstRow){
//		List<String[]> dataLst = null;
//		try{
//		    Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream): new XSSFWorkbook(inputStream);
//		    dataLst = read(wb,columns,hasReadFirstRow);
//		}catch (IOException e){
//		    e.printStackTrace();
//		}
//		return dataLst;
//    }
	
	public static List<String[]> read(InputStream inputStream, String fileName,
			int columns, boolean hasReadFirstRow) {
		List<String[]> dataLst = new ArrayList<String[]>();
		if (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")) {
			return dataLst;
		}
		boolean isExcel2003 = true;
		if (fileName.endsWith(".xlsx")) {
			//System.out.println("导入的文件为.xlsx后缀");
			isExcel2003 = false;
		}
		try {
			Workbook wb = isExcel2003 ? new HSSFWorkbook(inputStream)
					: new XSSFWorkbook(inputStream);
			dataLst = read(wb, columns, hasReadFirstRow);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataLst;
	}

	// hasReadFirstRow 是否读取excel第一行
//	public static List<String[]> read(String fileName,String uploadFileName,int columns,boolean hasReadFirstRow){
//	    List<String[]> dataLst = new ArrayList<String[]>();
//	    if (fileName == null || (!uploadFileName.endsWith(".xlsx")&&(!uploadFileName.endsWith(".xls")))){
//	        return dataLst;
//	    }
//	    boolean isExcel2003 = true;
//	    if (uploadFileName.endsWith(".xlsx")){
//	        isExcel2003 = false;
//	    }
//	    File file = new File(fileName);
//	    if (file == null || !file.exists()){
//	        return dataLst;
//	    }
//	    try{
//	        dataLst = read(new FileInputStream(file), isExcel2003,columns,hasReadFirstRow);
//	    }catch (Exception ex){
//	        ex.printStackTrace();
//	    }
//	    return dataLst;
//	}
//	
	
}
