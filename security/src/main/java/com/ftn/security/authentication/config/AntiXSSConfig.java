package com.ftn.security.authentication.config;

import com.fasterxml.jackson.core.JsonpCharacterEscapes;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AntiXSSConfig {

    @Autowired
    public void configeJackson(ObjectMapper mapper) {
        mapper.getFactory().setCharacterEscapes(new HTMLCharacterEscapes());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static class HTMLCharacterEscapes extends JsonpCharacterEscapes {

        @Override
        public int[] getEscapeCodesForAscii() {
            int[] asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
            // and force escaping of a few others:
            asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
            //asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
            //asciiEscapes['"'] = CharacterEscapes.ESCAPE_CUSTOM;
            asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
            return asciiEscapes;
        }

        @Override
        public SerializableString getEscapeSequence(int ch) {
            switch (ch) {
                case '&':
                    return new SerializedString("&#38;");
                case '<':
                    return new SerializedString("&#60;");
                case '>':
                    return new SerializedString("&#62;");
                case '\"':
                    return new SerializedString("&#34;");
                case '\'':
                    return new SerializedString("&#39;");
                default:
                    return super.getEscapeSequence(ch);
            }
        }
    }
}
