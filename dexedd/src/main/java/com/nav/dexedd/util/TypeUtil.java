package com.nav.dexedd.util;

import com.nav.dexedd.R;

/**
 * Type utility methods.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class TypeUtil {

    public static enum Type {

        NONE(0), NORMAL(1), FIGHTING(2), FLYING(3), POISON(4), GROUND(5), ROCK(6), BUG(7), GHOST(8), STEEL(9), FIRE(10),
        WATER(11), GRASS(12), ELECTRIC(13), PSYCHIC(14), ICE(15), DRAGON(16), DARK(17), FAIRY(18), UNKNOWN(10001);

        private Integer type;

        Type(Integer type) {
            this.type = type;
        }

        public static Type getTypeByValue(Integer typeValue) {
            for (Type type : Type.values()) {
                if (type.type == typeValue) {
                    return type;
                }
            }
            return NONE;
        }

        @Override
        public String toString() {
            return type.toString();
        }

    }

    public static int getTypeNameRes(Type type) {
        switch (type) {
            case NONE:
                return R.string.none;
            case NORMAL:
                return R.string.normal;
            case FIGHTING:
                return R.string.fighting;
            case FLYING:
                return R.string.flying;
            case POISON:
                return R.string.poison;
            case GROUND:
                return R.string.ground;
            case ROCK:
                return R.string.rock;
            case BUG:
                return R.string.bug;
            case GHOST:
                return R.string.ghost;
            case STEEL:
                return R.string.steel;
            case FIRE:
                return R.string.fire;
            case WATER:
                return R.string.water;
            case GRASS:
                return R.string.grass;
            case ELECTRIC:
                return R.string.electric;
            case PSYCHIC:
                return R.string.psychic;
            case ICE:
                return R.string.ice;
            case DRAGON:
                return R.string.dragon;
            case DARK:
                return R.string.dark;
            case FAIRY:
                return R.string.fairy;
            default:
                return R.string.none;
        }
    }

    public static int getTypeColorRes(Type type) {
        switch (type) {
            case NONE:
                return R.color.none;
            case NORMAL:
                return R.color.normal;
            case FIGHTING:
                return R.color.fighting;
            case FLYING:
                return R.color.flying;
            case POISON:
                return R.color.poison;
            case GROUND:
                return R.color.ground;
            case ROCK:
                return R.color.rock;
            case BUG:
                return R.color.bug;
            case GHOST:
                return R.color.ghost;
            case STEEL:
                return R.color.steel;
            case FIRE:
                return R.color.fire;
            case WATER:
                return R.color.water;
            case GRASS:
                return R.color.grass;
            case ELECTRIC:
                return R.color.electric;
            case PSYCHIC:
                return R.color.psychic;
            case ICE:
                return R.color.ice;
            case DRAGON:
                return R.color.dragon;
            case DARK:
                return R.color.dark;
            case FAIRY:
                return R.color.fairy;
            default:
                return R.color.none;
        }
    }




    public static int getTypeBackgroundRes(Type type) {
        switch (type) {
            case NONE:
                return R.drawable.type_background_none;
            case NORMAL:
                return R.drawable.type_background_normal;
            case FIGHTING:
                return R.drawable.type_background_fighting;
            case FLYING:
                return R.drawable.type_background_flying;
            case POISON:
                return R.drawable.type_background_poison;
            case GROUND:
                return R.drawable.type_background_ground;
            case ROCK:
                return R.drawable.type_background_rock;
            case BUG:
                return R.drawable.type_background_bug;
            case GHOST:
                return R.drawable.type_background_ghost;
            case STEEL:
                return R.drawable.type_background_steel;
            case FIRE:
                return R.drawable.type_background_fire;
            case WATER:
                return R.drawable.type_background_water;
            case GRASS:
                return R.drawable.type_background_grass;
            case ELECTRIC:
                return R.drawable.type_background_electric;
            case PSYCHIC:
                return R.drawable.type_background_psychic;
            case ICE:
                return R.drawable.type_background_ice;
            case DRAGON:
                return R.drawable.type_background_dragon;
            case DARK:
                return R.drawable.type_background_dark;
            case FAIRY:
                return R.drawable.type_background_fairy;
            default:
                return R.drawable.type_background_none;
        }
    }
}