(ns user
  (:require [babashka.http-client :as http]
            [clojure.data.json :as json]
            [clojure.pprint :as pp])
  (:import [bb.mom.tete.entities EventType]
           [java.time LocalDateTime]

           [java.util.function BiConsumer Consumer]))

(defn ->f [f]
  (reify Consumer
    (accept [_ x]
      (f x))
    BiConsumer
    (accept [_ x y]
      (f x y))))


(defn map->Event [m]
  {:name (:name m)
   :description (:description m)
   :date (str (:date m (LocalDateTime/now)))
   :type (str (:type m (EventType/CREATE)))})

(defn post-event [event]
  (let [url "http://localhost:8090/event"
        body (json/write-str (map->Event event))
        headers {"Content-Type" "application/json"}]
    (http/post url {:body body :headers headers})))

(defn query [tx-instant q]
  (let [url "http://localhost:8090/event/query"
        body (json/write-str {:txInstant tx-instant :query (str q)})
        headers {"Content-Type" "application/json"}]
    (http/post url {:body body :headers headers})))
(comment
(->> (range 100)
     (pmap #(post-event {:name (str "test" %)
                         :description (str (with-out-str (pp/cl-format true "~:R\n" %))
                                           " test")}))
     (map :body))

(-> (query 90 '{:find [n (count e)]
                :where [[e :event/description dc]
                        [e :event/name n]
                        [(some? (re-find #("test") dc))]]
                ;:order-by [[e :asc]]
                })
    :body
  (json/read-str)))