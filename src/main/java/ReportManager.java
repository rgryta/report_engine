import java.sql.*;

public class ReportManager {
    public String driverName="oracle.jdbc.driver.OracleDriver";
    public String conUrl="jdbc:oracle:thin:@localhost:1521/orclpdb";
    public String conUser="rgryta";
    public String conPswd="123456";

    public String sourceSQL = "select order_id,item_id,quantity from order_items";
    public String[] PK = {"order_id","item_id"};

    public int threadRows;


    public void initialize(){
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(conUrl, conUser, conPswd);
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("with source as ("+sourceSQL+")" +
                    "select count(*) as rowcount from source");
            rs.next();
            float rowCount = rs.getFloat("rowcount");
            int threadNo = 10;
            threadRows = (int)Math.ceil(rowCount/(float)threadNo);

            DataExtractor[] extractors = new DataExtractor[threadNo];
            for (int i=0;i<threadNo;i++) {
                extractors[i] = new DataExtractor(this,i);
                extractors[i].start();
            }
            for (int i=0;i<threadNo;i++) {
                extractors[i].join();
            }
        } catch (ClassNotFoundException|SQLException|InterruptedException e) {
            e.printStackTrace();
        }
    }

}
