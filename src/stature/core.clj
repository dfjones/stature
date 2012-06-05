(ns stature.core)

(use 'lamina.core 'aleph.udp)

(def messages (ref []))
(def ^:const max-message-count 10)

(defn print-messages
  [key identity old new]
  (if (= (count new) 1)
    (println key old "=>" new)))

(add-watch messages :print-watch print-messages)

(defn append-message
  [message]
  (dosync
    (alter messages
      #(if (>= (count %1) max-message-count)
         [%2]
         (conj %1 %2))
      message)))


(defn get-message-text
  [message]
  (.trim (new String (.array (:message message)))))

(def actions (comp append-message get-message-text))

(defn start-socket
  []
  (let [channel @(udp-socket {:port 10000})]
    (map* actions channel)))

(start-socket)

