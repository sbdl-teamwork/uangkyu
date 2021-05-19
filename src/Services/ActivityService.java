/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.Activity;
import Models.Type;
import Utils.Database;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Alfian Andi Nugraha
 */
public class ActivityService extends Activity {
    public ActivityService() {}
    
    public ActivityService(Activity activity) {
        this.setId(activity.getId());
        this.setDescription(activity.getDescription());
        this.setNominal(activity.getNominal());
        this.setCreatedAt(activity.getCreatedAt());
        this.setUpdatedAt(activity.getUpdatedAt());
        this.setUser(activity.getUser());
        this.setType(activity.getType());
    }
    
    public void insert() throws Exception {
        System.out.println(this.getType().getId());
        String query = String.format(
            "INSERT INTO %s (description, nominal, user_id, type, created_at, updated_at) VALUES (\"%s\", %f, %d, '%s', \"%s\", \"%s\")", 
            this.tableName, 
            this.getDescription(), 
            this.getNominal(), 
            this.getUser().getId(), 
            this.getType().getId(),
            this.getCreatedAt(), 
            this.getUpdatedAt()
        );
        System.out.println(query);
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    public void delete() throws Exception {
        String query = String.format(
            "DELETE FROM %s WHERE id = %d AND user_id = %d",
            this.tableName, this.getId(), this.getUser().getId()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    public void update() throws Exception {
        String query = String.format(
            "UPDATE %s SET description = '%s', nominal = %f, updated_at = \"%s\", type = '%s' WHERE id = %d AND user_id = %d",
            this.tableName, 
            this.getDescription(), 
            this.getNominal(), 
            this.getUpdatedAt(),
            this.getType().getId(),
            this.getId(),
            this.getUser().getId()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    private ArrayList<Activity> get(String querySelect) throws Exception {
        String query = querySelect;
        
        ArrayList<Activity> activities =  new ArrayList<Activity>();
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                Activity activity = new Activity();
                int id = result.getInt("id");
                String description = result.getString("description");
                float nominal = result.getFloat("nominal");
                String createdAt = result.getString("created_at");
                String updatedAt = result.getString("updated_at");
                
                Type type = new Type();
                String name = result.getString("name");
                String typeId = result.getString("type");
                type.setId(typeId).setName(name);
                
                activity
                    .setId(id)
                    .setDescription(description)
                    .setCreatedAt(createdAt)
                    .setUpdatedAt(updatedAt)
                    .setType(type)
                    .setNominal(nominal);
                
                activities.add(activity);
            }
            
        } catch (Exception err) {
            throw err;
        }
        
        return activities;
    }
    
    public ArrayList<Activity> getByInterval(String from, String to) throws Exception {
        String query = String.format(
            "SELECT * FROM %s INNER JOIN types ON activities.type = types.id WHERE activities.updated_at >= '%s' AND activities.updated_at <= '%s' AND user_id = %d ORDER BY updated_at DESC",
            this.tableName, from, to, this.getUser().getId()
        );
        
        ArrayList<Activity> activities =  new ArrayList<Activity>();
        
        try {
            activities = this.get(query);
        } catch (Exception err) {
            throw err;
        }
        
        return activities;
    }
    
    public ArrayList<Activity> getAll() throws Exception {
        String query = String.format(
            "SELECT * FROM %s ORDER BY updated_at DESC",
            this.tableName
        );
        
        ArrayList<Activity> activities =  new ArrayList<Activity>();
        
        try {
            activities = this.get(query);
        } catch (Exception err) {
            throw err;
        }
        
        return activities;
    }
    
    public float getTotalIncome(String from, String to) throws Exception {
        String query = String.format(
            "SELECT get_total_income(%d, '%s', '%s') AS income",
            this.getUser().getId(),
            from, to
        );
        
        float total = 0;
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            
            while(result.next()) {
                total = result.getFloat("income");
            }
        } catch (Exception err) {
            throw err;
        }
        
        return total;
    }
    
    public float getTotalExpense(String from, String to) throws Exception {
        String query = String.format(
            "SELECT get_total_expense(%d, '%s', '%s') AS expense",
            this.getUser().getId(),
            from, to
        );
        
        float total = 0;
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            
            while(result.next()) {
                total = result.getFloat("expense");
            }
        } catch (Exception err) {
            throw err;
        }
        
        return total;
    }
}
