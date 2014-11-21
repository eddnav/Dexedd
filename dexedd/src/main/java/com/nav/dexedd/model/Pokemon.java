package com.nav.dexedd.model;

import java.util.List;

/**
 * Pokémon model.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Pokemon {

    /**
     * Top for gender ratio calculations.
     *
     * @see com.nav.dexedd.model.Pokemon#genderRatio
     */
    private static final Double GENDER_RATIO_TOP = 8.0;

    private Integer id;
    private Integer speciesId;
    private Integer dexNumber;
    private String name;
    private String genus;
    private String flavorText;
    private Type primaryType;
    private Type secondaryType;
    private List<Ability> abilities;
    private Double height;
    private Double weight;

    /**
     * Todo: study the catch rates and implement the formulae
     */
    private Integer catchRate;
    private List<EggGroup> eggGroups;

    /**
     * Gender ratio represents the number of females in {@link com.nav.dexedd.model.Pokemon#GENDER_RATIO_TOP GENDER_RATIO_TOP}.
     */
    private Double genderRatio;
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getCatchRate() {
        return catchRate;
    }

    public void setCatchRate(Integer catchRate) {
        this.catchRate = catchRate;
    }

    public List<EggGroup> getEggGroups() {
        return eggGroups;
    }

    public String getEggGroupsAsString(String separator) {
        int i = 1;
        String eggGroupsString = "";
        for (EggGroup eggGroup : eggGroups) {
            if (i < eggGroups.size()) {
                eggGroupsString = eggGroupsString + eggGroup.getName() + " " + separator + " ";
            } else {
                eggGroupsString = eggGroupsString + eggGroup.getName();
            }
            i++;
        }
        return eggGroupsString;
    }

    public void setEggGroups(List<EggGroup> eggGroups) {
        this.eggGroups = eggGroups;
    }

    public Double getGenderRatio() {
        return genderRatio / GENDER_RATIO_TOP * 100;
    }

    public void setGenderRatio(Double genderRatio) {
        this.genderRatio = genderRatio;
    }

    public Boolean getCatched() {
        return catched;
    }

    public void setCatched(Boolean catched) {
        this.catched = catched;
    }
}
