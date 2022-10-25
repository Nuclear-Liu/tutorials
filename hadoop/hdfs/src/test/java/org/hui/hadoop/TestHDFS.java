package org.hui.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class TestHDFS {

    private Configuration conf = null;
    private FileSystem fs = null;

    @BeforeAll
    public void conn() throws IOException, InterruptedException {
        conf = new Configuration(true);
        // fs = FileSystem.get(conf); // use env: HADOOP_HOME
        fs = FileSystem.get(URI.create("hdfs://mycluster/"), conf, "god");
    }

    @Test
    public void mkdir() throws IOException {
        Path dir = new Path("hui");

        if (fs.exists(dir)) {
            fs.delete(dir, true);
        }

        fs.create(dir);
    }

    @Test
    public void upload() throws IOException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(new File("./out.txt")));
        Path outfile = new Path("/hui/out.txt");
        FSDataOutputStream output = fs.create(outfile);

        IOUtils.copyBytes(input, output, conf, true);
    }

    @Test
    public void download() {
        // download file
    }

    /**
     * for i in `seq 100000`;do  echo "hello hadoop $i"  >>  data.txt  ;done
     * hdfs dfs -D dfs.blocksize=1048576  -put  data.txt
     */
    @Test
    public void blocks() throws IOException {
        Path filePath = new Path("/usr/god/data.txt");
        FileStatus fss = fs.getFileStatus(filePath);
        BlockLocation[] blks = fs.getFileBlockLocations(fss, 0, fss.getLen());

        for (BlockLocation blk : blks) {
            System.out.println(blk);
        }

    }
    @AfterAll
    public void close() throws IOException {
        fs.close();
    }
}
