package me.pkrok.redirect.route;

import me.pkrok.redirect.config.ConfigRedirect;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class GetConfigRoute implements Route {
    Map<String, ConfigRedirect> CACHE = new HashMap<>();
    ConfigRedirect current;

    @Override
    public Object handle(Request request, Response response) throws Exception {
        if (!CACHE.containsKey(request.pathInfo())) {
            CACHE.put(request.pathInfo(), new ConfigRedirect(request.pathInfo()));
        }
        current = CACHE.get(request.pathInfo());
        if (current.getError().isEmpty()) {
            response.redirect(current.toString());
            CACHE.remove(request.pathInfo());
            return "";
        }
        else {
            return current.getError();
        }
    }
}
