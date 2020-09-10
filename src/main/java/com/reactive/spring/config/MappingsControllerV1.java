package com.reactive.spring.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MappingsControllerV1 {
    public static final String VERSION = "/v1";
    public static final String REQ_MAP = "/items";
    public static final String EXCEPT = "/runtimeException";
    public static final String ID_PATH = "/{id}";
}
