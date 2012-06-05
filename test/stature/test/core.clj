(ns stature.test.core
  (:use [stature.core])
  (:use [clojure.test]))


(use 'lamina.core 'aleph.udp)

(deftest receive-all-messages
  (let [messages (map str (range 5))
        mset (set messages)
        socket @(udp-socket)]
    (doseq [m messages]
      (println "Sending" m)
      (enqueue socket {:host "localhost" :port 10000 :message m}))
    (while (< (count @stature.core/messages) (count mset))
      (. Thread (sleep 10)))
    (is (= (set @stature.core/messages) mset))))

