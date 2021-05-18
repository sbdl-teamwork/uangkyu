/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author alfian-andi
 */
public class Log {
    private int id;
    private User user;
    private String message;
    private String created_at;

    public int getId() {
        return id;
    }

    public Log setId(int id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Log setUser(User user) {
        this.user = user;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Log setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public Log setCreatedAt(String created_at) {
        this.created_at = created_at;
        return this;
    }
}
