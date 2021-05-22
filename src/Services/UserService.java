/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.User;
import Utils.Database;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author alfian-andi
 */
public class UserService extends User {
    public UserService() {}
    
    public void register() throws Exception {
        String query = String.format(
            "CALL register_user('%s', '%s', '%s')", 
            this.getName(), this.getEmail(), this.getPassword()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    public User login() throws Exception {
        String query = String.format(
            "SELECT * FROM users WHERE email = '%s' AND password = '%s'",
            this.getEmail(), this.getPassword()
        );
        
        User user = new User();
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String createdAt = result.getString("created_at");
                String email = result.getString("email");
                String password = result.getString("password");
                
                user
                    .setId(id)
                    .setName(name)
                    .setPassword(password)
                    .setEmail(email)
                    .setCreatedAt(createdAt);
            }
        } catch (Exception err) {
            throw err;
        }
        
        return user;
    }
    
    public User getUserByEmail() throws Exception {
        String query = String.format(
            "SELECT * FROM users WHERE email = '%s'",
            this.getEmail()
        );
        
        User user = new User();
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                String createdAt = result.getString("created_at");
                String email = result.getString("email");
                String password = result.getString("password");
                
                user
                    .setId(id)
                    .setName(name)
                    .setPassword(password)
                    .setEmail(email)
                    .setCreatedAt(createdAt);
            }
        } catch (Exception err) {
            throw err;
        }
        
        if (user.getId() == 0) {
            throw new Exception("User tidak ditemukan");
        }
        
        return user;
    }
    
    public User getCurrentUser() throws Exception {
        File file = new File("email.txt");
        Scanner reader = new Scanner(file);
        String email = reader.nextLine();

        UserService userService = new UserService();
        userService.setEmail(email);

        return userService.getUserByEmail();   
    }
    
    public void logout() throws Exception {
        try {
            FileWriter file = new FileWriter("email.txt");
            file.write("");
            file.close();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
