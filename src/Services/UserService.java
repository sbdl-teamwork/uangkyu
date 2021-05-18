/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.User;
import Utils.Database;
import java.sql.Statement;

/**
 *
 * @author alfian-andi
 */
public class UserService extends User {
    public UserService() {}
    
    public void register() throws Exception {
        String query = String.format(
            "INSERT INTO users (name, email, password) VALUES ('%s', '%s', '%s')", 
            this.getName(), this.getEmail(), this.getPassword()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
}
