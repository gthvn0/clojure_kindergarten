(ns dir-reduc.core
  (:gen-class))

; (reduce f coll)(reduce f val coll)
;
; f should be a function of 2 arguments. If val is not supplied,
; returns the result of applying f to the first 2 items in coll, then
; applying f to that result and the 3rd item, etc. If coll contains no
; items, f must accept no arguments as well, and reduce returns the
; result of calling f with no arguments.  If coll has only 1 item, it
; is returned and f is not called.  If val is supplied, returns the
; result of applying f to val and the first item in coll, then
; applying f to that result and the 2nd item, etc. If coll contains no
; items, returns val and f is not called.

(def opposite {"NORTH" "SOUTH",
               "SOUTH" "NORTH",
               "WEST" "EAST",
               "EAST" "WEST"})

(defn removeOpposite
  [v d]
  (if (= (opposite (peek v)) d)
    (pop v)
    (conj v d)))

; (reduce removeOpposite [] ["NORTH" "WEST" "EAST" "SOUTH" "WEST"])
;
;  v [], "NORTH"
;  v ["NORTH"], "WEST"
;  v ["NORTH" "WEST"], "EAST"
;  v ["NORTH"], "SOUTH"
;  v [], "WEST"
;  v["WEST"]


(defn dirReduc
  "Takes an array of strings and returns an array of strings with the needless directions removed (W<->E or S<->N side by side)."
  [v]
  (not-empty (reduce removeOpposite [] v)))

(defn -main
  "I don't do a whole lot ... yet."
  []
  (println "Hello, World!"))
