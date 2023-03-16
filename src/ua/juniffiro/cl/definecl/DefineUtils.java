package ua.juniffiro.cl.definecl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSigner;
import java.security.CodeSource;

/**
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 * ( Created ) ( by ) ( @juniffiro )
 * 10/03/2023
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 */
public class DefineUtils {

    /**
     * Get CodeSource from the class file.
     *
     * @param pathToClass
     *        Path to compiled class file
     *
     * @return Code source of class.
     * @throws MalformedURLException
     *         Thrown to indicate that a malformed
     *         URL has occurred.
     */
    public static CodeSource getCodeSource(Path pathToClass) throws MalformedURLException {
        return new CodeSource(pathToClass
                .toFile()
                .toURI()
                .toURL(),
                new CodeSigner[0]);
    }

    /**
     * Get an array of bytes from the class file.
     *
     * @param pathToClass
     *        Path to class file
     * @return Byte array of class file.
     *
     * @throws IOException
     *         If the class file is not found or
     *         there are other problems with the file
     */
    public static byte[] getBytesOfClass(Path pathToClass) throws IOException {
        return Files.readAllBytes(pathToClass);
    }
}
