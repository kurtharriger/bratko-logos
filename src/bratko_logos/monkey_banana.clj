(ns bratko-logos.monkey-banana
  (:require [logos.minikanren :as mk]
            [clojure.contrib.macro-utils :as macro]))

(defn move-o [before action after]
  (mk/cond-e
   ((mk/== [:middle :onbox :middle :hasnot] before)
    (mk/== action :grasp)
    (mk/== [:middle :onbox :middle :has] after))
   ((mk/exist [pos has]
              (mk/== [pos :onfloor pos has] before)
              (mk/== :climb action)
              (mk/== [pos :onbox pos has] after)))
   ((mk/exist [pos1 pos2 box has]
              (mk/== [pos1 :onfloor pos1 has] before)
              (mk/== :push action)
              (mk/== [pos2 :onfloor pos2 has] after)))
   ((mk/exist [pos1 pos2 box has]
              (mk/== [pos1 :onfloor box has] before)
              (mk/== :walk action)
              (mk/== [pos2 :onfloor box has] after)))))

(defn canget-o [state out]
  (macro/symbol-macrolet [_ (mk/lvar)]
   (mk/cond-e
    ((mk/== [_ _ _ :has] state) (mk/== out [true state]))
    ((mk/exist [action next]
               (move-o state action next)
               (canget-o next out))))))

(comment
  ;; (true)
  (mk/run 1 [q]
          (canget-o [:atdoor :onfloor :atwindow :hasnot] q))

  ;; challenge collect the steps

  ;; With pattern matching we can write the following

  (defn-e move [before action after]
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

  (mk/run 1 [q]
          (move [:middle :onbox :middle :hasnot] :grasp q))
  )