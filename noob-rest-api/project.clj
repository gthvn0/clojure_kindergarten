(defproject noob-rest-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [
                 [org.clojure/clojure "1.10.3"]
                 [compojure "1.6.3"] ;; routing lib
                 [http-kit "2.5.3"]  ;; HTTP client server (event driven)
                 [ring/ring-defaults "0.3.3"] ;; default configurations of Ring middleware
                 [org.clojure/data.json "2.4.0"] ;; JSON parser/generator
                 ]
  :main ^:skip-aot noob-rest-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
