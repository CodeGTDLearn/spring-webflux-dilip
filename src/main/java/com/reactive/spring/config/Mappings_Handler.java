package com.reactive.spring.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mappings_Handler {
    public static final String VERSION = "/v1";
    public static final String REQ_MAP = "/items";
    public static final String ID = "id";

    public static final String VERS_FUNCT_ENDPT = VERSION + "/fun" + REQ_MAP;
    public static final String VERS_FUNCT_ENDPT_ID = VERSION + "/fun" + REQ_MAP + "/{" + ID + "}";
}
