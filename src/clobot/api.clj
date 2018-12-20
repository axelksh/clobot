(ns clobot.api
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))


(def base-url "https://api.telegram.org/bot")


(def api-methods
  {:sendMessage "/sendMessage"
   :sendPhoto "/sendPhoto"
   :bot-info "/getMe"})


(defn text-message
  "Sends text message to the chat"
  [text token chat-id]
  (http/post
    (str base-url token (:sendMessage api-methods))
    {:body (json/write-str {:text text :chat_id chat-id})
     :content-type :json
     :accept :json}))

