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
