package com.salttysugar.blog.posts.common.utils;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class FunctionalUtils {
    public static <T> UnaryOperator<T> peek(Consumer<T> c) {
        return x -> {
            c.accept(x);
            return x;
        };
    }

    public static void Throw(Throwable e) throws Throwable {
        throw e;
    }

    public static Optional<Boolean> isTrue(Boolean value) {
        return Optional.of(value)
                .filter(Boolean::booleanValue);
    }

}

