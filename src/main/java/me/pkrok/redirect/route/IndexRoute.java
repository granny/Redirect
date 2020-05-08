package me.pkrok.redirect.route;

import spark.Request;
import spark.Response;
import spark.Route;

public class IndexRoute implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return "<h2><a href=\"https://github.com/pkrok01/Redirect\">Config Redirect</a></h2>" +
                "<p>Terribly built, but get's the job done</p>" +
                "<h3>Paths that work (based on DiscordSRV's directory):</h3>" +
                "<p>http://config.pkrok.me/ORG/PROJECT/BRANCH/FILE/LANG/OPTION</p>" +
                "<pre>(Full Path)</pre>" +
                "<p>http://config.pkrok.me/BRANCH/FILE/LANG/OPTION</p>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\")</pre>" +
                "<p>http://config.pkrok.me/BRANCH/FILE/OPTION</p>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\")</pre>" +
                "<p>http://config.pkrok.me/FILE/OPTION</p>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\", BRANCH=\"master\")</pre>" +
                "<h3>Examples:</h3>" +
                "<a href=\"http://config.pkrok.me/DiscordSRV/DiscordSRV/master/config/en/ConfigVersion\">" +
                "http://config.pkrok.me/DiscordSRV/DiscordSRV/master/config/en/ConfigVersion</a><br>" +
                "<pre>(Full Path)</pre>" +
                "<a href=\"http://config.pkrok.me/master/config/en/ConfigVersion\">" +
                "http://config.pkrok.me/master/config/en/ConfigVersion</a><br>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\")</pre>" +
                "<a href=\"http://config.pkrok.me/master/config/ConfigVersion\">" +
                "http://config.pkrok.me/master/config/ConfigVersion</a><br>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\")</pre>" +
                "<a href=\"http://config.pkrok.me/config/ConfigVersion\">" +
                "http://config.pkrok.me/config/ConfigVersion</a>" +
                "<pre>(Assuming ORG=\"DiscordSRV\", PROJECT=\"DiscordSRV\", LANG=\"en\", BRANCH=\"master\")</pre>";
    }
}
