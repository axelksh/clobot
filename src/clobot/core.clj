(ns clobot.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [routes context POST GET]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [clj-http.client :as http]
            [clojure.data.json :as json]
            [clobot.util :as bot-util]))

(def telegram-api-host "https://api.telegram.org/bot")

(def telegram-api-methods
  {:sendMessage "sendMessage"
   :bot-info "getMe"})

(defn make-url
  "Creates full Telegram api url"
  [token method]
  (str telegram-api-host (format "%s/%s" token method)))

(defn make-payload
  "Creates json payload for clj-http"
  [params-map]
  {:body (json/write-str params-map)
   :content-type :json
   :accept :json})

(defn get-message
  "Returns message sent to the bot from request object"
  [req]
  (get-in req [:body "message"]))

(defn get-no-command
  "Returns custom error handler if exists"
  [handlers]
  (get-in handlers ["no-command"]))

(defn text-message
  "Sends message from the bot to the chat"
  [text token chat-id]
  (http/post
    (make-url token (:sendMessage telegram-api-methods))
    (make-payload {:text text
                   :chat_id chat-id})))

(defn no-command-default
  "Sends error message to the chat if appropriate handler is not found"
  [command token chat-id]
  (text-message (str command " - Unknown command.") token chat-id))

(defn bot-info
  "Returns basic information about the bot"
  [config]
  (fn [req]
    (http/get
      (make-url (:token config)
                (:bot-info telegram-api-methods)))))

(defn update-handler
  "Creates a function to resolve the updates got with web-hook
   and applies appropriate handler functions"
  [handlers config]
  (fn [req]
    (let [msg (get-message req)
          cmd (bot-util/get-message msg)
          handler (get-in handlers [cmd])]
      (if handler
        (handler msg)
        (:else (let [no-command (get-no-command handlers)]
                 (if no-command
                   (no-command msg)
                   (:else (no-command-default
                            cmd
                            (:token config)
                            (bot-util/get-chat-id msg))))))))))

(defn make-end-point
  "Creates endpoint to get and process updates from Telegram's servers"
  [handlers config]
  (routes
    (context (str "/" (:name config)) []
      (POST "/" [] (update-handler handlers config))
      (GET "/info" [] (bot-info config)))))

(defn print-start-log
  [config]
  (println (str "Starting bot on port: " (:port config)))
  (println (str "Bot's token: " (:token config)))
  (println (format "Get brief bot info: HOST/%s/info" (:name config)))
  (println (str "Webhook URL: HOST/" (:name config))))

(defn start-bot
  "Starts a Ring web app on a given port"
  [handlers config]
  (print-start-log config)
  (run-jetty (-> (make-end-point handlers config)
                 (wrap-json-body)
                 (wrap-json-response)) {:port (:port config)}))