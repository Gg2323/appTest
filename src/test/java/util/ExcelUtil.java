package util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	//	数据保存在resource底下的register.xlsx文件sheet1的第2行到第7行
	//确定数据是哪一行到哪一行，哪一列到哪一列
	/**
	 * 
	 * @param excelPath  文件路径 (类路径的根路径：/开头)
	 * @param sheetIndex sheet的索引
	 * @param startRow 开始行
	 * @param endRow 结束行（excel中的行号）
	 * @param startCell 开始列
	 * @param endCell 结束列（excel中的列号）
	 * @return 二维数组
	 * 
	 */
	public static Object[][] readExcel(String excelPath, int sheetIndex, int startRow, int endRow, int startCell,
			int endCell) {
		//创建一个二维数组
		//第2行到第7行  第1列到第4列
		//1到2有个几个数字--》2 --》（2-1） + 1
		//2到5有几个数字--》4个数字 --》（5-2） + 1
		Object[][] datas = new Object[endRow - startRow + 1][endCell - startCell + 1];

		//1：获得Excel文件输入流（加载资源作为流） -->"/register.xlsx"
		InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
		try {
			//2:获得工作簿-->面向接口编程
			Workbook workbook = WorkbookFactory.create(is);
			//3：得到sheet
			Sheet sheet = workbook.getSheetAt(sheetIndex);
			//4：循环所有的行
			for (int i = startRow; i <= endRow; i++) {
				//6：得到每一行
				Row row = sheet.getRow(i - 1); //
				//7:循环每一行的每一列
				for (int j = startCell; j <= endCell; j++) {
					//8：得到当前遍历的cell
					Cell cell = row.getCell(j - 1, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					//9：把所有列的类型都设置为String类型
					cell.setCellType(CellType.STRING);
					String cellValue = cell.getStringCellValue();

					//当i等于startRow，j等于startCell--》datas[0][0]
					//当i等于startRow，j等于startCell+1 --》datas[0][1]

					datas[i - startRow][j - startCell] = cellValue;

					//从1000取到1002行，从101列取到110列 --》3行10列
					//第2行到第7行  第1列到第4列  --》6行4列
					//第1行：
					//	第1列：data[0][0] = ?
					//	第2列：data[0][1]
					//	第3列：data[0][2]
					//	第4列：data[0][3]
					//第2行：
					//	第1列：data[1][0] = ?
					//	第2列：data[1][1]
					//	第3列：data[1][2]
					//	第4列：data[1][3]
					//	System.out.print("["+cellValue+"] - ");
				}
				//System.out.println();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return datas;
	}

	/**
	 * 读取excel某个sheet的所有数据
	 * @param excelPath
	 * @param sheetIndex
	 */
	public static void readExcelAllData(String excelPath, int sheetIndex) {

		InputStream is = ExcelUtil.class.getResourceAsStream(excelPath);
		XSSFWorkbook workbook = null;
		try {
			//2:获得工作簿-->面向接口编程
			workbook = new XSSFWorkbook(is);
			//3：得到sheet
			XSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			//3:得到最大行数
			int lastRowNum = sheet.getLastRowNum();
			//5：循环所有的行
			for (int i = 0; i <= lastRowNum; i++) {
				//得到每一行
				XSSFRow row = sheet.getRow(i);
				//得到当前行的最大列号（有几列就输出几）
				int lastCellNum = row.getLastCellNum();
				//遍历每行的所有列,
				for (int j = 0; j < lastCellNum; j++) {
					XSSFCell cell = row.getCell(j, MissingCellPolicy.CREATE_NULL_AS_BLANK);
					cell.setCellType(CellType.STRING);
					String cellValue = cell.getStringCellValue();
					//					System.out.print("[" + cellValue + "] - ");
				}
				//				System.out.println();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	//工具类中间，通常会写一个main--》方便测试、提供例子给其他用
	public static void main(String[] args) {
		/*		Object[][] datas = readExcel("/register.xlsx", 0, 2, 7, 1, 4);
				for (Object[] objects : datas) {
					for (Object object : objects) {
						System.out.print("[" + object + "] - ");
					}
					System.out.println();
				}*/
		StringBuffer buffer=new StringBuffer();
		buffer.append("ASDFGH");
		System.out.println(buffer.reverse());

		//readExcelAllData("/register.xlsx", 0);
	}

}
