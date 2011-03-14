(ns bratko-logos.monkey-banana
  (:require [logos.minikanren :as mk]))

(defn walk [pos1 pos2]
  (mk/cond-e
   ((mk/== pos1 :door) (mk/== pos2 :window))
   ((mk/== pos1 :window) (mk/== pos2 :door))))

(defn move [before action after]
  (mk/cond-e
   ((mk/== [:middle :onbox :middle :hasnot] before)
    (mk/== action :grasp)
    (mk/== [:middle :onbox :middle :has] after))
   ((mk/exist [pos1 pos2 box has]
              (mk/== [pos1 :onfloor box has] before)
              (mk/== (walk pos1 pos2) action)
              (mk/== [pos2 :onfloor box has] after)))))

(comment
  (defn-e walk [pos1 pos2]
    ([:door :window])
    ([:window :door]))

  (defn-e move [before action after]
    ([[:middle :onbox :middle :hasnot] :grasp [:middle :onbox :middle :has]])
    ([[?pos1 :onfloor ?box ?has] _ [?pos2 :onfloor ?box ?has]]))

  (mk/run 1 [q]
          (move [:middle :onbox :middle :hasnot] :grasp q))
  )