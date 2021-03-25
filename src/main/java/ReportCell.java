import org.apache.poi.ss.usermodel.CellType;

public class ReportCell {
    public String SQLColumnName;
    public CellType excelCellType;
    public String excelHeader;
    public String totalsMode;

    ReportCell (String inColumnName){
        SQLColumnName = inColumnName;
        excelCellType = CellType.STRING;
        excelHeader = inColumnName;
        totalsMode = "COUNT";
    }
}
