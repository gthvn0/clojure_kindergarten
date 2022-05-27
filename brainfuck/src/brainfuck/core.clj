(ns brainfuck.core
  (:gen-class))

;; Brainfuck has 8 commands
;; +--------+--------+-------------------------------------------------------+
;; | BF sym | Token  | Description                                           |
;; +--------+--------+-------------------------------------------------------+
;; | '>'    | :next  | increment data pointer (next cell)                    |
;; | '<'    | :prev  | decrement data pointer (prev cell)                    |
;; | '+'    | :inc   | add one to pos byte                                   |
;; | '-'    | :dec   | substrace one to pos byte                             |
;; | '.'    | :out   | output the byte of pos cell                           |
;; | ','    | :in    | accept one byte of input in pos cell                  |
;; | '['    | :begin | if byte is 0 jump to :end instead of next cell        |
;; | ']'    | :end   | if byte is not 0 jump to :being instead of next cell  |
;; +--------+--------+-------------------------------------------------------+
;;
;; Steps:
;;   - We need to keep track of cells, current data pointer, current token
;;   - We will need to get a list of tokens
;;   - Should be enough to execute the code

(defn bf_get_token
  "Take a BF command and return its token"
  [command]
  (case command
    \> :next
    \< :prev
    \+ :inc
    \- :dec
    \. :out
    \, :in
    \[ :begin
    \] :end
    nil))

(defn bf_parser
  "Take a BF program and return a list of tokens
   Example: ++. => (:inc :inc :out)
  "
  [bf_code]
  (for [c (seq bf_code)
        :when (some? (bf_get_token c))]
    (bf_get_token c)))

(defn find_begin_loop
  "Find the begin of a loop given a list of tokens and the idx of the end
   in this list
     - Example: '(:inc :begin :dec :begin :next :end :end :in) 5 -> 3
     - Example: '(:inc :begin :dec :begin :next :end :end :in) 6 -> 1
  "
  [tokens idx]
  (loop [nested 0
         cur (dec idx)]
    (case (nth tokens cur)
      :end (recur (inc nested) (dec cur))
      :begin (if (= nested 0)
             cur
             (recur (dec nested) (dec cur)))
      (recur nested (dec cur)))))

(defn find_end_loop
  "Find the end of a loop given a list of tokens and the idx of the begin
   in this list
     - Example: '(:inc :begin :dec :begin :next :end :end :in) 1 -> 6
     - Example: '(:inc :begin :dec :begin :next :end :end :in) 3 -> 5
  "
  [tokens idx]
  (loop [nested 0
         cur (inc idx)]
    (case (nth tokens cur)
      :begin (recur (inc nested) (inc cur))
      :end (if (= nested 0)
             cur
             (recur (dec nested) (inc cur)))
      (recur nested (inc cur)))))

(defn new_vm
  [cells dp ct]
  {:cells cells, :dp dp, :ct ct})

(defn bf_update_vm
  "Returned the vm_state updated after consuming first token.
   Our state is reprensented by:
    - cells: [0 0 1 2 0]
                  ^
    - dp:         2        (the current position of the cursor)
    - ct:  current token"
  [vm tokens]
  (let [token_read (nth tokens (vm :ct))]
    (case token_read
      :next (new_vm (vm :cells) (inc (vm :dp)) (inc (vm :ct)))
      :prev (new_vm (vm :cells) (dec (vm :dp)) (inc (vm :ct)))
      :inc  (new_vm
              (assoc (vm :cells) (vm :dp) (inc (get (vm :cells) (vm :dp))))
              (vm :dp)
              (inc (vm :ct)))
      :dec  (new_vm
              (assoc (vm :cells) (vm :dp) (dec (get (vm :cells) (vm :dp))))
              (vm :dp)
              (inc (vm :ct)))
      :in   nil
      :out (do
             (print (char (get (vm :cells) (vm :dp))))
             (new_vm (vm :cells) (vm :dp) (inc (vm :ct))))
      :begin (let [new_cp (if (= 0 (get (vm :cells) (vm :dp)))
                            (find_end_loop tokens (vm :ct))
                            (inc (vm :ct)))]
               (new_vm (vm :cells) (vm :dp) new_cp))
      :end (let [new_cp (if (= 0 (get (vm :cells) (vm :dp)))
                          (inc (vm :ct))
                          (find_begin_loop tokens (vm :ct)))]
             (new_vm (vm :cells) (vm :dp) new_cp)))))

(defn bf_run
  "Take a list of BF tokens and run them"
  [tokens]
  (loop [vm {:cells (vec (repeat 20 0)) ;; Cells are not dynamic
             :dp 0   ;; data pointer
             :ct 0}] ;; program counter
    (when (< (vm :ct) (count tokens))
      (recur (bf_update_vm vm tokens)))))

(defn -main
  "Run BF code"
  [code]
  (bf_run (bf_parser code))
  (println))
