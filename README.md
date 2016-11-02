# Cope
Cope is a simple and easy config format, parser and API for Java.

Cope was designed to be very simple and easy to understand. Look at the examples below and you will see that it is really cool and neat.

**You can view the junit code coverage [here](https://jackwhite20.github.io/Cope/coverage).**

# Features

- readable
- comments
- defaults
- easy to use
- lightweight
- fast

# Config format

_Config file config.cop:_

```yaml
# Comment support
global:
    connections 200

timeout:
	connect 5000
    client 50000
    server 50000

server:
    bind 0.0.0.0 80
```

# Examples

**If you want to use the javadoc you can browse it [here](https://jackwhite20.github.io/Cope/doc/).**

_General example:_

```java
try {
	// Create a config from a config file
	CopeConfig cope = Cope.from("config.cop").build();

	// Check if the header and the key is available
	if (cope.hasHeaderAndKey("global", "max-con")) {
		Key maxCon = cope.getHeader("global").getKey("max-con");
		if (maxCon.hasValues()) {
			System.out.println("Max connections: " + maxCon.getValue(0).asInt());
		}
	}

	// Check if the header exists
	if (cope.hasHeader("timeout")) {
		Header timeoutHeader = cope.getHeader("timeout");

		Key connectKey = timeoutHeader.getKey("connect");
		if (connectKey.hasValues()) {
			System.out.println("Timeout connect: " + connectKey.getValue(0));
		}

		Key clientKey = timeoutHeader.getKey("client");
		if (clientKey.hasValues()) {
			System.out.println("Timeout client: " + clientKey.getValue(0));
		}

		Key serverKey = timeoutHeader.getKey("server");
		if (serverKey.hasValues()) {
			System.out.println("Timeout server: " + serverKey.getValue(0));
		}
	}

	// Check if the header and the key is available
	if (cope.hasHeaderAndKey("server", "bind")) {
		Key bindKey = cope.getHeader("server").getKey("bind");
		// Check if the key has values
		if (bindKey.hasValues()) {
			String host = bindKey.getValue(0).asString();
			int port = bindKey.getValue(1).asInt();

			System.out.println("Host: " + host);
			System.out.println("Port: " + port);
		}
	}
} catch (CopeException e) {
	e.printStackTrace();
}
```

_Defaults:_

Defaults are working all the same. Of course you can set multiple key defaults in one header
if you call the "def" method multiple times with the same header but a different key.

```yaml
global:
    connections 200

# You want to have a default for the missing "server" header
```

```java
try {
    // Create the cope and add the default header
	CopeConfig cope = Cope.from("config.cop")
			.def(new Header("server"), new Key("bind"), new Value("0.0.0.0"), new Value("8080"))
			//.def(new Header("server"), new Key("another-key"), new Value("value"))
			.build();

	// No need to check if the header or key exists
	// because we have the default values
	Key bindKey = cope.getHeader("server").getKey("bind");
	String host = bindKey.getValue(0).asString();
	int port = bindKey.getValue(1).asInt();

	System.out.println("Host: " + host);
	System.out.println("Port: " + port);
} catch (CopeException e) {
	e.printStackTrace();
}
```

# Installation

_Maven:_

- Install [Maven 3](http://maven.apache.org/download.cgi)
- Clone/Download this repo
- Install it with: ```mvn clean install```

```xml
<dependency>
    <groupId>de.jackwhite20</groupId>
    <artifactId>cope</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

_Jar library:_

Simply download the jar from a [release](https://github.com/JackWhite20/Cope/releases) and include it in your project.

### License

Licensed under the GNU General Public License, Version 3.0.
