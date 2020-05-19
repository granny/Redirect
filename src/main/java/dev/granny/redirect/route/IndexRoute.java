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

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexRoute implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return "<h2><a href=\"https://github.com/granny/Redirect\">Config Redirect</a></h2>" +
                "<p>Terribly built, but get's the job done</p>" +
                "<h3>Paths that work (based on DiscordSRV's directory):</h3>" +
                "<p>https://config.granny.dev/ORG/PROJECT/BRANCH/FILE/LANG/OPTION</p>" +
                "<pre>(Full Path)</pre>" +
                "<p>https://config.granny.dev/BRANCH/FILE/LANG/OPTION</p>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\")</pre>" +
                "<p>https://config.granny.dev/BRANCH/FILE/OPTION</p>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\")</pre>" +
                "<p>https://config.granny.dev/FILE/OPTION</p>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\", BRANCH=\"master\")</pre>" +
                "<h3>Examples:</h3>" +
                "<a href=\"https://config.granny.dev/DiscordSRV/DiscordSRV/master/config/en/ConfigVersion\">" +
                "https://config.granny.dev/DiscordSRV/DiscordSRV/master/config/en/ConfigVersion</a><br>" +
                "<pre>(Full Path)</pre>" +
                "<a href=\"https://config.granny.dev/master/config/en/ConfigVersion\">" +
                "https://config.granny.dev/master/config/en/ConfigVersion</a><br>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\")</pre>" +
                "<a href=\"https://config.granny.dev/master/config/ConfigVersion\">" +
                "https://config.granny.dev/master/config/ConfigVersion</a><br>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\")</pre>" +
                "<a href=\"https://config.granny.dev/config/ConfigVersion\">" +
                "https://config.granny.dev/config/ConfigVersion</a>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\", BRANCH=\"master\")</pre>";
    }
}
