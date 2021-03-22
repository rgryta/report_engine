import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;

public class XLSXManager {

    public boolean create_datasheet(){
        String filePath = System.getProperty("user.dir");
        File file = new File(filePath + File.separator + "test.xlsx");
        if (file.exists()) //noinspection ResultOfMethodCallIgnored
            file.delete();

        System.setProperty("java.io.tmpdir", filePath); //path for SXSSF temporary files

        SXSSFWorkbook wb = new SXSSFWorkbook(-1);

        SXSSFSheet sh = wb.createSheet("sheet1");
        sh.setRowSumsBelow(false); //[-] row grouping button at the top
        sh.setRowSumsRight(false); //[-] column grouping button to the left

        sh.groupColumn(1,60); //range of system columns (first column has to be skipped so: +1)
        sh.groupColumn(62,99);

        int rowno=0;
        try {
        for (int i = 0; i < 10; i++) {
            File fileIn = new File(filePath + File.separator + "test_" + i + ".tmp");
            FileInputStream fis = new FileInputStream(fileIn);
            boolean cont = true;
            ObjectInputStream input = new ObjectInputStream(fis);
            while (cont) {
                try {
                    SQLRecord obj = (SQLRecord)input.readObject();

                    SXSSFRow row = sh.createRow(rowno++);
                    SXSSFCell cell;



                    for (int j = 0; j < 3; j++) {
                        cell = row.createCell(j, CellType.STRING);
                        cell.setCellValue(obj.columnValues[j]);
                    }

                    sh.flushRows();

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                catch(EOFException e){
                    cont = false;
                }

            }
            fis.close();
            //noinspection ResultOfMethodCallIgnored
            fileIn.delete();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        wb.dispose();
        return true;
    }
}
