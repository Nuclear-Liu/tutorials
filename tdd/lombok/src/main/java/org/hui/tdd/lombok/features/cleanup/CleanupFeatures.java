package org.hui.tdd.lombok.features.cleanup;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Hui.Liu
 * @since 2021-12-22 13:21
 */
public class CleanupFeatures {
    public static InputStream getInputStream() throws IOException {
        return new FileInputStream("/drivedbg.txt");
    }
}
