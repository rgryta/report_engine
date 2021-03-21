import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class XLSXManager {

    public boolean create_datasheet(){
        String filePath = System.getProperty("user.dir");
        File file = new File(filePath + File.separator + "test.xlsx");
        if (file.exists()) {
            file.delete();
        }

        System.setProperty("java.io.tmpdir", filePath); //path for SXSSF temporary files

        SXSSFWorkbook wb = new SXSSFWorkbook(-1);

        SXSSFSheet sh = wb.createSheet("sheet1");
        sh.setRowSumsBelow(false); //[-] row grouping button at the top
        sh.setRowSumsRight(false); //[-] column grouping button to the left

        sh.groupColumn(1,60); //range of system columns (first column has to be skipped so: +1)
        sh.groupColumn(62,99);

        int counter=0;
        int lastIdx=0;
        double cellValue = 0;

        for (int i = 0; i < 10000; i++){

            SXSSFRow row = sh.createRow(i);
            SXSSFCell cell;
            cell = row.createCell(0, CellType.NUMERIC);
            cell.setCellValue(i/1000);

            // if it's the 1st row then get the value to the supporting variable
            // change to array if grouping done by multiple columns
            if (i==0) cellValue = cell.getNumericCellValue();

            // check if we're still iterating over a new group
            if (cellValue!=cell.getNumericCellValue()){
                // if it's a new group and there are more than 1 rows within
                // the group then group the group and set new lastIdx
                if (counter!=1){
                    cellValue = cell.getNumericCellValue();
                    sh.groupRow(lastIdx+1,i-1); //groups have to start from the 2nd row in a group in excel
                    lastIdx=i;
                }
                counter=1;
            }
            else {
                counter++;
            }

            for (int j = 1; j < 100; j++) {
                cell = row.createCell(j, CellType.NUMERIC);
                cell.setCellValue((i+j)%100);
            }

            if ((i%120==0)&&(i!=0)) {
                try {
                    if (counter!=1) {
                        sh.groupRow(lastIdx + 1, i);
                        lastIdx = i;
                    }
                    sh.flushRows();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wb.dispose();
        return true;
    }
}
