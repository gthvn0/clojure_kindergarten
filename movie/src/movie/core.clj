(ns movie.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Hello, World!"))

;; Cost of systemA is easy
(defn cost_systemA
  "Evaluate the cost of systemA"
  [ticket_price times]
  (* ticket_price times))

;; Cost of systemB needs more compute. Let's decompose it
;;
;; If we go 3 times to cinema we need to apply the following
;; discount must be applied to the ticket with 0.9 as percentage:
;;   -> vec [ 0.9 0.9*0.9 0.9*0.9*0.9 ]
;; So we need to construct this vec and we will need to create a
;; function exponential:

(defn exp
  "return x**n"
  [x n]
  (reduce * (repeat n x)))

;; Now we need to apply this to the price of the ticket. It is just
;; a map of the partial function to the price of the ticket. The
;; partial function is:
(defn exp_x
  [x]
  (partial exp x))

;; We will then add the price of the card. Note that the lenght of
;; the vec is the number of times we will go to the cinema. Using range
;; we can create a vec with the correct lenght. So the cost of B is
;; computed as follow:

(defn cost_systemB
  "Evaluate the cost of systemB"
  [card_price ticket_price perc times]
  (+ card_price
     (reduce +
             (map (partial * ticket_price)
                  (map (exp_x perc)
                       (vec (range 1 (+ times 1))))))))

;; now we can easily compare the price of the systemA and systemB if
;; we now the number of times we are going to the cinema:
(defn compare_A_B
  "Return the difference between the cost of A and the cost of B"
  [card_price ticket_price perc times]
  (- (cost_systemA ticket_price times)
     (cost_systemB card_price ticket_price perc times)))

;; Let's try to manually find the answer using dichotomy with the following
;; inputs:
;;  - card is 500 euros
;;  - ticket is 15 euros
;;  - fraction of what we paid for the previous ticket is 0.9

;; Let's start with 30
(def t1 30)
(cost_systemA 15 t1)
(cost_systemB 500 15 0.9 t1)
(compare_A_B 500 15 0.9 t1) ;; -179 euros... really need to go more times...

;; 40 times
(def t2 40)
(cost_systemA 15 t2)
(cost_systemB 500 15 0.9 t2)
(compare_A_B 500 15 0.9 t2) ;; -33 so we need to go more times to cinema

;; 50 times
(def t3 50)
(cost_systemA 15 t3)
(cost_systemB 500 15 0.9 t3)
(compare_A_B 500 15 0.9 t3) ;; +115 ok so less is ok

;; 45 times
(def t4 45)
(cost_systemA 15 t4)
(cost_systemB 500 15 0.9 t4)
(compare_A_B 500 15 0.9 t4) ;; +41 ok still less

;; 42 times
(def t5 42)
(cost_systemA 15 t5)
(cost_systemB 500 15 0.9 t5)
(compare_A_B 500 15 0.9 t5) ;; -3 so we probably need to go 43 times if we want
                            ;; the card be profitable... let's check that

;; 43 times
(def t6 43)
(cost_systemA 15 t6)
(cost_systemB 500 15 0.9 t6)
(compare_A_B 500 15 0.9 t6) ;; +11 ok we've got it. After 43 times the card is
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

(defn movie
  "Return the number of times we need to go to cinema from which a card is
  profitable"
  [card ticket perc]
  (compute_movie card ticket perc 1))

; TEST
(movie 500  15  0.9) ;; should return 43 
                     ;; (with card the total price is 634, with tickets 645)
(movie 100  10  0.95) ;; should return 24 
                      ;; (with card the total price is 235, with tickets 240)
