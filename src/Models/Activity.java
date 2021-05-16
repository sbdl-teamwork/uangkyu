/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import Utils.Database;

/**
 *
 * @author Alfian Andi Nugraha
 */
public class Activity {
    private int id;
    private String description;
    private float nominal;
    private String createdAt;
    private String updatedAt;
    protected String tableName = "activities";
    
    public int getId() {
        return id;
    }

    public Activity setId(int id) {
        this.id = id;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Activity setDescription(String description) {
        this.description = description;
        return this;
    }

    public float getNominal() {
        return nominal;
    }

    public Activity setNominal(float nominal) {
        this.nominal = nominal;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Activity setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public Activity setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }
}
