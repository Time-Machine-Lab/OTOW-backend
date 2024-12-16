package com.tml.otowbackend.core.ws;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONException;
import org.json.JSONObject;

@Data
@AllArgsConstructor
public class WsMessage {
    private String type;

    private String key;

    private String message;

    private Integer num;
}
