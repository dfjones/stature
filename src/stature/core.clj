(ns stature.core)

(use 'lamina.core 'aleph.udp)

(defn handler [ch]
  (let [next-message (read-channel ch)]
    (println next-message)))

(let [channel @(udp-socket {:port 10000})]
  (receive-all channel #(println %)))


