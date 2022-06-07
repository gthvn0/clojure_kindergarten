(ns ya-web.core
  (:require [ya-web.server :as s])
  (:gen-class))

(defn -main
  "Start the server on the given port"
  [port]
  (s/start-server (Integer. port)))
