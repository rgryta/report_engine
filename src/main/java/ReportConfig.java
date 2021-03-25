import java.util.ArrayList;
import java.util.List;

public class ReportConfig {
    public List<ReportCell> cells;
    public int[][] hideColumns;
    public String[][] groupingColumns;

    ReportConfig(){
        cells = new ArrayList<>();

        cells.add(new ReportCell("order_id"));
        cells.add(new ReportCell("item_id"));
        cells.add(new ReportCell("quantity"));

        hideColumns = new int[3][2];
        hideColumns[0] = new int[]{0, 2};
        hideColumns[1] = new int[]{1, 2};
        hideColumns[2] = new int[]{2, 2};

        groupingColumns = new String[2][];
        groupingColumns[0] = new String[]{"order_id"};
        groupingColumns[1] = new String[]{"item_id"};

    }
}
