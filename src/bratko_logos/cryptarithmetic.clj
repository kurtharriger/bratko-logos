(ns bratko-logos.cryptarithmetic
  (:refer-clojure :exclude [reify inc ==])
  (:use [logos minikanren match]
        [logos.nonrel :only [defn-a]])
  (:require [logos.arithmetic :as a]))

(defn sum [n1 n2 n]
  (sum1 n1 n2 n 0 0 (range 10) (lvar)))

(defn-e sum1 [l1 l2 l3 c1 c2 dg1 dg2]
  ([[] [] [] _ c1 _ dg1])
  ([[?d1 . ?n1] [?d2 . ?n2] [?d . ?n] _ _ _ _]
     (exist [c3 dg3]
      (sum1 ?n1 ?n2 ?n c1 c3 dg1 dg3)
      (digitsum ?d1 ?d2 c3 ?d c2 dg3 dg2))))

(defn digitsum [d1 d2 c1 d c dg1 dg]
  (exist [dg2 dg3]
   (del-var d1 dg1 dg2)
   (del-var d2 dg2 dg3)
   (del-var d dg3 dg)
   (project [d1 d2 c1]
            (== s (+ d1 d2 c1))
            (project [s]
                     (== d (mod s 10))
                     (== c (/ s 10))))))

(defn-a del-var [a l1 l2]
  ([_ _ l1] (nonlvar a))
  ([]))