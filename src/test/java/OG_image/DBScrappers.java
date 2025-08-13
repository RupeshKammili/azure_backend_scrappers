package OG_image;

import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class DBScrappers {

    @DataProvider()
    public static Object[] needTotestUrls() throws IOException {
        // Read file path from environment variable or fallback to relative path
        String excelPath = System.getenv("EXCEL_UPLOAD_PATH");
        if (excelPath == null || excelPath.isEmpty()) {
            excelPath = "./Uploads/Scrappers.xlsx";  // default fallback path
        }

        FileInputStream stream = new FileInputStream(new File(excelPath));
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int row = sheet.getPhysicalNumberOfRows();
        Object[] c = new Object[row];
        for (int i = 0; i < row; i++) {
            c[i] = sheet.getRow(i).getCell(0).getStringCellValue();
            System.out.println(c[i]);
        }

        workbook.close();
        stream.close();
        return c;
    }

}
