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

    public Option(Context ctx) throws MalformedURLException {
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
