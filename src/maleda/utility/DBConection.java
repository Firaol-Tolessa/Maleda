package maleda.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConection {

    public static Connection con;
    public static Statement st;
    public static ResultSet rs;

    static {
        try {
            connect();
        } catch (Exception e) {
            AlertDefn.methodERROR("There is problem of database connection!!");
            System.out.println("Error" + e);
        }
    }

    private static void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/maleda_database", "root", "");
        st = con.createStatement();
        System.out.println("Connected");
    }

    public static ResultSet getData(String query) {
        
        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            //AlertDefn.methodERROR("THe data cannot fechtched from the databse!!");
            System.out.println("Exception: " + ex.getMessage());
        }
        return rs;
    }

    public static void updateData(String query) {
        
        try {
            st.executeUpdate(query);
            AlertDefn.showNotification("REGISTRATION SUCCESS", "The data is registered successfuly.");
                    } catch (SQLException ex) {
            AlertDefn.methodERROR("The data is not registred!!");
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public static void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
