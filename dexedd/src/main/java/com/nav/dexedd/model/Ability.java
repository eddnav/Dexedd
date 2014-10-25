package com.nav.dexedd.model;

/**
 * Comment.
 *
 * @author Eduardo Naveda
 * @since 0.0.0
 */
public class Ability {

    private String name;
    private String flavorText;
    private String effect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
