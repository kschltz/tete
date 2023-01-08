(ns user
  (:import (bb.mom.tete TeteApplication)
           (bb.mom.tete.entities Event)
           (java.util.function BiConsumer Consumer)
           (java.util.stream Collectors)))

(defn ->f [f]
  (reify Consumer
    (accept [_ x]
      (f x))
    BiConsumer
    (accept [_ x y]
      (f x y))))


(-> (Event/randomStream)
    (.limit 10)
    (.forEach (->f prn)))

(defn start []
  (TeteApplication/main (into-array String [""])))