package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider 1
	
	@DataProvider(name="loginData")
	public String[][] getData() throws IOException{
		String path = ".\\testData\\OpenCart_LoginData.xlsx";
		
		ExcelUtility xlUtil = new ExcelUtility(path);
		
		int totalRows = xlUtil.getRowCount("Data");
		int totalColumns = xlUtil.getCellCount("Data", 1);
		
		String loginData[][] = new String[totalRows][totalColumns];
		
		for(int r=1; r<=totalRows;r++) {
			
			for(int c=0; c<totalColumns; c++) {
				loginData[r-1][c] = xlUtil.getCellData("Data", r, c);
			}
		}
		return loginData;
	}
	
	//DataProvider 2
	
	
	//DataProvider 3
	
}
