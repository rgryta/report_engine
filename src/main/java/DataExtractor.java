import java.io.File;
import java.io.FileWriter;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataExtractor extends Thread{

    private ReportManager parent;
    private int extractorID;

    private Connection con;

    DataExtractor(ReportManager reportManager, int id)
            throws ClassNotFoundException, SQLException {

        super();
        parent = reportManager;
        Class.forName(parent.driverName);
        con = DriverManager.getConnection(parent.conUrl, parent.conUser, parent.conPswd);
        extractorID = id;
    }

    public void run(){
        connect();
    }

    public void connect(){
        try {
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("WITH SOURCE AS ("+parent.sourceSQL+")" +
                    "SELECT * FROM SOURCE " +
                    "ORDER BY "+String.join(",",parent.PK) +" "+
                    "OFFSET "+ (extractorID*parent.threadRows) +" " +
                    "ROWS FETCH NEXT "+ parent.threadRows +" " +
                    "ROWS ONLY");
            rs.setFetchSize(50);

            String filePath = System.getProperty("user.dir");
            File file = new File(filePath + File.separator + "test_"+ extractorID +".tmp");
            if (file.exists()) //noinspection ResultOfMethodCallIgnored
                file.delete();
            FileWriter writer = new FileWriter(file);


            ResultSetMetaData md = rs.getMetaData();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            int columnsNo = md.getColumnCount();

            String[] row;
            while(rs.next()) {
                row = new String[columnsNo];
                for (int i = 1; i <= columnsNo; i++)
                    switch (md.getColumnType(i)) {
                        case Types.DATE -> row[i - 1] = dateFormat.format(rs.getDate(i));
                        case Types.TIMESTAMP -> row[i - 1] = timestampFormat.format(rs.getTimestamp(i));
                        default -> row[i - 1] = rs.getString(i);
                    }
                writer.write(String.join(";",row)+System.lineSeparator());
            }

            con.close();
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
