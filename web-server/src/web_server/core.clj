(ns web-server.core
  (:require [org.httpkit.server :as s]))

(defn main-handler
  [req]
  {:status 200
   :body "Welcome to my new server"})

(defn new-server
  []
  (s/run-server main-handler {:port 8080}))
