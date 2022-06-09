(ns tic-tac-toe.core
  (:gen-class))

; Constants
(def init_board [1 2 3 4 5 6 7 8 9])

(defn line->str
  [line]
  (str "| " (nth line 0) " | " (nth line 1) " | " (nth line 2) " |"))

(defn print-board
  "Take a tic tac toe board that is an array of 9 index and
   display it has a 3x3 board (kind of)."
  [board]
  (let [b (partition 3 board)
        separator #(println "+---+---+---+")]
    (separator)
    (println (line->str (first b)))
    (separator)
    (println (line->str (second b)))
    (separator)
    (println (line->str (last b)))
    (separator)))

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
   [board idx]
   (and (some? idx) (integer? (get board (dec idx)))))

(defn next-player
  "Return the next player."
  [player]
  (let [player_switch {:X :O :O :X}]
    (player_switch player)))

(defn do-player-move
  "Play the players move. You need to ensure that the move is valid.
   It returns the new board."
  [board player move]
  (assoc board (dec move) (name player)))

(defn human-play
  "If human plays we need to ask her its move, check if it is valid and do the
   move"
  [board player]
  (println "It is your turn player" (name player) ", where do you play?")
  (loop [move (read-user-input)]
    (if-not (move-is-valid? board move)
      (do
        (println "Invalid move, pick number in the board... ")
        (print-board board)
        (recur (read-user-input)))
      (do-player-move board player move))))

(defn machine-play
  "As it is not implemented let's call human-play for now"
  [board player]
  (println "Machine should play but it is not yet implemented")
  (human-play board player))

(defn human?
  "Return true if the player is human. For now :X is humain"
  [player]
  (= player :X))

(defn play-one-turn
  "Play one turn for a given player and board
    - Take the user input
    - check that it is valid
    - if yes play
    - else ask again
    Return the new board
   "
   [board player]
   (if (human? player)
     (human-play board player)
     (machine-play board player)))

(defn extract-moves
  "Return the list of index for a given player from a board
   Example: [X X 3 O O 6 7 8 9] X -> (1 2)"
  [board player]
  (keys
    (filter
      (fn [[_ v]] (= v (name player)))
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
  (let [win_moves '((1 2 3) (4 5 6) (7 8 9)
                    (1 4 7) (2 5 8) (3 6 9)
                    (1 5 9) (3 5 7))
        moves (extract-moves board player)]
   (some #(contains-winning-moves? moves %) win_moves)))

(defn board-is-complete?
  "Return true if there is no more moves."
  [board]
  (every? string? board))

; ---------------------------
; Main loop
(defn game-loop
  []
  (loop [board init_board
         player :X]
    (print-board board)
    (let [new_board (play-one-turn board player)]
      (cond
        (board-is-winning? new_board player) (do
                                               (print-board new_board)
                                               (println "Player" (name player) "won"))
        (board-is-complete? new_board) (do
                                         (print-board new_board)
                                         (println "Board is complete without winner"))
        :else (recur new_board (next-player player))))))

(defn -main
  []
  (game-loop))
