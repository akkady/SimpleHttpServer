package me.akkad.http;

import me.akkad.exception.HttpVersionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpVersionTest {

    @Test
    void getBestCompatibleVersionShouldExacteMach() throws HttpVersionException {
        // GIVEN
        String literalVersion = "HTTP/1.1";
        // ACT
        HttpVersion expected = HttpVersion.HTTP_1_1;
        HttpVersion result = HttpVersion.getBestCompatibleVersion(literalVersion);
        // VERIFY
        assertEquals(expected, result);
    }

    @Test
    void getBestCompatibleVersionWithoutMinorShouldMach() throws HttpVersionException {
        // GIVEN
        String literalVersion = "HTTP/1";
        // ACT
        HttpVersion expected = HttpVersion.HTTP_1_0;
        HttpVersion result = HttpVersion.getBestCompatibleVersion(literalVersion);
        // VERIFY
        assertEquals(expected, result);
    }

    @Test
    void getBestCompatibleVersionShouldThrowWithNotSupportVersion() throws HttpVersionException {
        // GIVEN
        String literalVersion = "HTTP/3.0";
        // ACT
        try {
            HttpVersion.getBestCompatibleVersion(literalVersion);
            fail();
        } catch (HttpVersionException e) {
            // VERIFY
            assertEquals("Http version not supported", e.getMessage());
        }

    }

    @Test
    void getBestCompatibleVersionShouldMatchWithHigherMinor() throws HttpVersionException {
        // GIVEN
        String literalVersion = "HTTP/1.3";
        // ACT
        try {
            HttpVersion result = HttpVersion.getBestCompatibleVersion(literalVersion);
            // VERIFY
            assertEquals(HttpVersion.HTTP_1_1, result);
        } catch (HttpVersionException e) {
            fail();

        }

    }
}