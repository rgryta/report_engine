import org.apache.poi.ss.usermodel.CellType;

import java.util.HashMap;
import java.util.Map;

public class ReportConfig {
    public Map<String,CellType> typeColumnGroups = new HashMap<>();
    public int[][] hideColumns;
    public String[][] groupingColumns;

    ReportConfig(){
        String[] sourceColumns = {"a","b"};
        CellType[] sourceTypes = {CellType.STRING,CellType.STRING};
        String[] targetColumns = {"c","d"};
        CellType[] targetTypes = {CellType.STRING,CellType.STRING};
        String[] comparisonColumns = {"e","f","g"};
        CellType[] comparisonTypes = {CellType.STRING,CellType.STRING,CellType.STRING};

        for (int i=0;i<=sourceColumns.length;i++) typeColumnGroups.put(sourceColumns[i],sourceTypes[i]);
        for (int i=0;i<=sourceColumns.length;i++) typeColumnGroups.put(targetColumns[i],targetTypes[i]);
        for (int i=0;i<=sourceColumns.length;i++) typeColumnGroups.put(comparisonColumns[i],comparisonTypes[i]);

        hideColumns = new int[3][];
        hideColumns[0] = new int[]{1, 1};
        hideColumns[1] = new int[]{1, 1};
        hideColumns[2] = new int[]{1, 2};



    }
}
