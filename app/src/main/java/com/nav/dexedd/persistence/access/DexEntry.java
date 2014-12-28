package com.nav.dexedd.persistence.access;

import android.content.Context;
import android.database.Cursor;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.TreeTraverser;
import com.nav.dexedd.R;
import com.nav.dexedd.model.Ability;
import com.nav.dexedd.model.EggGroup;
import com.nav.dexedd.model.EvolutionCondition;
import com.nav.dexedd.model.Item;
import com.nav.dexedd.model.Location;
import com.nav.dexedd.model.Move;
import com.nav.dexedd.model.Pokemon;
import com.nav.dexedd.model.Region;
import com.nav.dexedd.model.Stat;
import com.nav.dexedd.model.StatSpread;
import com.nav.dexedd.model.Type;
import com.nav.dexedd.persistence.DexDatabase;
import com.nav.dexedd.structure.Tree;
import com.nav.dexedd.util.PokemonTextUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Dex entry data access object
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class DexEntry extends Access {

    public static enum Version {

        XY(24); // Todo add all versions

        private Integer version;

        private Version(Integer version) {
            this.version = version;
        }

        @Override
        public String toString() {
            return version.toString();
        }

    }


    private Integer pokemonId;
    private Version version;

    private DexEntry(Context context, Integer pokemonId, Version version) {
        super(context);
        this.pokemonId = pokemonId;
        this.version = version;
    }

    /**
     * Creates a dex entry.
     *
     * @param context   The application context
     * @param pokemonId The Pokémon id
     * @return A dex entry object
     */
    public static DexEntry create(Context context, Integer pokemonId) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        Version version = Version.XY; // Todo from preferences
        return new DexEntry(context, pokemonId, version);
    }

    /**
     * Creates a dex entry.
     *
     * @param context   The application context
     * @param pokemonId The Pokémon id
     * @param version   The game version
     * @return A dex entry object
     */
    public static DexEntry create(Context context, Integer pokemonId, Version version) {
        database = DexDatabase.getInstance(context).getReadableDatabase();
        return new DexEntry(context, pokemonId, version);
    }

    /**
     * Get the Pokémon information for this dex entry.
     *
     * @return The {@link com.nav.dexedd.model.Pokemon} for the dex entry
     */
    public Pokemon getPokemon() {
        String[] args = {Dex.DexType.NATIONAL_DEX.toString(), version.toString(), pokemonId.toString()};
        String query = getContext().getString(R.string.get_basic_info);
        Cursor cursor = database.rawQuery(query, args);
        cursor.moveToFirst();
        Pokemon pokemon = new Pokemon();
        pokemon.setId(cursor.getInt(0));
        pokemon.setSpeciesId(cursor.getInt(1));
        pokemon.setDexNumber(cursor.getInt(2));
        pokemon.setName(cursor.getString(3));
        pokemon.setGenus(cursor.getString(4));
        pokemon.setFlavorText(PokemonTextUtil.cleanDexText(cursor.getString(5)));
        pokemon.setPrimaryType(new Type(cursor.getInt(6)));
        if (cursor.isNull(7)) {
            pokemon.setSecondaryType(new Type(cursor.getInt(7)));
        }
        pokemon.setAbilities(getAbilities(pokemon.getId()));
        pokemon.setCatchRate(cursor.getInt(8));
        pokemon.setEggGroups(getEggGroups(pokemon.getSpeciesId()));
        pokemon.setGenderRatio(cursor.getDouble(9));
        // Height from the data source is measured in decameters (dam), thus the conversion to meters
        pokemon.setHeight((double) cursor.getInt(10) / 10);
        // Weight from the data source is measured in hectograms (hg), thus the conversion to kilograms
        pokemon.setWeight((double) cursor.getInt(11) / 10);
        pokemon.setBaseStats(getStats(pokemon.getId()));
        pokemon.setEvolutionChain(getEvolutionChain(pokemon.getSpeciesId()));
        pokemon.setCatched(cursor.getInt(12) == 1);
        cursor.close();
        return pokemon;
    }

    /**
     * Get a Pokémon's abilities.
     *
     * @param pokemonId The Pokémon id
     * @return The ability list for the Pokémon
     */
    public List<Ability> getAbilities(Integer pokemonId) {
        String[] args = {pokemonId.toString()};
        String query = getContext().getString(R.string.get_abilities);
        Cursor cursor = database.rawQuery(query, args);
        List<Ability> abilities = new ArrayList<>();
        while (cursor.moveToNext()) {
            Ability ability = new Ability();
            ability.setId(cursor.getInt(0));
            ability.setName(cursor.getString(1));
            ability.setFlavorText(PokemonTextUtil.cleanDexText(cursor.getString(2)));
            ability.setEffect(PokemonTextUtil.cleanDexText(cursor.getString(3)));
            ability.setIsHidden(cursor.getInt(4) == 1);
            ability.setSlot(cursor.getInt(5));
            abilities.add(ability);
        }
        cursor.close();
        return abilities;
    }

    /**
     * Get a Pokémon's egg groups.
     *
     * @param speciesId The Pokémon species id
     * @return The egg group list for the Pokémon species
     */
    public List<EggGroup> getEggGroups(Integer speciesId) {
        String[] args = {pokemonId.toString()};
        String query = getContext().getString(R.string.get_egg_groups);
        Cursor cursor = database.rawQuery(query, args);
        List<EggGroup> eggGroups = new ArrayList<>();
        while (cursor.moveToNext()) {
            EggGroup eggGroup = new EggGroup();
            eggGroup.setId(cursor.getInt(0));
            eggGroup.setName(cursor.getString(1));
            eggGroups.add(eggGroup);
        }
        cursor.close();
        return eggGroups;
    }

    /**
     * Get a Pokémon's stats.
     *
     * @param pokemonId The Pokémon id
     * @return The StatSpread object for the Pokémon
     */
    public StatSpread getStats(Integer pokemonId) {
        String[] args = {pokemonId.toString()};
        String query = getContext().getString(R.string.get_stats);
        Cursor cursor = database.rawQuery(query, args);
        StatSpread statSpread = new StatSpread();
        while (cursor.moveToNext()) {
            Stat stat = new Stat(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
            statSpread.setStat(stat, stat.getId());
        }
        cursor.close();
        return statSpread;
    }

    /**
     * Get a Pokemon's evolution chain.
     *
     * @param speciesId The Pokémon species id
     * @return A Pokémon's evolution chain
     */
    public Tree<Pokemon> getEvolutionChain(Integer speciesId) {
        String[] args = {speciesId.toString()};
        String query = getContext().getString(R.string.get_evolution_chain);
        Cursor cursor = database.rawQuery(query, args);

        Tree<Pokemon> evolutionChain = new Tree<>();

        TreeTraverser<Tree<Pokemon>> traverser = new TreeTraverser<Tree<Pokemon>>() {
            @Override
            public Iterable<Tree<Pokemon>> children(Tree<Pokemon> root) {
                return root.getChildren();
            }
        };

        List<Pokemon> evolutions = new ArrayList<>();

        // Group the evolution conditions by species
        while (cursor.moveToNext()) {

            Pokemon newEvolution = new Pokemon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2));
            newEvolution.setEvolutionConditions(new ArrayList<EvolutionCondition>());
            EvolutionCondition evolutionCondition = prepareEvolutionCondition(cursor);


            boolean added = false;
            for (Pokemon evolution : evolutions) {
                if (evolution.getSpeciesId().equals(newEvolution.getSpeciesId())) {
                    evolution.getEvolutionConditions().add(evolutionCondition);
                    added = true;
                }
            }

            if (!added) {
                newEvolution.getEvolutionConditions().add(evolutionCondition);
                evolutions.add(newEvolution);
            }

        }

        /* WARNING: Getting the root of the evolution tree. For the moment this works as there are no
        Pokémon with multiple evolution conditions that have different base for evolution,
        this may change in the future so heads up */
        for (Pokemon evolution : evolutions) {
            if (evolution.getEvolutionConditions().get(0).getFromPokemon() == null) {
                evolutionChain.setData(evolution);
                evolutionChain.setRank(0);
                evolutionChain.setChildren(new ArrayList<Tree<Pokemon>>());
                evolutions.remove(evolution);
                break;
            }
        }

        while (evolutions.size() > 0) {
            for (Iterator<Pokemon> iterator = evolutions.iterator(); iterator.hasNext(); ) {
                Pokemon evolution = iterator.next();
                FluentIterable<Tree<Pokemon>> evolutionChainNodes = traverser.breadthFirstTraversal(evolutionChain);

                /* WARNING: For the moment this works as there are no Pokémon with multiple evolution
                conditions that have different base for evolution, this may change in the future
                so heads up */
                Pokemon fromPokemon = evolution.getEvolutionConditions().get(0).getFromPokemon();
                for (Tree<Pokemon> evolutionChainNode : evolutionChainNodes) {
                    if (fromPokemon != null) {
                        if (evolutionChainNode.getData().getSpeciesId().equals(fromPokemon.getSpeciesId())) {
                            Tree<Pokemon> newEvolutionChainNode = new Tree<>();
                            newEvolutionChainNode.setData(evolution);
                            newEvolutionChainNode.setRank(evolutionChainNode.getRank() + 1);
                            newEvolutionChainNode.setChildren(new ArrayList<Tree<Pokemon>>());
                            evolutionChainNode.getChildren().add(newEvolutionChainNode);
                            iterator.remove();
                            break;
                        }
                    }
                }
            }
        }
        cursor.close();
        return evolutionChain;
    }

    private EvolutionCondition prepareEvolutionCondition(Cursor cursor) {
        EvolutionCondition evolutionCondition = new EvolutionCondition();
        if (!cursor.isNull(3)) {
            evolutionCondition.setFromPokemon(new Pokemon(cursor.getInt(3), cursor.getInt(4), cursor.getString(5)));
        }
        if (!cursor.isNull(6)) {
            evolutionCondition.setTrigger(EvolutionCondition.Trigger.getTriggerByValue(cursor.getInt(6)));
        }
        if (!cursor.isNull(7)) {
            evolutionCondition.setMinimumLevel(cursor.getInt(7));
        }
        if (!cursor.isNull(8)) {
            evolutionCondition.setTriggerItem(new Item(cursor.getInt(8), cursor.getString(9)));
        }
        if (!cursor.isNull(10)) {
            evolutionCondition.setGender(cursor.getInt(10));
        }
        if (!cursor.isNull(11)) {
            evolutionCondition.setLocation(new Location(cursor.getInt(11), cursor.getString(12), new Region(cursor.getInt(13), cursor.getString(14))));
        }
        if (!cursor.isNull(15)) {
            evolutionCondition.setHeldItem(new Item(cursor.getInt(15), cursor.getString(16)));
        }
        if (!cursor.isNull(17)) {
            evolutionCondition.setAtDaytime(cursor.getString(17).equals(EvolutionCondition.DAYTIME_CONDITION));
        }
        if (!cursor.isNull(18)) {
            evolutionCondition.setKnownMove(new Move(cursor.getInt(18), cursor.getString(19)));
        }
        if (!cursor.isNull(20)) {
            evolutionCondition.setKnownMoveType(new Type(cursor.getInt(20)));
        }
        if (!cursor.isNull(21)) {
            evolutionCondition.setMinimumHappiness(cursor.getInt(21));
        }
        if (!cursor.isNull(22)) {
            evolutionCondition.setMinimumBeauty(cursor.getInt(22));
        }
        if (!cursor.isNull(23)) {
            evolutionCondition.setMinimumAffection(cursor.getInt(23));
        }
        if (!cursor.isNull(24)) {
            evolutionCondition.setRelativePhysicalStats(cursor.getInt(24));
        }
        if (!cursor.isNull(25)) {
            evolutionCondition.setInPartyPokemonSpecies(new Pokemon(cursor.getInt(25), cursor.getInt(26), cursor.getString(27)));
        }
        if (!cursor.isNull(28)) {
            evolutionCondition.setInPartyPokemonType(new Type(cursor.getInt(28)));
        }
        if (!cursor.isNull(29)) {
            evolutionCondition.setTradeFor(new Pokemon(cursor.getInt(29), cursor.getInt(30), cursor.getString(31)));
        }
        if (!cursor.isNull(32)) {
            evolutionCondition.setBabyTriggerItem(new Item(cursor.getInt(32), cursor.getString(33)));
        }
        evolutionCondition.setNeedsOverworldRain(cursor.getInt(34) == 1);
        evolutionCondition.setTurnUpsideDown(cursor.getInt(35) == 1);

        return evolutionCondition;
    }
}
