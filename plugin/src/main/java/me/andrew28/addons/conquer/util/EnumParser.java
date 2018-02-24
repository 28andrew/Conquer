package me.andrew28.addons.conquer.util;

import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.localization.Noun;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Andrew Tran
 */
public class EnumParser<E extends Enum<E>> extends Parser<E> {
    private String pattern;
    private Map<String, E> enumParseMap = new HashMap<>();
    private Class<E> enumClass;

    public EnumParser(Class<E> enumClass) {
        this.enumClass = enumClass;
        for (E e : enumClass.getEnumConstants()) {
            enumParseMap.put(e.name().toLowerCase().replace('_', ' '), e);
            enumParseMap.put(e.name().toLowerCase().replace("_", ""), e);
        }
    }

    @Override
    public String toString(E o, int flags) {
        String name = o.name().toLowerCase().replace('_', ' ');
        String pluralSuffix = ((flags & Noun.PLURAL) != 0 ? (name.endsWith("s") ? "es" : "s") : "");
        return name + pluralSuffix;
    }

    @Override
    public String toVariableNameString(E o) {
        return toString(o, 0);
    }

    @Override
    public String getVariableNamePattern() {
        if (pattern == null) {
            pattern = "(";
            pattern += Arrays.stream(enumClass.getEnumConstants())
                    .map(this::toVariableNameString)
                    .map(Pattern::quote)
                    .collect(Collectors.joining("|"));
            pattern += ")";
        }
        return pattern;
    }

    @Override
    public E parse(String s, ParseContext context) {
        return enumParseMap.get(s.toLowerCase());
    }
}
