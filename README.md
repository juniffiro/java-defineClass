# Java defineClass utility

**defineClass()** is a **protected** method of the ClassLoader class that allows you to convert a byte array into an
instance of the class '**Class**'.

I added the ability to use this method in any ClassLoader and extended its functionality.

## How to use

```java
/*
 * Example of creating a new instance
 * of a compiled Test.class
 * with defineClass()
 */
try {
    Class<?> testClass = Define.defineClass(
            getClass().getClassLoader(),
        Paths.get("./classes/Test.class"));
    testClass.newInstance();
} catch (Exception e) {
    throw new RuntimeException(e);
}
```
## Open source

The project is an open source project distributed under the Apache License 2.0 <br>

## Getting started

1. Download the latest build from releases
2. Read the FAQ and examples
3. Enjoy!

## Status

The project is in beta. Use at your own risk. <br>

