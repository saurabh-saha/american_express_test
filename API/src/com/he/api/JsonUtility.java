package com.he.api;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.util.List;

public class JsonUtility {
    public static JsonObject convert(User u) {
        JsonObject o = Json.createObjectBuilder()
                .add("firstName", u.firstName)
                .add("lastName", u.lastName)
                .add("id", u.id)
                .build();
        return o;
    }

    public static JsonArray convertArray(List<User> users) {
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(User u : users) {
            builder.add(convert(u));
        }
        return builder.build();
    }
}
