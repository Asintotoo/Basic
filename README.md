## Basic - A Basic Spigot Library to simplify Minecraft Plugins Development

How to include the API with Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.Asintotoo</groupId>
        <artifactId>Basic</artifactId>
        <version>LATERS</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```
**Note**: Replace "LATEST" with the latest release version avaiable [here](https://github.com/Asintotoo/Basic/releases)

**Note**: Basic requires to be shaded in order to be used in your Minecraft Plugin, please include the following in your *pom.xml*
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.5.1</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <minimizeJar>true</minimizeJar>
                <createDependencyReducedPom>false</createDependencyReducedPom>
                <artifactSet>
                    <includes>
                        <include>com.github.Asintotoo:ColorLib*</include>
                        <include>com.github.Asintotoo:Basic*</include>
                    </includes>
                </artifactSet>
                <relocations>
                    <relocation>
                        <pattern>com.asintoto.colorlib</pattern>
                        <shadedPattern>something.unique.lib.colorlib
                        </shadedPattern>
                    </relocation>
                    <relocation>
                        <pattern>com.asintoto.basic</pattern>
                        <shadedPattern>something.unique.lib.basic
                        </shadedPattern>
                    </relocation>
                </relocations>
            </configuration>
        </plugin>
    </plugins>
</build>
```

# Wiki Coming Soon...
