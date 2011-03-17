(ns bratko-logos.monkey-banana
  (:require [logos.minikanren :as mk]))

(mk/defn-e move-o [before action after]
  ([[:middle :onbox :middle :hasnot]
    :grasp
    [:middle :onbox :middle :has]])
  ([[?pos :onfloor ?pos ?has]
    :climb
    [?pos :onbox ?pos ?has]])
  ([[?pos1 :onfloor ?pos1 ?has]
    :push
    [?pos2 :onfloor ?pos2 ?has]])
  ([[?pos1 :onfloor ?box ?has]
    :walk
    [?pos2 :onfloor ?box ?has]]))

(defn-e canget-o [state out]
  ([[_ _ _ :has] true])
  ([_ _] (exist [action next]
                (move-o state action next)
                (canget-o next out))))

(comment
 (run 1 [q]
      (canget-o [:atdoor :onfloor :atwindow :hasnot] q))
 )