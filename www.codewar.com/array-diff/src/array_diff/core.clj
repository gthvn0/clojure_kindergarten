(ns array-diff.core
  (:gen-class))

;; fn v1 -> v2 - {item}
(filter (fn [x] (not= 2 x)) [1 2 2 3])

(defn array-diff
  "Subtracts one list from another and returns the result.
  array-diff [1 2 2] [2] -> [1]
  "
  [a b]
  (loop [v1 a
         v2 b]
  (if (empty? v2)
    (vec v1)
    (recur (filter (fn [x] (not= (peek v2) x)) v1)
           (pop v2)))))

(array-diff [1 2 2 3] [2])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
