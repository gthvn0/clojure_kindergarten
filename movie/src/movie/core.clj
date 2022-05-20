(ns movie.core
  (:gen-class))

;; System A : he buys a ticket (15 dollars) every time
;; System B : he buys a card (500 dollars) and a first ticket for 0.90 times the ticket price,
;; then for each additional ticket he pays 0.90 times the price paid for the previous ticket.
;;
;; System A : 15 * 3 = 45
;; System B : 500 + 15 * 0.90 + (15 * 0.90) * 0.90 + (15 * 0.90 * 0.90) * 0.90 ( = 536.5849999999999, no rounding for each ticket)
;;
;; System B is better than A when ceil(price of System B) < price of System A.

;; So we will iterate until System B is cheaper than A
;; If we take the example
;; Iter 1, A 15, B 500 + 15 * 0.9
;; Iter 2, A (Iter 1 + ticket_price), B (Iter 1 + ticket_price_iter_1 * 0.9)
;; Iter 3, A (Iter 2 + ticket_price), B (Iter 2 + ticket_price_iter_2 * 0.9)

(defn movie
  "Return the number of times we need to go to cinema from which a card is
  profitable"
  [card ticket perc]
  (loop [
         iter 1
         systemA ticket
         systemB (+ card (* ticket perc))]
    (if (< (Math/ceil systemB) systemA)
      (do
        (println (str "card " card " .. ticket " ticket " .. perc " perc))
        (println (str "iter " iter " .. A: " systemA " .. B: " systemB))
        iter)
      (recur
        (inc iter)
        (+ systemA ticket)
        (+ systemB (* ticket (Math/pow perc (inc iter))))))))

(movie 0 10 0.95)
;; If I go once:
;;   - systemA => 10
;;   - systemB => 0 + 10 * 0.95 = 9.5
;; Ceil systemB => 10 so not good. We need one more iteration

(movie 100 10 0.95) ;; should return 24

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Hello, World!"))
