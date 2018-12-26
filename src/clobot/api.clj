(ns clobot.api
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))


(def base-url "https://api.telegram.org/bot")


(def api-methods
  {:sendMessage "/sendMessage"
   :sendDocument "/sendDocument"
   :sendPhoto "/sendPhoto"
   :sendVideo "/sendVideo"
   :sendAudio "/sendAudio"
   :sendSticker "/sendSticker"
   :bot-info "/getMe"})


(defn text-message
  "Sends text message to the chat"
  [text token chat-id]
  (http/post
    (str base-url token (:sendMessage api-methods))
    {:body (json/write-str {:text text :chat_id chat-id})
     :content-type :json
     :accept :json}))


(defn document-message
  [document token chat-id]
  (http/post
    (str base-url token (:sendDocument api-methods))
    {:multipart [{:name "chat_id" :content (str chat-id)}
                 {:name "document" :content document}]}))


(defn photo-message
  [photo token chat-id]
  (http/post
    (str base-url token (:sendPhoto api-methods))
    {:multipart [{:name "chat_id" :content (str chat-id)}
                 {:name "photo" :content photo}]}))


(defn video-message
  [video token chat-id]
  (http/post
    (str base-url token (:sendVideo api-methods))
    {:multipart [{:name "chat_id" :content (str chat-id)}
                 {:name "video" :content video}]}))


(defn audio-message
  [audio token chat-id]
  (http/post
    (str base-url token (:sendAudio api-methods))
    {:multipart [{:name "chat_id" :content (str chat-id)}
                 {:name "audio" :content audio}]}))


(defn sticker-message
  [sticker token chat-id]
  (http/post
    (str base-url token (:sendSticker api-methods))
    {:multipart [{:name "chat_id" :content (str chat-id)}
                 {:name "sticker" :content sticker}]}))
