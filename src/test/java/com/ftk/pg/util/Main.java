package com.ftk.pg.util;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

class Signature {
    @SerializedName("protected")
    private String protectedValue;
    private String signature;

    public Signature(String protectedValue, String signature) {
        this.protectedValue = protectedValue;
        this.signature = signature;
    }
}

class Request {
    private String payload;
    private Signature signature;

    public Request(String payload, Signature signature) {
        this.payload = payload;
        this.signature = signature;
    }
}

public class Main {
    public static void main(String[] args) {
        Signature sig = new Signature("WFkZXJz...", "MEUCIQCB...");
        Request req = new Request("BEwCcB60ci7Pj6OB", sig);

        Gson gson = new Gson();
        String json = gson.toJson(req);
        System.out.println(json);
    }
}