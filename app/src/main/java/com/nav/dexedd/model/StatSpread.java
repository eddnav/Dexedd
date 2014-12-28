package com.nav.dexedd.model;

/**
 * Class representing the stats of a Pokémon.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class StatSpread {

    private Stat healthPoints;
    private Stat attack;
    private Stat defense;
    private Stat specialAttack;
    private Stat specialDefense;
    private Stat speed;

    public Stat getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Stat hp) {
        this.healthPoints = hp;
    }

    public Stat getAttack() {
        return attack;
    }

    public void setAttack(Stat attack) {
        this.attack = attack;
    }

    public Stat getDefense() {
        return defense;
    }

    public void setDefense(Stat defense) {
        this.defense = defense;
    }

    public Stat getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(Stat specialAttack) {
        this.specialAttack = specialAttack;
    }

    public Stat getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(Stat specialDefense) {
        this.specialDefense = specialDefense;
    }

    public Stat getSpeed() {
        return speed;
    }

    public void setSpeed(Stat speed) {
        this.speed = speed;
    }

    /**
     * Sets a specific stat by its id:<br><br>
     * <p/>
     * 1: HP<br>
     * 2: Attack<br>
     * 3: Defense<br>
     * 4: Special Attack<br>
     * 5: Special Defense<br>
     * 6: Speed<br>
     *
     * @param stat   The stat to set
     * @param statId The stat id
     */
    public void setStat(Stat stat, Integer statId) {
        switch (statId) {
            case 1:
                setHealthPoints(stat);
                break;
            case 2:
                setAttack(stat);
                break;
            case 3:
                setDefense(stat);
                break;
            case 4:
                setSpecialAttack(stat);
                break;
            case 5:
                setSpecialDefense(stat);
                break;
            case 6:
                setSpeed(stat);
                break;
        }
    }
}
