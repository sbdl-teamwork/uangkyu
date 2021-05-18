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
public class Type {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public Type setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Type setName(String name) {
        this.name = name;
        return this;
    }
}
