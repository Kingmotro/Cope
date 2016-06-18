# Cope
Cope is a simple and easy config format, parser and API for Java.

**You can view the junit code coverage [here](https://jackwhite20.github.io/Cope/coverage).**

# Features

- readable
- comments
- easy to use
- lightweight
- fast

# Installation

- Install [Maven 3](http://maven.apache.org/download.cgi)
- Clone/Download this repo
- Install it with: ```mvn clean install```

**Maven dependency**

_Cope:_
```xml
<dependency>
    <groupId>de.jackwhite20</groupId>
    <artifactId>cope</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

# Config format

_Config file config.cop:_

```yaml
# Comment support
global:
    max-con 200

timeout:
	connect 5000
    client 50000
    server 50000

server:
    bind 0.0.0.0 80
```

# Cope API usage

**If you want to use the javadoc you can browse it [here](https://jackwhite20.github.io/Cope/doc/).**

```java
try {
	// Create a config from a config file
	CopeConfig cope = Cope.from("config.cop");

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

### License

Licensed under the GNU General Public License, Version 3.0.
