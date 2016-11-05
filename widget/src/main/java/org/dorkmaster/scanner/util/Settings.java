package org.dorkmaster.scanner.util;

import org.apache.commons.lang3.StringUtils;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Settings {
    private ResourceBundle bundle;

    private Settings() {
        bundle = ResourceBundle.getBundle("settings");
    }

    private static final Settings instance = new Settings();

    public static Settings instance() {
        return instance;
    }

    public Value value(String key) {
        String v = System.getenv(key);
        if (StringUtils.isBlank(v)) {
            v = System.getProperty(key);
            if (StringUtils.isBlank(v)) {
                try {
                    v = bundle.getString(key);
                }
                catch(MissingResourceException e) {
                    v = null;
                }
            }
        }
        return new Value(v);
    }

    public static class Value {
        private String value;

        public Value(String value) {
            this.value = value;
        }

        public String asString() {
            return value;
        }

        public String[] asStrings(String separator) {
            return value.split(separator);
        }

        public Integer asInt() {
            return Integer.valueOf(value);
        }

        public Integer[] asInts(String separator) {
            String[] tmp = asStrings(separator);
            Integer[] result = new Integer[tmp.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = Integer.valueOf(tmp[i]);
            }
            return result;
        }

        public Boolean asBool() {
            return Boolean.valueOf(value);
        }

        public String asString(String def) {
            return isNull() ? def : value;
        }

        public String[] asStrings(String separator, String[] def) {
            return isNull() ? def : asStrings(separator);
        }

        public Integer asInt(Integer def) {
            return isNull() ? def : Integer.valueOf(value);
        }
        public Integer[] asInts(String separator, Integer[] def) {
            return isNull() ? def : asInts(separator);
        }

        public Boolean asBool(Boolean def) {
            return isNull() ? def : Boolean.valueOf(value);
        }

        public boolean isNull() {
            return null == value;
        }
    }
}
