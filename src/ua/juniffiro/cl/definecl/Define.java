package ua.juniffiro.cl.definecl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;

/**
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 * ( Created ) ( by ) ( @juniffiro )
 * 27/02/2023
 * +-+-+-+-+-+ +-+-+ +-+-+-+-+-+-+-+-+-+
 */
public class Define {

    /*
    This utility is designed and uses part of
    the class loader implementation.

    Java 8 ClassLoader src
    https://github.com/zxiaofan/JDK/blob/master/JDK1.8/src/java/lang/ClassLoader.java
     */

    /**
     * Converts a ByteBuffer into an instance
     * of class 'Class', with an optional
     * ProtectionDomain.
     *
     * @param classLoader
     *        Target class loader
     * @param name
     *        The expected binary name of the
     *        class, or null if not known
     * @param buf
     *        ByteBuffer
     * @param domain
     *        The ProtectionDomain of the class, or null.

     * @return Instance of class 'Class'.
     *
     * @throws InvocationTargetException
     *         If an error occurs when calling
     *         a method via Reflection API
     * @throws NoSuchMethodException
     *         If when calling a method via
     *         Reflection API the method was not found
     * @throws IllegalAccessException
     *         If the Reflection API does not have
     *         access to the defineClass() method in ClassLoader
     */
    public static Class<?> defineClass(ClassLoader classLoader,
                                       String name,
                                       ByteBuffer buf,
                                       ProtectionDomain domain)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {

        byte[] data = new byte[buf.remaining()];
        buf.get(data);

        return defineClass(classLoader, name, data, 0, data.length, domain);
    }

    /**
     * Converts the byte array obtained from
     * the compiled class file into an instance
     * of the class 'Class', with an optional
     * ProtectionDomain.
     * <p>
     * See {@link #defineClass(ClassLoader, String, byte[], int, int, ProtectionDomain)}
     *
     * @param pathToClass
     *        Path to compiled class file
     *
     * See {@link DefineUtils#getBytesOfClass(Path)}
     */
    public static Class<?> defineClass(ClassLoader classLoader,
                                       Path pathToClass,
                                       ProtectionDomain domain)
            throws NoSuchMethodException, IOException,
            InvocationTargetException, IllegalAccessException {

        byte[] data = DefineUtils.getBytesOfClass(pathToClass);

        return defineClass(classLoader, null, data, 0, data.length, domain);
    }

    /**
     * Converts the byte array obtained from
     * the compiled class file into an instance
     * of the class 'Class', with the ProtectionDomain
     * obtained from the CodeSource file.
     * <p>
     * See {@link #defineClass(ClassLoader, Path, ProtectionDomain)}
     */
    public static Class<?> defineClass(ClassLoader classLoader, Path pathToClass)
            throws NoSuchMethodException, IOException, InvocationTargetException,
            IllegalAccessException {

        return defineClass(classLoader, pathToClass, new ProtectionDomain(
                DefineUtils.getCodeSource(pathToClass), null));
    }

    /**
     * A method similar to
     * {@link #defineClass(ClassLoader, Path, ProtectionDomain)}
     * but takes the path to the file as a
     * String instead of the Path parameter.
     *
     * @param pathToClass
     *        String type
     *        Path to compiled class file
     */
    public static Class<?> defineClass(ClassLoader classLoader,
                                       String pathToClass,
                                       ProtectionDomain domain)
            throws IOException, InvocationTargetException, NoSuchMethodException,
            IllegalAccessException {

        return defineClass(classLoader, Paths.get(pathToClass), domain);
    }

    /**
     * A method similar to
     * {@link #defineClass(ClassLoader, Path)}
     * but takes the path to the class as a
     * String instead of the Path parameter.
     *
     * @param pathToClass
     *        String type
     *        Path to compiled class file
     */
    public static Class<?> defineClass(ClassLoader classLoader, String pathToClass)
            throws NoSuchMethodException, IOException,
            InvocationTargetException, IllegalAccessException {

        return defineClass(classLoader, Paths.get(pathToClass));
    }

    /**
     * Converts an array of bytes into an instance
     * of the class 'Class' without ProtectionDomain
     * (StaticData.defaultProtectionDomain)
     * <p>
     * See {@link #defineClass(ClassLoader, String, byte[], int, int)}
     */
    public static Class<?> defineClass(ClassLoader classLoader,
                                       String name,
                                       byte[] data,
                                       int offset,
                                       int len)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        return defineClass(classLoader, name, data, offset, len, null);
    }

    /**
     * Converts an array of bytes into an instance
     * of the class 'Class'.
     * Deprecated method and not recommended for use.
     * <p>
     * See {@link #defineClass(ClassLoader, String, byte[], int, int)}
     */
    @Deprecated
    public static Class<?> defineClass(ClassLoader classLoader,
                                       byte[] data,
                                       int offset,
                                       int len)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        return defineClass(classLoader, null, data, offset, len);
    }

    /**
     * Converts an array of bytes into an instance
     * of the class 'Class' with an optional
     * ProtectionDomain, using the Reflection API.
     *
     * @param classLoader
     *        Target class loader
     * @param name
     *        The expected binary name of the
     *        class, or null if not known
     * @param data
     *        The bytes that make up the class data
     * @param offset
     *        The start offset in b of the class data
     * @param len
     *        The length of the class data
     * @param domain
     *        The ProtectionDomain of the class, or null.
     *
     * @return Instance of class 'Class'.
     *
     * @throws InvocationTargetException
     *         If an error occurs when calling
     *         a method via Reflection API
     * @throws NoSuchMethodException
     *         If when calling a method via
     *         Reflection API the method was not found
     * @throws IllegalAccessException
     *         If the Reflection API does not have
     *         access to the defineClass() method in ClassLoader
     */
    public static Class<?> defineClass(ClassLoader classLoader,
                                       String name,
                                       byte[] data,
                                       int offset,
                                       int len,
                                       ProtectionDomain domain)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = ClassLoader.class.getDeclaredMethod(
                "defineClass",
                String.class,
                byte[].class,
                int.class,
                int.class,
                ProtectionDomain.class);
        method.setAccessible(true);

        Class<?> clazz = (Class<?>) method.invoke(classLoader, new Object[]{
                name, data, offset, len, domain
        });
        method.setAccessible(false);

        return clazz;
    }
}
