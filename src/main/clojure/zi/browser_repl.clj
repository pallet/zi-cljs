(ns zi.browser-repl
  "repl mojo for zi plugin"
  (:require
   [zi.core :as core]
   [zi.mojo :as mojo]
   [clojure.java.io :as io]
   [clojure.string :as string])
  (:import
   java.io.File
   [clojure.maven.annotations
    Goal RequiresDependencyResolution Parameter Component]
    org.apache.maven.plugin.ContextEnabled
    org.apache.maven.plugin.Mojo
    org.apache.maven.plugin.MojoExecutionException))

(defn write-html
  "Write an html file for the browser."
  [dir f main-ns]
  (let [html (slurp (io/resource "browser.html"))]
    (.. (File. dir) mkdirs)
    (spit (io/file dir "browser.html") (format html "" f main-ns))))

(mojo/defmojo BrowserMojo
  {Goal "browser"
   RequiresDependencyResolution "test"}
  [^{Parameter
     {:expression "${browser.port}" :defaultValue "9000"}}
   ^Integer
   port
   ^{Parameter
     {:expression "${project.build.finalName}"
      :alias "outputFileName"}}
   ^String
   output-file-name
   ^{Parameter
     {:expression "${project.name}"
      :alias "mainNamespace"}}
   ^String
   main-namespace]

  (let [source-paths (->
                      (core/clojure-source-paths
                       source-directory "clojurescript")
                      (core/clojure-source-paths
                       source-directory "clojure")
                      (into (core/clojure-source-paths test-source-directory)))
        classpath-elements (->
                            source-paths
                            (conj output-directory)
                            (into test-classpath-elements))
        cl (core/classloader-for
            (core/classpath-with-source-jars classpath-elements))]
    (write-html output-directory output-file-name main-namespace)
    (.debug log (format "source paths: %s" (vec source-paths)))
    (.debug log (format "classpath elements: %s" (vec classpath-elements)))
    (classlojure/eval-in
     cl
     `(do
        (require '[clojure.main :as main])
        (require '[cljs.repl :as repl])
        (require '[cljs.repl.browser :as browser])))
    (.debug log "cljs.repl loaded")
    (classlojure/eval-in
     cl
     `(fn [in# out# err#]
        (binding [*in* in# *out* out# *err* err#]
          (clojure.main/with-bindings
            (def ~'env ((ns-resolve '~'cljs.repl.browser '~'repl-env)))
            ((ns-resolve '~'cljs.repl '~'repl) ~'env))))
     *in* *out* *err*)))
