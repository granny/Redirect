/*
 * Redirect - A redirect web server for DiscordSRV's wiki
 * Copyright (C) 2020 pkrok01 "granny"
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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