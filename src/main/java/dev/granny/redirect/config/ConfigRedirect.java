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

package dev.granny.redirect.config;

import lombok.Getter;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ConfigRedirect {
    private String org;
    private String project;
    private String branch;
    private String file;
    private String lang;
    private String option;
    private int line;
    @Getter private String error = "";
    private static String rawGitHubUrl = "https://raw.githubusercontent.com/%s/%s/%s/src/main/resources/%s/%s.yml";
    private static String gitHubUrl = "https://github.com/%s/%s/blob/%s/src/main/resources/%s/%s.yml%s";

    public ConfigRedirect(String org, String project, String branch, String file, String lang, String option) {
        setValues(org, project, branch, file, lang, option);
    }

    public ConfigRedirect() {
        setValues(null, null, null, null, null, null);
    }

    public ConfigRedirect(@NotNull String pathInfo) {
        String[] pathArr = pathInfo.replaceAll("^/", "").split("/");
        switch (pathArr.length) {
            case 6:
                setValues(pathArr[0], pathArr[1], pathArr[2], pathArr[3], pathArr[4], pathArr[5]);
                break;
            case 4:
                setValues("DiscordSRV", "DiscordSRV", pathArr[0], pathArr[1], pathArr[2], pathArr[3]);
                break;
            case 3:
                setValues("DiscordSRV", "DiscordSRV", pathArr[0], pathArr[1], "en", pathArr[2]);
                break;
            case 2:
                setValues("DiscordSRV", "DiscordSRV", "master", pathArr[0], "en", pathArr[1]);
                break;
            default:
                setValues(null, null, null, null, null, null);
        }

    }

    private void setValues(String org, String project, String branch, String file, String lang, String option) {
        this.org = org;
        this.project = project;
        this.branch = branch;
        this.file = file;
        this.lang = lang;
        this.option = option;

        String page = requestHttp(getRawUrl());
        String[] pageLines = page.split("\n");
        if (pageLines.length == 1) {
            System.out.println("Could not locate page/option.");
            error = pageLines[0];
        }
        else {
            locateOption(pageLines);
        }
    }

    public String getRawUrl() {
        return String.format(rawGitHubUrl, org, project, branch, file, lang);
    }

    public String getUrl() {
        return String.format(gitHubUrl, org, project, branch, file, lang, line != 0 ? "#L" + line : "");
    }

    private String requestHttp(String requestUrl) {
        try {
            return IOUtils.toString(new URL(requestUrl), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "Failed to fetch URL: " + e.getMessage();
        }
    }

    private void locateOption(@NotNull String[] split) {
        int line = 0;
        for (int i = 0; i < split.length; i++) {
            if (split[i].trim().toLowerCase().startsWith(option.toLowerCase())) {
                line = i;
                break;
            }
        }
        this.line = ++line;
    }

    public String toString() {
        return getUrl();
    }

}
