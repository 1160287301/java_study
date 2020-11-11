package java编程思想.java_IO.压缩;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * 支持 Zip 格式 的 Java 库 更加 全面。 利用 该 库 可以 方便 地保 存 多个 文件， 它 甚至 有一个 独立 的 类， 使得 读取 Zip 文件 更加 方便。
 * 这个 类 库 使 用的 是 标准 Zip 格式， 所以 能 与 当前 那些 可通过 因 特 网 下载 的 压缩 工具 很好 地 协作。
 * 下面 这个 例子 具有 与 前例 相同 的 形式， 但它 能 根据 需要 来处 理 任意 多个 命令行 参数。
 * 另外， 它 显示 了 用 Checksum 类 来 计算 和 校验 文件 的 校验 和 的 方法。
 * 一共 有 两种 Checksum 类型： Adler32（ 它 快 一些） 和 CRC32（ 慢 一些， 但 更 准确）。
 *
 */
public class ZipCompress {
    public static void main(String[] args)
            throws IOException {
        FileOutputStream f = new FileOutputStream("test.zip");
        CheckedOutputStream csum =
                new CheckedOutputStream(f, new Adler32());
        ZipOutputStream zos = new ZipOutputStream(csum);
        BufferedOutputStream out =
                new BufferedOutputStream(zos);
        zos.setComment("A test of Java Zipping");
        // No corresponding getComment(), though.
        for (String arg : args) {
            System.out.print("Writing file " + arg);
            BufferedReader in =
                    new BufferedReader(new FileReader(arg));
            zos.putNextEntry(new ZipEntry(arg));
            int c;
            while ((c = in.read()) != -1)
                out.write(c);
            in.close();
            out.flush();
        }
        out.close();
        // Checksum valid only after the file has been closed!
        System.out.print("Checksum: " + csum.getChecksum().getValue());
        // Now extract the files:
        System.out.print("Reading file");
        FileInputStream fi = new FileInputStream("test.zip");
        CheckedInputStream csumi =
                new CheckedInputStream(fi, new Adler32());
        ZipInputStream in2 = new ZipInputStream(csumi);
        BufferedInputStream bis = new BufferedInputStream(in2);
        ZipEntry ze;
        while ((ze = in2.getNextEntry()) != null) {
            System.out.print("Reading file " + ze);
            int x;
            while ((x = bis.read()) != -1)
                System.out.write(x);
        }
        if (args.length == 1)
            System.out.print("Checksum: " + csumi.getChecksum().getValue());
        bis.close();
        // Alternative way to open and read Zip files:
        ZipFile zf = new ZipFile("test.zip");
        Enumeration e = zf.entries();
        while (e.hasMoreElements()) {
            ZipEntry ze2 = (ZipEntry) e.nextElement();
            System.out.print("File: " + ze2);
            // ... and extract the data as before
        }
        /* if(args.length == 1) */
    }
}
