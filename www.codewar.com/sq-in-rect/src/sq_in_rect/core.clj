(ns sq-in-rect.core
  (:gen-class))

(defn sq-in-rect
  [lng wdth]
  (loop [longueur lng
         largeur wdth
         result []]
    (if (some zero? (list longueur largeur))
      ;; 5 5 return nil. So when there is only one square we returned nil
      (when (> (count result) 2)
        result)
      (if (< longueur largeur)
        (recur longueur (- largeur longueur) (conj result longueur))
        (recur (- longueur largeur) largeur (conj result largeur))))))

(defn -main
  "I don't do a whole lot ... yet."
  [arg1 arg2]
  (let [a (Integer/parseInt arg1)
        b (Integer/parseInt arg2)]
    (println (sq-in-rect a b))))
