package gzip;
import java.util.zip.*;
import java.io.*;

public class MyGZIPOutputStream extends GZIPOutputStream
{
    /**
     * Creates a new output stream with the specified buffer size.
     * @param out the output stream
     * @param size the output buffer size
     * @exception IOException If an I/O error has occurred.
     * @exception IllegalArgumentException if size is <= 0
     */
    public MyGZIPOutputStream(OutputStream out, int size) throws IOException {
        super(out, size);
    }

    /**
     * Creates a new output stream with a default buffer size.
     * @param out the output stream
     * @exception IOException If an I/O error has occurred.
     */
    public MyGZIPOutputStream(OutputStream out) throws IOException {
        this(out, 512);
    }

    /**
     * Sets the compression level for subsequent entries which are DEFLATED.
     * The default setting is DEFAULT_COMPRESSION.
     * @param level the compression level (0-9)
     * @exception IllegalArgumentException if the compression level is invalid
     */
    public void setLevel(int level) {
        def.setLevel(level);
    }
}