import java.io.Serializable;

public class SQLRecord implements Serializable {
    public String[] columnValues;

    public SQLRecord(int inColumnsNo){
        columnValues = new String[inColumnsNo];
    }
}
