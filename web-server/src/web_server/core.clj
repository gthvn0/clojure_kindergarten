(ns web-server.core
  (:require [org.httpkit.server :as s]))

(defonce server (atom nil))

(defn main-handler
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "<h1>Welcome</h1>"})

(defn start-server
  "Run server on port given as argument"
  [port]
  (reset! server (s/run-server main-handler {:port port})))

(defn stop-server
  "Stop the server"
  []
  (when-not (nil? @server)
    (@server :timeout 100)
    (reset! server nil)))
