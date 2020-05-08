package me.pkrok.redirect;

import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.Language;
import github.scarsz.configuralize.ParseException;
import me.pkrok.redirect.route.GetConfigRoute;
import me.pkrok.redirect.route.IndexRoute;
import spark.Request;
import spark.Response;

import java.io.File;
import java.io.IOException;

import static spark.Spark.*;

public class Redirect {

    public static void main(String[] args) throws IOException, ParseException {
        final DynamicConfig config;

        config = new DynamicConfig(Language.EN);
        config.addSource(Redirect.class, "config", new File("config.yml"));
        config.saveAllDefaults();
        config.loadAll();


        staticFileLocation("/static/");
        ipAddress(config.getString("Bind.IP"));
        port(config.getInt("Bind.Port"));
        get("/", new IndexRoute());
        get("/*", new GetConfigRoute());
        notFound(Redirect::notFoundRoute);
    }

    public static String notFoundRoute(Request req, Response res) {
        res.type("application/json");
        return "{\"message\":\"Custom 404\"}";
    }
}