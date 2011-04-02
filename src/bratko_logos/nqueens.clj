(ns bratko-logos.nqueens
  (:refer-clojure :exclude [reify inc ==])
  (:use [logos minikanren match]
        [logos.logic :only [member-o]]
        [logos.disequality :only [!=]])
  (:require [logos.nonrel :as nonrel]))

(defn-e nqueens-o [l]
  ([()])
  ([[[?x ?y] . ?others]]
     (exist []
      (nqueens-o ?others)
      (member-o ?y [1 2 3 4 5 6 7 8])
      (noattack-o [?x ?y] ?others))))

(defn-e noattack-o [q others]
  ([_ ()])
  ([[?x ?y] [[?x1 ?y1] . ?others]]
     (exist []
      (!= ?y ?y1)
      (nonrel/project [?y ?y1 ?x ?x1]
                      (!= (- ?y1 ?y) (- ?x1 ?x))
                      (!= (- ?y1 ?y) (- ?x ?x1)))
      (noattack-o [?x ?y] ?others))))

(comment
  (run 1 [q]
       (exist [y1 y2 y3 y4 y5 y6 y7 y8]
              (== q [[1 y1] [2 y2] [3 y3] [4 y4] [5 y5] [6 y6] [7 y7] [8 y8]])
              (nqueens-o q)))
  )