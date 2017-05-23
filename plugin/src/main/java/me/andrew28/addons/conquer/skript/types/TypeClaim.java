package me.andrew28.addons.conquer.skript.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ParseContext;
import ch.njol.yggdrasil.Fields;
import me.andrew28.addons.conquer.Conquer;
import me.andrew28.addons.conquer.api.Claim;
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
@Name("Claim")
@Description("Represents a claim (usually a chunk)")
@Syntax("Retrieved via expressions")
public class TypeClaim extends ASAType<Claim>{
    private FactionsPlugin factionsPlugin = Conquer.getInstance().getFactionsPlugin();
    @Override
    public ClassInfo<Claim> getClassInfo() {
        return new ClassInfo<>(Claim.class, "conquerclaim")
                .name("claim")
                .user("(conquer)?claim")
                .parser(new Parser<Claim>() {
                    @Override
                    public String toString(Claim claim, int flags) {
                        return claim.representationObjectToString(claim.getRepresentationObject(), flags);
                    }

                    @Override
                    public String toVariableNameString(Claim claim) {
                        return toString(claim, 0);
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
                .serializer(new Serializer<Claim>() {
                    @Override
                    public Fields serialize(Claim claim) throws NotSerializableException {
                        Fields fields = claim.serialize();
                        fields.putObject("impl", factionsPlugin.getClass().getCanonicalName());
                        return fields;
                    }

                    @Override
                    public void deserialize(Claim claim, Fields fields) throws StreamCorruptedException, NotSerializableException {
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
                    protected Claim deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        FactionsPlugin impl = null;
                        try {
                            Class<? extends FactionsPlugin> implClass = (Class<? extends FactionsPlugin>) Class.forName((String) fields.getObject("impl"));
                            if (factionsPlugin.getClass().equals(implClass)){
                                impl = factionsPlugin;
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
                                    String.format("Trying to load a saved claims variable from a different factions plugin (%s) but it could not be loaded because " +
                                                    "that plugin is no longer on the server. Variable will be null."
                                            , impl.getClass().getCanonicalName()));
                            return null;
                        }
                        if (!impl.hasBeenInitialized()){
                            impl.initialize();
                        }
                        return impl.deserializeClaim(fields);
                    }
                }).defaultExpression(new EventValueExpression<>(Claim.class));
    }
}
