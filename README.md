# Zi-cljs

Zi-Cljs is a maven plugin for clojurescript.

It uses the maven pom `sourceDirectory` and `testSourceDirectory` settings to
locate source, which by default means that it uses `src/main/clojurescript` and
`src/test/clojurescript`.

It requires maven 3.0.3.

This is alpha quality at present.

## Philosophy

Why build something that Rich Hickey said he hopes he would never see? The
ClojureScript compile runs on the JVM, so jars are the natural packaging for use
with it.  While support for clojurescript in
[leiningen](https://github.com/technomancy/leiningen) would be very nice, it
isn't there yet, and it wasn't clear (to me at least) how to add it to the
current version.

`zi-cljs` is intended to be used in module containing just the clojurescript
code, so that the classpath is spearate from the classpath of the server side
of your project.  This also allows a disintiction in the Clojure version
used for compiling ClojureScript, and the Clojure version used on the server.

## Available goals

 * zi-cljs:compile
 * zi-cljs:browser

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
          <version>0.1.0</version>
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

## compile

<table>
  <tr>
    <th>Property</th>
    <th>Default</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>outputFileName</td>
    <td>${project.build.finalName}</td>
    <td>The target file name</td>
  </tr>
  <tr>
    <td>outputDirectory</td>
    <td>${project.build.outputDirectory}</td>
    <td>The output directory</td>
  </tr>
  <tr>
    <td>debug</td>
    <td>true</td>
    <td>Sets the :optimisation to nil if true (overrides optimize)</td>
  </tr>
  <tr>
    <td>optimize</td>
    <td>false</td>
    <td>Sets the :optimisation to :advanced if true</td>
  </tr>
  <tr>
    <td>verbose</td>
    <td>false</td>
    <td>Sets :pretty-print to true</td>
  </tr>
  <tr>
    <td>targetVersion</td>
    <td></td>
    <td>Sets :targer to the keywordised value</td>
  </tr>
</table>

## browser

Start a repl for running on a browser.  The goal writes a `browser.html` file in
the target directory, and this should be loaded in the browser to connect to the
repl server started by this goal.

<table>
  <tr>
    <th>Property</th>
    <th>Default</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>port</td>
    <td>browser.port</td>
    <td>The port that the REPL server process should run on</td>
  </tr>
  <tr>
    <td>mainNamespace</td>
    <td>${project.name}</td>
    <td>The namespace to be required by the REPL</td>
  </tr>
  <tr>
    <td>outputDirectory</td>
    <td>${project.build.outputDirectory}</td>
    <td>The output directory</td>
  </tr>
</table>

## Zi, the builder

Zi was a builder in [northern mythology](http://www.pitt.edu/~dash/mbuilder.html#eckwadt).

## License

Licensed under [EPL](http://www.eclipse.org/legal/epl-v10.html)

Copyright 2011 Hugo Duncan.
