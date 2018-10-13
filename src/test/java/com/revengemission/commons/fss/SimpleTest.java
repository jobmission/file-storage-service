package com.revengemission.commons.fss;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

public class SimpleTest {


    @Test
    @Ignore
    public void writeFile() throws Exception {
        File dest = new File("/tmp/spark/c.txt");
        Files.write(dest.toPath(), Arrays.asList("this is just a test case!"),
                Charset.defaultCharset(), StandardOpenOption.APPEND);
    }
}
