package me.andrew28.addons.conquer.skript.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.ConquerFaction;
import me.andrew28.addons.conquer.api.FactionsPlugin;
import me.andrew28.addons.core.ASAType;
import me.andrew28.addons.core.annotations.Description;
import me.andrew28.addons.core.annotations.Name;
import me.andrew28.addons.core.annotations.Syntax;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

/**
 * @author Andrew Tran
 */
@Name("Faction/Kingdom/Guild")
@Description("Represents a faction/kingdom/guild")
@Syntax("Retrieved via expressions")
public class TypeFaction extends ASAType<ConquerFaction>{
    @Override
    public ClassInfo<ConquerFaction> getClassInfo() {
        return new ClassInfo<>(ConquerFaction.class, "conquerfaction")
                .name("faction")
                .user("(conquer)?(faction|kingdom|guild)")
                .parser(new Parser<ConquerFaction>() {
                    @Override
                    public String toString(ConquerFaction faction, int i) {
                        return faction.getName();
                    }

                    @Override
                    public String toVariableNameString(ConquerFaction faction) {
                        return faction.getName();
                    }

                    @Override
                    public String getVariableNamePattern() {
                        return ".+";
                    }

                    @Override
                    public boolean canParse(ParseContext context) {
                        return false;
                    }
                })
                .serializer(new Serializer<ConquerFaction>() {
                    @Override
                    public Fields serialize(ConquerFaction faction) throws NotSerializableException {
                        Fields fields = faction.serialize();
                        fields.putObject("impl", Conquer.getInstance().getFactionsPlugin().getClass().getCanonicalName());
                        return fields;
                    }

                    @Override
                    public void deserialize(ConquerFaction faction, Fields fields) throws StreamCorruptedException, NotSerializableException {
                        assert false;
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }

                    @Override
                    protected ConquerFaction deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        FactionsPlugin impl = null;
                        try {
                            Class<? extends FactionsPlugin> implClass = (Class<? extends FactionsPlugin>) Class.forName((String) fields.getObject("impl"));
                            if (Conquer.getInstance().getFactionsPlugin().getClass().equals(implClass)){
                                impl = Conquer.getInstance().getFactionsPlugin();
                            }else{
                                impl = implClass.newInstance();
                            }
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                            e.printStackTrace();
                        }
                        if (impl == null){
                            throw new StreamCorruptedException();
                        }
                        if (!impl.canBeUsed()){
                            Conquer.getInstance().getLogger().warning(
                                    String.format("Trying to load a saved factions variable from a different factions plugin (%s) but it could not be loaded because " +
                                                    "that plugin is no longer on the server. Variable will be null."
                            , impl.getClass().getCanonicalName()));
                            return null;
                        }
                        if (!impl.hasBeenInitialized()){
                            impl.initialize();
                        }
                        return impl.deserializeFaction(fields);
                    }
                }).defaultExpression(new EventValueExpression<>(ConquerFaction.class));

    }
}
