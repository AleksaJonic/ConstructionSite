package com.lexsoft.project.constructions.utils;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public abstract class FileUtils {

    public String getFileAsString(String fileName) {
        try {
            InputStream input = this.getClass().getResourceAsStream(fileName);
            return IOUtils.toString(input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
