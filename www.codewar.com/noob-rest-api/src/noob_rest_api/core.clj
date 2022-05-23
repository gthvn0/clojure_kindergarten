(ns noob-rest-api.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp])
  (:gen-class))

(defn simple-body-page
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "Hello world<br>" req)})

(defn request-example
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (->>
           (pp/pprint req)
           (str "Request Object: " req))})

(defroutes app-routes
  (GET "/" [] simple-body-page)
  (GET "/request" [] request-example)
  (route/not-found "Error, page not found!"))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ;; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))

