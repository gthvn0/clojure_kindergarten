(ns ya-web.core
  (:require [ya-web.server :as s]
            [config.core :refer [env]])
  (:gen-class))

(defn -main
  "Start the server on port found in config.edn"
  []
  (let [port (:port env)]
    (println "Starting server on port " (str port))
    (s/start-server port)))
