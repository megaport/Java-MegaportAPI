# Java-MegaportAPI

This library is a wrapper around the Megaport RESTful API that is documented at dev.megaport.com.

How do I get the library?
=========================

You can add this library as a Maven dependency:

```xml
<dependency>
    <groupId>com.megaport.api</groupId>
    <artifactId>client</artifactId>
    <version>1.4.4</version>
</dependency>
```

How do I use the library?
=========================

The entry point for this library is ```MegaportApiSession```.  You create one of these, passing in some credentials or a valid token.

```java
    MegaportApiSession session = new MegaportApiSession(Environment.TRAINING, "api.test", "s0me-s3cret#");
```

Once you have a session, you can call the methods to interact with the Megaport RESTful API.

This is how to get a list of locations where Megaports can be ordered:

```java
    List<PortLocationDto> ports = session.findPortLocations();
```

Here is how to get a list of your services:

```java
    List<MegaportServiceDto> ports = session.findPorts();
```

Please look at the test cases for examples of how to exercise the API provided in this library.

Full details of this API are provided at dev.megaport.com.
