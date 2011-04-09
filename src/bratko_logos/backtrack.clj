(ns bratko-logos.backtrack
  (:refer-clojure :exclude [reify inc ==])
  (:use [logos minikanren match])
  (:require [logos.nonrel :as n]
            [logos.arithmetic :as a]))

(defn-e f [x y]
  ([_ 0] (n/project [x]
                    (== (< x 3) true)))
  ([_ 2] (n/project [x]
                    (== (<= 3 x) true)
                    (== (< x 6) true)))
  ([_ 4] (n/project [x]
                    (== (<= 6 x) true))))

(comment
  ;; preferred syntax
  (defn-e f [x y]
    ([_ 0] (a/< x 3))
    ([_ 2] (a/<= 3 x) (a/< x 6))
    ([_ 4] (a/<= 6 x)))
  )

(comment
  ;; fails
  ;; but it tries other branches pointlessly
  (run 1 [y]
       (f 1 y)
       (n/project [y]
                  (== (< 2 y) true)))

  ;; cond-a
  )
  
