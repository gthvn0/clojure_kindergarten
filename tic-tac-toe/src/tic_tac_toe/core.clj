(ns tic-tac-toe.core
  (:gen-class))


; -----------------------------------------------------------------------------
; Define the display of a board.

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

; -----------------------------------------------------------------------------
; Define AI move

(defn first-empty
  "Return the first empty room on the board
   Example: [X 2 3 O 5 6 7 8 9] -> 2"
  [board]
  (first (filter integer? board)))

(defn basic-ai
  "Just peek the first empty place and return the move"
  [board]
  (let [move (first-empty board)]
    (println "> Machine chose" move)
    move))

(defn machine-play
  "Call one of the AI"
  [board]
  (basic-ai board))

; -----------------------------------------------------------------------------
; Define Human move

(defn read-human-input
  "Read the user input and returns an integer or nil if not valid and if it
   is valid returns its move"
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

(defn human-play
  "Ask her its move, check if it is valid and do the move"
  [board]
  (loop [move (read-human-input)]
    (if-not (move-is-valid? board move)
      (do
        (println "> Invalid move, pick a free number in the board... ")
        (print-board board)
        (recur (read-human-input)))
      move)))

; -----------------------------------------------------------------------------
; Define when a board is winning

; get-vlines, get-hlines and get-diags are used to check if a board is winning.
; To win a player need to fill one of this eight lines so we can extract them
; from a board to check that if there is only X or O.

(defn get-vlines
  "Return vertical lines"
  [board]
  (let [part (partition 3 board)]
    (list
    (for [x part :let [y (first x)]] y)
    (for [x part :let [y (second x)]] y)
    (for [x part :let [y (last x)]] y))))

(defn get-hlines
  "Return horizontal-lines"
  [board]
  (partition 3 board))

(defn get-diags
  "Return diagonals"
  [board]
  (list
    (list (nth board 0) (nth board 4) (nth board 8))
    (list (nth board 2) (nth board 4) (nth board 6))))

(defn get-winning-lines
  "Return a list of all winning lines"
  [board]
  (concat
    (get-vlines board)
    (get-hlines board)
    (get-diags board)))

(defn line-is-winning?
  "Take a line and check if it is winning. It is winning if all items are the
   same X X X or O O O."
  [line]
  (= 1 (count (set line))))

(defn board-is-winning?
  "A board is winning if one of all its winning lines is full of X or O"
  [board]
  (let [win_lines (get-winning-lines board)]
    (if (some line-is-winning? win_lines)
      true
      false)))

(defn board-is-complete?
  "Return true if there is no more moves."
  [board]
  (every? string? board))

; -----------------------------------------------------------------------------
; Define functions to manage the game loop

(defn next-player
  "Return the next player."
  [player]
  (let [player_switch {:X :O :O :X}]
    (player_switch player)))

(defn human?
  "Return true if the player is human. For now :X is humain"
  [player]
  (= player :X))

(defn do-player-move
  "Play the players move. You need to ensure that the move is valid befor
   calling this function. It returns the new board."
  [board player move]
  (assoc board (dec move) (name player)))

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
     (do
       (println "> It is your turn player" (name player) ", where do you play?")
       (do-player-move board player (human-play board)))
     (do-player-move board player (machine-play board))))

; -----------------------------------------------------------------------------
; Main loop

(defn game-loop
  []
  (loop [board [1 2 3 4 5 6 7 8 9]
         player :X]
    (print-board board)
    (let [new_board (play-one-turn board player)]
      (cond
        (board-is-winning? new_board) (do
                                        (print-board new_board)
                                        (println "> Player" (name player) "won"))
        (board-is-complete? new_board) (do
                                         (print-board new_board)
                                         (println "> Board is complete without winner"))
        :else (recur new_board (next-player player))))))

(defn -main
  []
  (game-loop))
