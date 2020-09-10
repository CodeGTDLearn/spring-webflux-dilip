package com.client.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MappingsClient {

    //CLIENT
    public static final String ROOT = "/client";
    public static final String SINGLE_ITEM = "/item";
    public static final String RETRIEVE = "/retrieve";
    public static final String EXCHANGE = "/exchange";
    public static final String EXCEPTION = "/runtimeException";
    public static final String ID_PATH = "/{id}";

    //API
    public static final String VERSION = "/v1";
    public static final String REQ_MAP = "/items";
    public static final String ID = "id";

    public static final String API_VERS_FUNC_ENDPT = VERSION + "/fun" + REQ_MAP;
    public static final String API_VERS_FUNC_ENDPT_EXCEPTION = VERSION + "/fun" + REQ_MAP + EXCEPTION;
    public static final String API_VERS_FUNC_ENDPT_ID = VERSION + "/fun" + REQ_MAP + "/{" + ID + "}";






}
