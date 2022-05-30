(ns ball.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [ball.core :refer [max-ball]]))

(deftest a-test1
  (testing "max-ball"
     (is (= (max-ball 37), 10))
     (is (= (max-ball 45), 13))
     (is (= (max-ball 99), 28))
     (is (= (max-ball 85), 24))
     
)) 
