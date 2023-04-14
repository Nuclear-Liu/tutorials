package org.hui.tdd.lombok.features.cleanup;

import lombok.Cleanup;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Hui.Liu
 * @since 2021-12-22 12:39
 */
public class TestCleanup {
    @Test
    public void testCleanup() throws IOException {
        String inputPath = new String("./TestCleanup.java");
        String outputPath = new String("./TestCleanup.java2");
        @Cleanup InputStream in = new FileInputStream(inputPath);
        @Cleanup OutputStream out = new FileOutputStream(outputPath);
        byte[] b = new byte[4096];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }
}
