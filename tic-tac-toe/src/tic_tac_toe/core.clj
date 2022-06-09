(ns tic-tac-toe.core
  (:gen-class))

; Constants
(def init_board [1 2 3 4 5 6 7 8 9])

(defn line->str
  [line]
  (str " " (nth line 0) " | " (nth line 1) " | " (nth line 2)))

(defn print-board
  "Take a tic tac toe board that is an array of 9 index and
   display it has a 3x3 board (kind of)."
  [board]
  (let [b (partition 3 board)]
    (println (line->str (first b)))
    (println "---+---+---")
    (println (line->str (second b)))
    (println "---+---+---")
    (println (line->str (last b)))))

(defn read-user-input
  "Read the user input and returns an integer or nil if not valid"
  []
  (let [user_input (read-line)]
    (if (every? #(Character/isDigit %) user_input)
      (Integer/parseInt user_input)
      nil)))

(defn move-is-valid?
  "Check that a move is valid in the context of a tic tac toc
   grid. That means it must be in the range of the game but also
   not an already played spot."
   [idx board]
   (and (some? idx) (integer? (get board (dec idx)))))

(defn next-player
  [player]
  (if (= player \X)
    \O
    \X))

(defn play-move
  "Play the players move. You need to ensure that the move is valid.
   It returns the new board."
  [player move board]
  (assoc board (dec move) player))

(defn game-one-turn
  "Play one turn for a given player and board
    - Take the user input
    - check that it is valid
    - if yes play
    - else ask again
    Return the new board
   "
   [board player]
   (println "It is your turn player " player ", where do you play? ")
   (loop [move (read-user-input)]
     (if-not (move-is-valid? move board)
       (do
         (println "Invalid move, pick number in the board... ")
         (print-board board)
         (recur (read-user-input)))
       (play-move player move board))))

(defn extract-moves
  "Return the list of index for a given player from a board
   Example: [X X 3 O O 6 7 8 9] X -> (1 2)"
  [board player]
  (keys
    (filter
      (fn [[_ v]] (= v player))
      (zipmap init_board board))))

(defn contains-winning-moves?
  [our_m win_m]
  (= (count our_m)
     (count (set (concat our_m win_m)))))

(defn board-is-winning?
  "Return true if the board is winning.
   It is winning if X or O are like:
      -> Horizontal winning
         1 2 3     4 5 6     7 8 9
         X X X     . . .     . . .
         . . .     X X X     . . .
         . . .     . . .     X X X

      -> Vertical winning
         1 4 7     2 5 8     3 6 9
         X . .     . X .     . . X
         X . .     . X .     . . X
         X . .     . X .     . . X

      -> Diagonal winning
         1 5 9     3 5 7
         X . .     . . X
         . X .     . X .
         . . X     X . .

  So we win if our moves is part of ones of this 8 winning values.
  "
  [board player]
  (let [win1 '(1 2 3)
        win2 '(4 5 6)
        win3 '(7 8 9)
        win4 '(1 4 7)
        win5 '(2 5 8)
        win6 '(3 6 9)
        win7 '(1 5 9)
        win8 '(3 5 7)
        moves (extract-moves board player)]
   (or
     (contains-winning-moves? moves win1)
     (contains-winning-moves? moves win2)
     (contains-winning-moves? moves win3)
     (contains-winning-moves? moves win4)
     (contains-winning-moves? moves win5)
     (contains-winning-moves? moves win6)
     (contains-winning-moves? moves win7)
     (contains-winning-moves? moves win8))))

; Main loop
(defn game-loop
  []
  (loop [board init_board
         player \X]
    (print-board board)
    (let [new_board (game-one-turn board player)]
      (if (board-is-winning? new_board player)
        (do
          (print-board new_board)
          (println "YOU WON" player))
        (recur new_board (next-player player))))))

(defn -main
  []
  (game-loop))
