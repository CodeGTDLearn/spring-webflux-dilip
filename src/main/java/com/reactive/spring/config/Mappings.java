package com.reactive.spring.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mappings{
    public static final String VERSION = "/v1";
    public static final String GET_ENDPOINT = "/items";
    public static final String ID_PATH = "/{id}";
}
