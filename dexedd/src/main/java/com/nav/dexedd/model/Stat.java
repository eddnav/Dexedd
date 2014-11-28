package com.nav.dexedd.model;

/**
 * Class represents a single base stat value and its effort yield.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Stat {

    /**
     * Max stat value.
     */
    public final static int MAX_STAT_VALUE = 255;

    private Integer id;
    private Integer base;
    private Integer effort;

    public Stat(Integer id, Integer base, Integer effort) {
        this.id = id;
        this.base = base;
        this.effort = effort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBase() {
        return base;
    }

    public void setBase(Integer base) {
        this.base = base;
    }

    public Integer getEffort() {
        return effort;
    }

    public void setEffort(Integer effort) {
        this.effort = effort;
    }

}
