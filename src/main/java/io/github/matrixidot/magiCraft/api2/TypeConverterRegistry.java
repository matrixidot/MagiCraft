package io.github.matrixidot.magiCraft.api2;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TypeConverterRegistry {
    private record TypePair(Class<?> source, Class<?> target) {}

    private final Map<TypePair, Function<Object, Object>> converters = new HashMap<>();

    public <S, T> void register(Class<S> source, Class<T> target, Function<S, T> converter) {
        converters.put(new TypePair(source, target), obj -> converter.apply(source.cast(obj)));
    }

    public Object convert(Object input, Class<?> target) {
        if (input == null) return null;
        Class<?> source = input.getClass();
        if (target.isAssignableFrom(source)) return input;
        Function<Object, Object> converter = converters.get(new TypePair(source, target));
        if (converter != null) return converter.apply(input);
        throw new IllegalArgumentException("No converter registered from " + source.getName() + " to " + target.getName());
    }
}
