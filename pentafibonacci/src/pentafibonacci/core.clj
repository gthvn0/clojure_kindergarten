(ns pentafibonacci.core
  (:gen-class))

(defn pentafibo
  ([] (pentafibo 0 1 1 2 4))
  ([a b c d e] (lazy-seq (cons (bigint a) (pentafibo (bigint b) (bigint c) (bigint d) (bigint e) (+' a b c d e))))))

; NOTE: After looking solutions it is possible to do it without bigint.
;       A pattern exist and can be used...

(defn count-odd-pentaFib
  [n]
  (- (count (filter odd? (vec (take (+ n 1) (pentafibo))))) 1))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Run lein test to check that function is working as expexted"))
