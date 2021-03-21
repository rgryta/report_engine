import java.sql.*;

public class DataExtractor {

    private static final String driverName="oracle.jdbc.driver.OracleDriver";

    private static final String conUrl="jdbc:oracle:thin:@localhost:1521/orclpdb";
    private String conUser="rgryta";
    private String conPswd="123456";


    public void connect(){
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(conUrl, conUser, conPswd);
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select order_id,item_id,quantity from order_items");
            rs.setFetchSize(50);
            ResultSetMetaData md = rs.getMetaData();
            md.getColumnCount();
            System.out.println(md.getColumnName(1)+" "+md.getColumnLabel(1));
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

//step5 close the connection object
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
