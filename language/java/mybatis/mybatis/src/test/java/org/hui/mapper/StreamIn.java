package org.hui.mapper;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StreamIn implements Closeable {
    @Override
    public void close() throws IOException {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("close: " + this.getClass().getSimpleName());
    }

    public static void main(String[] args) throws InterruptedException {
        try(StreamIn in = new StreamIn()) {
            System.out.println("...");
            throw new IOException("connect failed");
        } catch (IOException e) {
            System.out.println("is failed.");
            TimeUnit.MILLISECONDS.sleep(100);
            throw new RuntimeException(e);
        }
    }
}
