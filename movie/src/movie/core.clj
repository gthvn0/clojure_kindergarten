(ns movie.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Hello, World!"))

;; Cost of systemA
(defn cost_systemA
  "Evaluate the cost of systemA"
  [ticket_price times]
  (* ticket_price times))

;; Cost of systemB
(defn cost_systemB
  "Evaluate the cost of systemB"
  [card_price ticket_price perc times]
  (loop [p (* ticket_price perc)
            idx 0
            total card_price]
    (if (= idx times)
      total
      (recur (* p perc) (+ idx 1) (+ total p)))))

;; now we can easily compare the price of the systemA and systemB if
;; we know the number of times we are going to the cinema:
(defn diff_costA_costB
  "Return the difference between the cost of A and the cost of B"
  [card_price ticket_price perc times]
  (- (cost_systemA ticket_price times)
     (cost_systemB card_price ticket_price perc times)))

;; Let's try to manually find the answer using dichotomy with the following
;; inputs:
;;  - card is 500 euros
;;  - ticket is 15 euros
;;  - fraction of what we paid for the previous ticket is 0.9

;; We can create a function that will help us to test outputs
(def test1
  (fn [t]
    [(cost_systemA 15 t)
     (cost_systemB 500 15 0.9 t)
     (diff_costA_costB 500 15 0.9 t)]))

;; Let's start with 30
(test1 30)
;; -179 euros... really need to go more times...

;; 40 times
(test1 40)
;; -33 euros... really need to go more times...

;; 50 times
(test1 50)
;; +115 ok so less is ok

;; 45 times
(test1 45)
;; +41 ok still less

;; 42 times
(test1 42)
;; -3 so we probably need to go 43 times if we want
;; the card be profitable... let's check that

;; 43 times
(test1 43)
;; +11 ok we've got it. After 43 times the card is
;; profitable

;; Let's do this automatically now
(defn card_is_profitable
  "Return true if card is profitable, false otherwise"
  [card ticket perc x]
  (> (cost_systemA ticket x) (cost_systemB card ticket perc x)))


(defn compute_movie
  [card ticket perc x]
  (if (card_is_profitable card ticket perc x)
    x
    (compute_movie card ticket perc (+ 1 x))))

(defn movie_basic
  "Return the number of times we need to go to cinema from which a card is
  profitable"
  [card ticket perc]
  (compute_movie card ticket perc 1))

; TEST
(movie_basic 500  15  0.9) ;; should return 43
                     ;; (with card the total price is 634, with tickets 645)
(movie_basic 100  10  0.95) ;; should return 24
                      ;; (with card the total price is 235, with tickets 240)

;; movie_basic is O(n). We will try to reduce a little bit by looking by interval.
;; For a given size we want to get the upper and lower bound. For example if the
;; answer is 47 and the given size is ten we want to return (40 50)

(defn interval_is_ok
  "It takes two value and return true if card is profitable in this given interval"
  [card ticket perc x y]
  (and
    (not (card_is_profitable card ticket perc x))
    (card_is_profitable card ticket perc y)))

(defn get_interval
  "Return an interval of a given size where card is profitable"
  [card ticket perc size base]
  (loop [matcher [base (+ base size)]]
    (if (interval_is_ok card ticket perc (first matcher) (second matcher))
      matcher
      (recur [(second matcher) (+ size (second matcher))]))))

(interval_is_ok 500 15 0.9 10 200) ; must be true
(interval_is_ok 500 15 0.9 10 20) ; must be false
(interval_is_ok 500 15 0.9 100 200) ; must be false

;; So we can start with an interval of 100
(get_interval 500 15 0.9 100 0) ; should return [0 100]

;; then interval of 10 using the lower value previously find
(get_interval 500 15 0.9 10 0) ; should return [40 50]

;; and now we can check all starting from 40
(card_is_profitable 500 15 0.9 40)
(card_is_profitable 500 15 0.9 41)
(card_is_profitable 500 15 0.9 42)
(card_is_profitable 500 15 0.9 43) ;; => got it

(defn movie
  "Return the number of times we need to go to cinema from which a card is
  profitable"
  [card ticket perc]
  (let [first_try (get_interval card ticket perc 100 0)
        second_try (get_interval card ticket perc 10 (first first_try))]
    (loop [n (first second_try)]
      (if (card_is_profitable card ticket perc n)
        n
        (recur (+ 1 n))))))

(movie 500  15  0.9) ;; should return 43
