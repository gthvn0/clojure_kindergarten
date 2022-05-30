(ns ball.core
  (:gen-class))

(defn height_at_t
  "Height of the ball at t for a given speed in km/h"
  [v t]
  (let [g 9.81       ; earth's gravity
        s (/ v 3.6)] ; convert speed in m/s
   (-(* s t) (* 0.5 g t t))))

(defn max-ball
  "Takes a parameter v (in km per hour) and returns the time in tenth of second
   of the maximum height recorded by the device."
  [v]
  (let [height (partial height_at_t v)]
    (some #(when
             (> (height (/ % 10)) (height (/ (inc %) 10))) %)
          (range))))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Hello, World!"))
