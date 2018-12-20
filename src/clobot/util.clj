(ns clobot.util)


(defn get-text
  "Returns command sent to the bot from request object"
  [msg]
  (get-in msg ["text"]))


(defn get-chat-id
  "Returns chat id sent to the bot from request object"
  [msg]
  (get-in msg ["chat" "id"]))
