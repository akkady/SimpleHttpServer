package me.akkad.http;

import me.akkad.exception.HttpVersionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum HttpVersion {
    HTTP_1_0("HTTP/1.0", 1, 0),
    HTTP_1_1("HTTP/1.1", 1, 1),
    HTTP_2("HTTP/2", 2, 0);
    private final String LITERAL;
    private final int MAJOR;

    private final int MINOR;

    HttpVersion(String LITERAL, int MAJOR, int MINOR) {
        this.LITERAL = LITERAL;
        this.MAJOR = MAJOR;
        this.MINOR = MINOR;
    }

    private static final Pattern httpVersionRegex = Pattern.compile("^HTTP/(?<major>\\d)(.(?<minor>\\d))?");

    public static HttpVersion getBestCompatibleVersion(String literalVersion) throws HttpVersionException {
        Matcher matcher = httpVersionRegex.matcher(literalVersion);
        if (!matcher.find()) throw new HttpVersionException();

        int major = parseWithDefault0(matcher.group("major"));
        int minor = parseWithDefault0(matcher.group("minor"));

        if (!isSupported(major)) throw new HttpVersionException();

        HttpVersion bestCompatible = null;
        for (HttpVersion version : HttpVersion.values()) {
            if (version.LITERAL.equals(literalVersion)) {
                return version;
            } else {
                if (version.MAJOR == major && version.MINOR <= minor) {
                    bestCompatible = version;
                }
            }
        }
        if (bestCompatible != null) return bestCompatible;
        throw new HttpVersionException("No compatible version was found");
    }

    private static int parseWithDefault0(String nbrStr) {
        if (nbrStr == null) return 0;
        return Integer.parseInt(nbrStr);
    }

    private static boolean isSupported(int version) {
        return version < 3 && version > 0;
    }
}
