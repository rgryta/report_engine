import java.sql.*;

public class ReportManager {
    private static final String driverName="oracle.jdbc.driver.OracleDriver";

    private static final String conUrl="jdbc:oracle:thin:@localhost:1521/orclpdb";
    private String conUser="rgryta";
    private String conPswd="123456";

    private String sourceSQL = "select order_id,item_id,quantity from order_items";

    public void initialize(){
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(conUrl, conUser, conPswd);
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("with source as ("+sourceSQL+")" +
                    "select count(*) as rowcount from source");
            rs.next();
            float rowCount = rs.getFloat("rowcount");

            float sheetRows = 100;
            int sheetNo = (int) Math.ceil(rowCount/sheetRows);

            System.out.println(rowCount);
            DataExtractor de = new DataExtractor(driverName,conUrl,conUser,conPswd,sourceSQL);
            de.start();
        } catch (ClassNotFoundException|SQLException e) {
            e.printStackTrace();
        }
    }

}
