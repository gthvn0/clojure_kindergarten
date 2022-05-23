(ns dir-reduc.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [dir-reduc.core :refer [dirReduc]]))

(deftest a-test
  (testing "Test 1"
    (def ur ["NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST"])
    (def vr ["WEST"])
    (is (= (dirReduc ur) vr))))
