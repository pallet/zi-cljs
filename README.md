# Zi-cljs

Zi-Cljs is a maven plugin for clojurescript.

It uses the maven pom `sourceDirectory` and `testSourceDirectory` settings to
locate source, which by default means that it uses `src/main/clojurescript` and
`src/test/clojurescript`.

From an implementation perspecitve, most of the goals are written in clojure.

It requires maven 3.0.3.

## Available goals

 * zi-cljs:compile

## Install

Globally installing the plugin allows you to run the goals without modifying
a project's pom file.

To globally enable the zi-cljs plugin, you need to add `pluginGroup` and
`pluginRepository` configuration to your `~/.m2/settings.xml` file.

```xml
    <pluginGroups>
      <pluginGroup>org.cloudhoist.plugin</pluginGroup>
    </pluginGroups>
```

```xml
    <profiles>
      <profile>
        <id>clojure-dev</id>
        <pluginRepositories>
          <pluginRepository>
            <id>sonatype-snapshots</id>
            <url>http://oss.sonatype.org/content/repositories/releases</url>
          </pluginRepository>
        </pluginRepositories>
      </profile>
    </profiles>

    <activeProfiles>
      <activeProfile>clojure-dev</activeProfile>
    </activeProfiles>
```

To enable zi-cljs in a project pom, without globally enabling the plugin, you
will need to add a `pluginRepositories` entry to your pom:

```xml
    <pluginRepositories>
      <pluginRepository>
        <id>sonatype-snapshots</id>
        <url>http://oss.sonatype.org/content/repositories/releases</url>
      </pluginRepository>
    </pluginRepositories>
```

## Configuration

It uses the maven pom `sourceDirectory` and `testSourceDirectory` settings to
locate source, which by default means that it uses `src/main/clojurescript` and
`src/test/clojurescript`.

## Goals

### compile

The compile goal AOT compiles clojurescript source with the closure compiler..

```xml
    <build>
      <plugins>
        <plugin>
          <groupId>org.cloudhoist.plugin</groupId>
          <artifactId>zi-cljs</artifactId>
          <version>0.1.0-SNAPSHOT</version>
          <executions>
            <execution>
              <id>default-compile</id>
              <goals>
                <goal>compile</goal>
              </goals>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </build>
```

## Zi, the builder

Zi was a builder in [northern mythology](http://www.pitt.edu/~dash/mbuilder.html#eckwadt).

## License

Licensed under [EPL](http://www.eclipse.org/legal/epl-v10.html)

Copyright 2011 Hugo Duncan.
