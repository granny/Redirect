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
import com.github.kevinsawicki.http.HttpRequest;
import io.javalin.http.Context;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static io.javalin.plugin.rendering.template.TemplateUtil.model;

public class Option {

    private final String option;
    private int line;

    public Option(Context ctx) {
        Map<String, String> pathParams = ctx.pathParamMap();

        String branch = pathParams.getOrDefault("branch", "master");
        String config = pathParams.getOrDefault("config", "config");
        String lang = pathParams.getOrDefault("lang", "en") + ".yml";
        this.option = pathParams.get("option");

        String rawGithubUrl = String.format(
                "https://raw.githubusercontent.com/DiscordSRV/DiscordSRV/%s/src/main/resources/%s/%s",
                branch, config, lang
        );

        try {
            HttpRequest request = HttpRequest.get(new URL(rawGithubUrl));
            if (request.notFound()) {
                Log.error("Could not locate page " + rawGithubUrl);
                ctx.render("/static/could-not-locate.vm", model("link", rawGithubUrl, "host", ctx.host()));
            } else {
                if (option != null) {
                    locateOption(request.body().split("\n"));
                }

                String githubUrl = String.format(
                        "https://github.com/DiscordSRV/DiscordSRV/blob/%s/src/main/resources/%s/%s%s",
                        branch, config, lang, line != 0 ? "#L" + line : ""
                );

                Log.info(String.format("Redirecting to %s/%s/%s", branch, config, lang));
                ctx.redirect(githubUrl);
            }
        } catch (MalformedURLException e) {
            Log.error("Could not locate page " + rawGithubUrl);
        }
    }

    private void locateOption(String[] pageLines) {
        int line = 0;
        for (int i = 0; i < pageLines.length; i++) {
            String pageLine = pageLines[i].trim().toLowerCase();
            if (pageLine.startsWith(option.toLowerCase())) {
                line = i;
                break;
            }
        }
        this.line = ++line;
    }
}
