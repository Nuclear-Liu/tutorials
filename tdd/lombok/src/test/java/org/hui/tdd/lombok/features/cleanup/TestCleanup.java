package org.hui.tdd.lombok.features.cleanup;

import lombok.Cleanup;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
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
        @Cleanup InputStream in = this.getClass().getResourceAsStream("/TestCleanup.java");
        String inputPath = new String("./TestCleanup.java");
        String outputPath = new String("./target/TestCleanup.java2");
        // @Cleanup InputStream in = new FileInputStream(inputPath);
        @Cleanup OutputStream out = new FileOutputStream(outputPath);
        byte[] b = new byte[4096];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }
    @Test
    @Ignore("当有 try-catch 块是不可以一起使用 编译报错")
    public void testCleanupTryCatch() {
        /*@Cleanup*/ InputStream in = null;
        String outputPath = new String("./target/TestCleanup.java2");
        /*@Cleanup*/ OutputStream out = null;
        try {
            in = this.getClass().getResourceAsStream("/TestCleanup.java");
            out = new FileOutputStream(outputPath);
            byte[] b = new byte[4096];
            while (true) {
                int r = in.read(b);
                if (r == -1) break;
                out.write(b, 0, r);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testCleanupFunction() throws IOException {
        String outputPath = "./target/TestCleanup.java2";
        @Cleanup
        InputStream in = CleanupFeatures.getInputStream();
        @Cleanup
        FileOutputStream out = new FileOutputStream(outputPath);
        byte[] b = new byte[4096];
        while (true) {
            int r = in.read(b);
            if (r == -1) break;
            out.write(b, 0, r);
        }
    }
    @Test
    public void testCleanupTryWithResource() {
        try (InputStream in = this.getClass().getResourceAsStream("/TestCleanup.java");
             OutputStream out = new FileOutputStream("./target/TestCleanup.java2")) {
            byte[] b = new byte[4096];
            while (true) {
                int r = in.read(b);
                if (r == -1) break;
                out.write(b, 0, r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
