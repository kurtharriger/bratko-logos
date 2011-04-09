(ns bratko-logos.backtrack
  (:refer-clojure :exclude [reify inc ==])
  (:use [logos minikanren match])
  (:require [logos.arithmetic :as a]))

(defn-e f [x y]
    ([_ 0] (a/< x 3))
    ([_ 2] (a/<= 3 x) (a/< x 6))
    ([_ 4] (a/<= 6 x)))

(comment
  ;; fails
  ;; but it tries other branches pointlessly
  (run 1 [y]
       (f 1 y)
       (a/< 2 y))

  ;; cond-a
  )
  
