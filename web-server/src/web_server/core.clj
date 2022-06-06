(ns web-server.core
  (:require [org.httpkit.server :as s]
            [compojure.core :refer [GET routes]]
            [compojure.route :as r]))

(defonce server (atom nil))

(defn app
  []
  (routes
    (GET "/" [req]
         {:status 200
          :headers {"Content-Type" "text/html"}
          :body "<h1>Welcome</h1>"})
    (r/not-found "<h1>Not found</h1>")))

(defn start-server
  "Run server on port given as argument"
  [port]
  (reset! server (s/run-server  (app) {:port port})))

(defn stop-server
  "Stop the server"
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))
