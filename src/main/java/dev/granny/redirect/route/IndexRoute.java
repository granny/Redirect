/*
 * Redirect - A redirect web server for DiscordSRV's wiki
 * Copyright (C) 2020 granny
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

package dev.granny.redirect.route;

import dev.granny.redirect.Redirect;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.WeakHashMap;


public class IndexRoute implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        Map<String, Object> model = new WeakHashMap<>();
        return Redirect.render(model, "templates/index.vm");
    }
}
