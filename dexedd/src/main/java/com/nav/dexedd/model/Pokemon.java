package com.nav.dexedd.model;

import java.util.List;

/**
 * Pokémon model.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Pokemon {

    private Integer id;
    private Integer speciesId;
    private Integer dexNumber;
    private String name;
    private String genus;
    private String flavorText;
    private Type primaryType;
    private Type secondaryType;
    private List<Ability> abilities;
    private Boolean catched;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(Integer speciesId) {
        this.speciesId = speciesId;
    }

    public Integer getDexNumber() {
        return dexNumber;
    }

    public void setDexNumber(Integer dexNumber) {
        this.dexNumber = dexNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
    }

    public Type getPrimaryType() {
        return primaryType;
    }

    public void setPrimaryType(Type primaryType) {
        this.primaryType = primaryType;
    }

    public Type getSecondaryType() {
        return secondaryType;
    }

    public void setSecondaryType(Type secondaryType) {
        this.secondaryType = secondaryType;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public Boolean getCatched() {
        return catched;
    }

    public void setCatched(Boolean catched) {
        this.catched = catched;
    }
}
