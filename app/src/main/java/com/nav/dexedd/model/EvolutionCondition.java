package com.nav.dexedd.model;

import android.content.Context;

import com.nav.dexedd.R;
import com.nav.dexedd.util.TypeUtil;

/**
 * Evolution condition model.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class EvolutionCondition {

    public static final String DAYTIME_CONDITION = "day";

    public static enum Trigger {
        LEVEL_UP(1), TRADE(2), USE_ITEM(3), SHED(4);

        private final int value;

        private Trigger(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Trigger getTriggerByValue(int value) {
            for (Trigger trigger : Trigger.values()) {
                if (trigger.getValue() == value) {
                    return trigger;
                }
            }
            return LEVEL_UP;
        }
    }

    private Pokemon fromPokemon;
    private Trigger trigger;
    private Integer minimumLevel;
    private Item triggerItem;
    private Integer gender;
    private Location location;
    private Item heldItem;
    private Boolean atDaytime;
    private Move knownMove;
    private Type knownMoveType;
    private Integer minimumHappiness;
    private Integer minimumBeauty;
    private Integer minimumAffection;
    private Integer relativePhysicalStats;
    private Pokemon inPartyPokemonSpecies;
    private Type inPartyPokemonType;
    private Pokemon tradeFor;
    private Item babyTriggerItem;
    private Boolean needsOverworldRain;
    private Boolean turnUpsideDown;

    public Pokemon getFromPokemon() {
        return fromPokemon;
    }

    public void setFromPokemon(Pokemon fromPokemon) {
        this.fromPokemon = fromPokemon;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public Integer getMinimumLevel() {
        return minimumLevel;
    }

    public void setMinimumLevel(Integer minimumLevel) {
        this.minimumLevel = minimumLevel;
    }

    public Item getTriggerItem() {
        return triggerItem;
    }

    public void setTriggerItem(Item triggerItem) {
        this.triggerItem = triggerItem;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Item getHeldItem() {
        return heldItem;
    }

    public void setHeldItem(Item heldItem) {
        this.heldItem = heldItem;
    }

    public Boolean getAtDaytime() {
        return atDaytime;
    }

    public void setAtDaytime(Boolean atDaytime) {
        this.atDaytime = atDaytime;
    }

    public Move getKnownMove() {
        return knownMove;
    }

    public void setKnownMove(Move knownMove) {
        this.knownMove = knownMove;
    }

    public Type getKnownMoveType() {
        return knownMoveType;
    }

    public void setKnownMoveType(Type knownMoveType) {
        this.knownMoveType = knownMoveType;
    }

    public Integer getMinimumHappiness() {
        return minimumHappiness;
    }

    public void setMinimumHappiness(Integer minimumHappiness) {
        this.minimumHappiness = minimumHappiness;
    }

    public Integer getMinimumBeauty() {
        return minimumBeauty;
    }

    public void setMinimumBeauty(Integer minimumBeauty) {
        this.minimumBeauty = minimumBeauty;
    }

    public Integer getMinimumAffection() {
        return minimumAffection;
    }

    public void setMinimumAffection(Integer minimumAffection) {
        this.minimumAffection = minimumAffection;
    }

    public Integer getRelativePhysicalStats() {
        return relativePhysicalStats;
    }

    public void setRelativePhysicalStats(Integer relativePhysicalStats) {
        this.relativePhysicalStats = relativePhysicalStats;
    }

    public Pokemon getInPartyPokemonSpecies() {
        return inPartyPokemonSpecies;
    }

    public void setInPartyPokemonSpecies(Pokemon inPartyPokemonSpecies) {
        this.inPartyPokemonSpecies = inPartyPokemonSpecies;
    }

    public Type getInPartyPokemonType() {
        return inPartyPokemonType;
    }

    public void setInPartyPokemonType(Type inPartyPokemonType) {
        this.inPartyPokemonType = inPartyPokemonType;
    }

    public Pokemon getTradeFor() {
        return tradeFor;
    }

    public void setTradeFor(Pokemon tradeFor) {
        this.tradeFor = tradeFor;
    }

    public Boolean getNeedsOverworldRain() {
        return needsOverworldRain;
    }

    public Item getBabyTriggerItem() {
        return babyTriggerItem;
    }

    public void setBabyTriggerItem(Item babyTriggerItem) {
        this.babyTriggerItem = babyTriggerItem;
    }

    public void setNeedsOverworldRain(Boolean needsOverworldRain) {
        this.needsOverworldRain = needsOverworldRain;
    }

    public Boolean getTurnUpsideDown() {
        return turnUpsideDown;
    }

    public void setTurnUpsideDown(Boolean turnUpsideDown) {
        this.turnUpsideDown = turnUpsideDown;
    }

    public String toString(Context context, Pokemon pokemon) {
        if (fromPokemon == null) {
            if (babyTriggerItem == null) {
                return context.getString(R.string.base_stage);
            } else {
                return String.format(context.getString(R.string.baby_stage), babyTriggerItem.getName());
            }
        } else {
            String condition;

            String levelUp = context.getString(R.string.trigger_level_up);
            String trade = context.getString(R.string.trigger_trade);
            String useItem = context.getString(R.string.trigger_use_item);
            String shed = context.getString(R.string.trigger_shed);

            switch (trigger) {
                case LEVEL_UP:
                    condition = levelUp;
                    break;
                case TRADE:
                    condition = trade;
                    break;
                case USE_ITEM:
                    condition = useItem;
                    break;
                case SHED:
                    condition = String.format(shed, fromPokemon.getName(), pokemon.getName());
                    break;
                default:
                    condition = levelUp;
            }

            String requirementMinimumLevel = context.getString(R.string.requirement_minimum_level);
            if (minimumLevel != null) {
                condition = condition + " " + String.format(requirementMinimumLevel, minimumLevel);
            }

            String requirementTriggerItem = context.getString(R.string.requirement_trigger_item);
            if (triggerItem != null) {
                condition = condition + " " + String.format(requirementTriggerItem, triggerItem.getName());
            }

            String requirementLocation = context.getString(R.string.requirement_location);
            if (location != null) {
                condition = condition + " " + String.format(requirementLocation, location.getName(), location.getRegion().getName());
            }

            String requirementHeldItem = context.getString(R.string.requirement_held_item);
            if (heldItem != null) {
                condition = condition + " " + String.format(requirementHeldItem, heldItem.getName());
            }

            if (atDaytime != null) {
                if (atDaytime) {
                    condition = condition + " " + context.getString(R.string.requirement_at_daytime);
                } else {
                    condition = condition + " " + context.getString(R.string.requirement_at_nighttime);
                }
            }

            String requirementKnownMove = context.getString(R.string.requirement_known_move);
            if (knownMove != null) {
                condition = condition + " " + String.format(requirementKnownMove, knownMove.getName());
            }

            String requirementKnownMoveType = context.getString(R.string.requirement_known_move_type);
            if (knownMove != null) {
                condition = condition + " " + String.format(requirementKnownMoveType,
                        TypeUtil.getTypeNameRes(TypeUtil.TypeValue.getTypeValueByName(knownMoveType.getId().toString())));
            }

            String requirementMinimumHappiness = context.getString(R.string.requirement_minimum_happiness);
            if (minimumHappiness != null) {
                condition = condition + " " + String.format(requirementMinimumHappiness, minimumHappiness);
            }

            String requirementMinimumBeauty = context.getString(R.string.requirement_minimum_beauty);
            if (minimumBeauty != null) {
                condition = condition + " " + String.format(requirementMinimumBeauty, minimumBeauty);
            }

            String requirementMinimumAffection = context.getString(R.string.requirement_minimum_affection);
            if (minimumAffection != null) {
                condition = condition + " " + String.format(requirementMinimumAffection, minimumAffection);
            }

            String requirementAttackEqualsDefense = context.getString(R.string.requirement_attack_equals_defense);
            String requirementAttackGreaterThanDefense = context.getString(R.string.requirement_attack_greater_than_defense);
            String requirementAttackLessThanDefense = context.getString(R.string.requirement_attack_less_than_defense);

            if (relativePhysicalStats != null) {
                switch (relativePhysicalStats) {
                    case 0:
                        condition = condition + " " + requirementAttackEqualsDefense;
                        break;
                    case 1:
                        condition = condition + " " + requirementAttackGreaterThanDefense;
                        break;
                    case 2:
                        condition = condition + " " + requirementAttackLessThanDefense;
                        break;
                    default:
                        condition = condition + " " + requirementAttackEqualsDefense;
                }
            }

            String requirementInPartyPokemonSpecies = context.getString(R.string.requirement_in_party_pokemon_species);
            if (inPartyPokemonSpecies != null) {
                condition = condition + " " + String.format(requirementInPartyPokemonSpecies, inPartyPokemonSpecies.getName());
            }

            String requirementInPartyPokemonType = context.getString(R.string.requirement_in_party_pokemon_type);
            if (inPartyPokemonType != null) {
                condition = condition + " " + String.format(requirementInPartyPokemonType,
                        TypeUtil.getTypeNameRes(TypeUtil.TypeValue.getTypeValueByName(inPartyPokemonType.getId().toString())));
            }

            String requirementTradeFor = context.getString(R.string.requirement_trade_for);
            if (tradeFor != null) {
                condition = condition + " " + String.format(requirementTradeFor, tradeFor.getName());
            }

            if (needsOverworldRain != null && needsOverworldRain) {
                condition = condition + " " + context.getString(R.string.requirement_rain);
            }

            if (turnUpsideDown != null && turnUpsideDown) {
                condition = condition + " " + context.getString(R.string.requirement_turn_upside_down);
            }

            if (gender != null) {
                if (gender == 1) {
                    condition = condition + " " + context.getString(R.string.requirement_gender_female);
                } else {
                    condition = condition + " " + context.getString(R.string.requirement_gender_male);
                }
            }

            return condition;
        }
    }
}
