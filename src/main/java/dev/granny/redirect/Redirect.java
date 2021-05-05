/*
 * Redirect - A redirect web server for DiscordSRV's wiki
 * Copyright (C) 2021 granny
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

package dev.granny.redirect;

import com.esotericsoftware.minlog.Log;
import io.javalin.Javalin;
import org.apache.commons.lang3.StringUtils;


import java.util.Arrays;
import java.util.List;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class Redirect {

    List<String> defaultPaths = Arrays.asList(
            "/DiscordSRV/DiscordSRV/blob/:branch/src/main/resources/:config/:lang/",
            "/DiscordSRV/DiscordSRV/:branch/:config/:lang/",
            "/:branch/:config/:lang/",
            "/:branch/:config/",

            "/alerts/",
            "/config/",
            "/linking/",
            "/messages/",
            "/synchronization/",
            "/voice/"
    );

    public static void main(String[] args) {
        new Redirect(args);
    }

    public Redirect(String[] args) {

        // start up the Javalin instance
        Javalin app = Javalin.create(javalinConfig -> javalinConfig.addStaticFiles("/static")).start(
                StringUtils.isNotBlank(System.getenv("IP"))
                        ? System.getenv("REDIRECT_IP")
                        : "0.0.0.0",
                Integer.parseInt(StringUtils.isNotBlank(System.getenv("PORT"))
                        ? System.getenv("REDIRECT_PORT")
                        : "4567")
        );

        // set up a basic root page
        app.get("/", ctx -> ctx.render("/static/index.vm", model("host", ctx.host())));

        // iterate through defaultPaths
        defaultPaths.forEach(path -> {
            Log.info("Registering path: " + path + ":option");
            app.get(path + ":option", Option::new);

            Log.info("Registering path: " + path);
            app.get(path, Option::new);
        });

    }
}