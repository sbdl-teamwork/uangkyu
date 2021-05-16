/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author Alfian Andi Nugraha
 */
public class Database {
    public static Connection MySqlConfig;
    
    public static Connection ConfigDB() throws SQLException, ClassNotFoundException {
        try {
            String url = "jdbc:mysql://localhost:3306/db_uangkyu?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Database.MySqlConfig = DriverManager.getConnection(url, user, pass);
        } catch(SQLException err) {
            System.out.println("Koneksi gagal " + err.getMessage());
        }
        
        return Database.MySqlConfig;
    }
}
