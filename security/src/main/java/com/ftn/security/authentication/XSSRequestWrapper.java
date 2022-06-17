package com.ftn.security.authentication;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.net.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class XSSRequestWrapper extends HttpServletRequestWrapper {
    public XSSRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            encodedValues[i] = replaceXSSCharacters((values[i]));
        }

        return encodedValues;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        //Non json type, return directly
        if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }

        //Null, return directly
        String json = CharStreams.toString(new InputStreamReader(
                super.getInputStream(), Charsets.UTF_8));

        if (json.isEmpty()) {
            return super.getInputStream();
        }
        //It should be noted here that parameters in json format cannot directly use the escape util. Of hutool Escape, because it will escape "also,
        //This makes @ RequestBody unable to resolve into a normal object, so we implement a filtering method ourselves
        //Or you can customize your own objectMapper to handle the escape of json access parameters (recommended)
        json = replaceXSSCharacters(json).trim();
        final ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }

            @Override
            public int read() {
                return bis.read();
            }
        };
    }

    private String replaceXSSCharacters(String value) {
        if (value == null) {
            return null;
        }
        return value
                //.replace("&", "&#38;")
                .replace("<", "&#60;")
                .replace(">", "&#62;")
                //.replace("\"", "&#34;")
                .replace("'", "&#39;");
    }

    @Override
    public String getParameter(String parameter) {
        return replaceXSSCharacters(super.getParameter(parameter));
    }

    @Override
    public String getHeader(String name) {
        return replaceXSSCharacters(super.getHeader(name));
    }
}
