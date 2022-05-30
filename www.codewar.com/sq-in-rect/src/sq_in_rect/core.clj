(ns sq-in-rect.core
  (:gen-class))

(defn sq-in-rect [lng wdth]
  (when (not= lng wdth)
    (loop [longueur lng, largeur wdth, result []]
      (if (some zero? (list longueur largeur))
        result
        (if (< longueur largeur)
          ;; 3 5
          (recur longueur (- largeur longueur) (conj result longueur))
          ;; 5 3
          (recur (- longueur largeur) largeur (conj result largeur)))))))

(defn -main
  "I don't do a whole lot ... yet."
  [arg1 arg2]
  (let [a (Integer/parseInt arg1)
        b (Integer/parseInt arg2)]
    (println (sq-in-rect a b))))
