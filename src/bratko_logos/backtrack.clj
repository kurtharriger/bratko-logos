(ns bratko-logos.backtrack
  (:refer-clojure :exclude [reify inc ==])
  (:use [logos minikanren match]
        [logos.nonrel :only [defn-a]])
  (:require [logos.arithmetic :as a]))

(defn-e f [x y]
    ([_ 0] (a/< x 3))
    ([_ 2] (a/<= 3 x) (a/< x 6))
    ([_ 4] (a/<= 6 x)))

(defn-e f-log-a [x y]
    ([_ 0] (log "clause 1") (a/< x 3))
    ([_ 2] (log "clause 2") (a/<= 3 x) (a/< x 6))
    ([_ 4] (log "clause 3") (a/<= 6 x)))

(defn-a f-log-b [x y]
    ([_ 0] (log "clause 1") (a/< x 3))
    ([_ 2] (log "clause 2") (a/<= 3 x) (a/< x 6))
    ([_ 4] (log "clause 3") (a/<= 6 x)))

(comment
  ;; fails
  ;; but it tries other branches pointlessly
  (run 1 [y]
       (f 1 y)
       (a/< 2 y))

  ;; we can see that here
  (run-debug 1 [y]
             (f-log-a 1 y)
             (a/< 2 y))

  ;; cond-a
  (run-debug 1 [y]
             (f-log-b 1 y)
             (a/< 2 y))
  )
  
