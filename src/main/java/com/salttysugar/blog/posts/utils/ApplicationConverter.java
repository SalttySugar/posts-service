package com.salttysugar.blog.posts.utils;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ApplicationConverter {
    ConversionService conversionService;

    ApplicationConverter(@Qualifier("webFluxConversionService") ConversionService conversionService) {
        this.conversionService = conversionService;
    }


    public <T> Function<Object, T> convert(Class<T> type) {
        return (source) -> Optional
                .ofNullable(conversionService.convert(source, type))
                .map(type::cast)
                .orElseThrow(RuntimeException::new);
    }

    public <T> Function<List<?>, List<T>> convertToListOf(Class<T> type) {
        return (source) -> source
                .stream()
                .map(convert(type))
                .collect(Collectors.toList());
    }
}
