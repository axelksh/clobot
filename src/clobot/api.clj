(ns clobot.api
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))


(def base-url "https://api.telegram.org/bot")


(def methods
  {:sendMessage "/sendMessage"
   :bot-info "/getMe"})


(defn- make-payload
  "Creates json payload for clj-http"
  [params-map]
  {:body (json/write-str params-map)
   :content-type :json
   :accept :json})


(defn text-message
  "Sends message from the bot to the chat"
  [text token chat-id]
  (http/post
    (str base-url token (:sendMessage methods))
    (make-payload {:text text
                   :chat_id chat-id})))
