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
        this.setDateAt(activity.getDateAt());
    }
    
    public void delete() throws Exception {
        String query = String.format(
            "CALL delete_activity(%d, %d)",
            this.getId(), this.getUser().getId()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    public ArrayList<Activity> getByInterval(String from, String to) throws Exception {
        String query = String.format(
            "SELECT * FROM transaction_activities WHERE date_at >= '%s' AND date_at <= '%s' AND user_id = %d ORDER BY date_at DESC",
            from, to, this.getUser().getId()
        );
        
        ArrayList<Activity> activities =  new ArrayList<Activity>();
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            ResultSet result = statement.executeQuery(query);
            while(result.next()) {
                Activity activity = new Activity();
                int id = result.getInt("id");
                String description = result.getString("description");
                float nominal = result.getFloat("nominal");
                String dateAt = result.getString("date_at");
                String createdAt = result.getString("created_at");
                String updatedAt = result.getString("updated_at");
                
                Type type = new Type();
                String name = result.getString("name");
                String typeId = result.getString("type");
                type.setId(typeId).setName(name);
                
                activity
                    .setId(id)
                    .setDescription(description)
                    .setDateAt(dateAt)
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
    
    public void insertIncome() throws Exception {
        String query = String.format(
            "CALL insert_income_activity('%s', %f, %d, '%s')",
            this.getDescription(), 
            this.getNominal(), 
            this.getUser().getId(), 
            this.getDateAt()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    public void insertExpense() throws Exception {
        String query = String.format(
            "CALL insert_expense_activity('%s', %f, %d, '%s')",
            this.getDescription(), 
            this.getNominal(), 
            this.getUser().getId(), 
            this.getDateAt()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    public void updateIncome() throws Exception {
        String query = String.format(
            "CALL update_income_activity(%d, '%s', %f, %d, '%s')",
            this.getId(),
            this.getDescription(), 
            this.getNominal(), 
            this.getUser().getId(), 
            this.getDateAt()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
    
    public void updateExpense() throws Exception {
        String query = String.format(
            "CALL update_expense_activity(%d, '%s', %f, %d, '%s')",
            this.getId(),
            this.getDescription(), 
            this.getNominal(), 
            this.getUser().getId(), 
            this.getDateAt()
        );
        
        try {
            Statement statement = Database.ConfigDB().createStatement();
            statement.executeUpdate(query);
        } catch (Exception err) {
            throw err;
        }
    }
}
