(ns pentafibonacci.core-test
  (:require [clojure.test :refer :all]
            [pentafibonacci.core :refer :all]))

(defn test-assert [act exp]
  (is (= act exp)))

(deftest a-test1
  (testing "count-odd-pentaFib : low values"
    (let [lstI [45, 68, 76, 100, 121]
          resultsI [15, 23, 25, 33, 40] ]
      (loop [i 0]
        (if (<= i 4)
          (do
            (test-assert (count-odd-pentaFib (lstI i)) (resultsI i))
            (recur (inc i))))))))
