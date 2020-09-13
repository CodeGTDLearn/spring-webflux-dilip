package com.reactive.spring.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MappingsStream {
    public static final String VERSION = "/v1";
    public static final String REQ_MAP = "/stream";
    public static final String FUNCTIONAL = "/fun";
    public static final String STREAM_ENDPOINT = VERSION + REQ_MAP;
    public static final String STREAM_ENDPOINT_FUNC = VERSION + FUNCTIONAL + REQ_MAP;
}
