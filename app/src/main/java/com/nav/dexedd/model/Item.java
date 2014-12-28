package com.nav.dexedd.model;

/**
 * Item model
 * 
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Item {

    private Integer id;
    private String name;

    public Item() {}

    public Item(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
