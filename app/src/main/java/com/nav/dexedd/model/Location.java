package com.nav.dexedd.model;

/**
 * Location model
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Location {

    private Integer id;
    private String name;
    private Region region;

    public Location() {}

    public Location(Integer id, String name, Region region) {
        this.id = id;
        this.name = name;
        this.region = region;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}
