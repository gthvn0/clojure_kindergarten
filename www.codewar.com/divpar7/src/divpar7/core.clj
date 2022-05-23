(ns divpar7.core
  (:gen-class))

;; A number m of the form 10x + y is divisible by 7 if and only if x − 2y is
;; divisible by 7

;; Your task is to return to the function seven(m) (m integer >= 0) an array of
;; numbers, the first being the last number m with at most 2 digits obtained by
;; your function (this last m will be divisible or not by 7), the second one
;; being the number of steps to get the result.

;; Example with 1603
;; iter1: 1603 as most than 2 digits => 160 - (2*3) = 154
;; iter2: 156 as most than 2 digits => 15 - (2*8) = 7   => divisible by 7
;; so return [ 7 2 ]

(quot 1603 10) ;; 160
(rem 1603 10) ;; 3

(defn toto
  [a b]
  (vector a b))

(toto 5 "help")

(defn seven
  [m]
  (loop [iter 0
         number m]
    (if (< number  100)
      (vector number iter)
      (let [x (quot number 10)
            y (rem number 10)]
        (recur (inc iter) (- x (* 2 y)))))))

(seven 1603) ;; [7, 2]
(seven 371) ;; [35, 1]
(seven 483) ;; [42, 1]

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Hello, World!"))
