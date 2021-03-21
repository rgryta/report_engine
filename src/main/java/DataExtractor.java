import java.sql.*;

public class DataExtractor extends Thread{

    private Connection con;

    private String sourceSQL;


    DataExtractor(String driver, String connection,
                  String username, String password,
                  String source)
            throws ClassNotFoundException, SQLException {
        super();
        Class.forName(driver);
        con = DriverManager.getConnection(connection, username, password);
        sourceSQL = source;
    }

    public void run(){
        connect();
    }

    public void connect(){
        try {
            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("with source as ("+sourceSQL+")" +
                    "select * from source");
            rs.setFetchSize(50);
            ResultSetMetaData md = rs.getMetaData();
            md.getColumnCount();

            System.out.println(md.getColumnName(1)+" "+md.getColumnLabel(1));
            //while(rs.next())
            //    System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));

//step5 close the connection object
            con.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
