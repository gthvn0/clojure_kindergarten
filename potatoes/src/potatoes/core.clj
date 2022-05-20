(ns potatoes.core
  (:gen-class)
  (:require [clojure.test :refer [is]]))

;; 100 Kg de potatoes, 99% d'eau => 99Kg d'eau, 1Kg de matière seche
;; Après le four
;; 50Kg de potatoes, 98% d'eau => 49Kg d'eau, 1Kg de matière seche

(defn potatoes
  "Return the final weight knowing the init percent of water, the init weight
   and the final percent of water"
  [p0 w0 p1]
  (let [init_weight_dry_matter (* (/ (- 100 p0) 100) w0)
        final_percent_dry_matter (- 100 p1)]
    (int (/ (* 100 init_weight_dry_matter) final_percent_dry_matter))
  ))

(defn dotest
  [p0 w0 p1 sol]
  (is (= (potatoes p0 w0 p1) sol)))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Basic Tests")
  (dotest 82 127 80 114)
  (dotest 93 129 91 100)
  )
