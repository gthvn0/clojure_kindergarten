(ns brainfuck.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [brainfuck.core :refer [bf_run]]))

(deftest bf-hello
  (testing "Hello world"
    (is (= "Hello World!\n"
           (bf_run (str "++++++++"
                        "[>++++[>++>+++>+++>+<<<<-]"
                        ">+>+>->>+[<]<-]>>.>---.+++++++.."
                        "+++.>>.<-.<.+++.------.--------.>>+.>++."))))))
