package org.hui.java.nio;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileTest {
    @Test public void test() throws IOException {
        RandomAccessFile rw = new RandomAccessFile("", "rw");
        FileChannel channel = rw.getChannel();
        MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0,4096L);

    }
}
