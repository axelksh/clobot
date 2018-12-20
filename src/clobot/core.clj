(ns clobot.core
  (:require [clobot.util :as util]
            [clobot.api :as api]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.core :refer [routes context POST GET]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [clj-http.client :as http]))


(defn- get-message
  "Returns message sent to the bot from request object"
  [req]
  (get-in req [:body "message"]))


(defn- get-no-command
  "Returns custom error handler if exists"
  [handlers]
  (get-in handlers ["no-command"]))


(defn- no-command-default
  "Sends error message to the chat if appropriate handler is not found"
  [command token chat-id]
  (api/text-message (str command " - Unknown command.") token chat-id))


(defn- bot-info
  "Returns basic information about the bot"
  [config]
  (fn [req]
    (http/get
      (str api/base-url (:token config) (:bot-info api/api-methods)))))


(defn- update-handler
  "Creates a function to resolve the updates got with web-hook
   and applies appropriate handler functions"
  [handlers config]
  (fn [req]
    (let [msg (get-message req)
          cmd (util/get-text msg)
          handler (get-in handlers [cmd])]
      (if handler
        (handler msg (util/get-chat-id msg))
        (:else (let [no-command (get-no-command handlers)]
                 (if no-command
                   (no-command msg (util/get-chat-id msg))
                   (:else (no-command-default
                            cmd
                            (:token config)
                            (util/get-chat-id msg))))))))))


(defn- make-routes
  "Creates endpoint to get and process updates from Telegram's servers"
  [handlers config]
  (routes
    (context (str "/" (:url config)) []
      (POST "/" [] (update-handler handlers config))
      (GET "/info" [] (bot-info config)))))


(defn- print-start-log
  [config]
  (println (str "Starting bot on port: " (:port config)))
  (println (str "Bot's token: " (:token config)))
  (println (format "Get brief bot info: HOST/%s/info" (:url config)))
  (println (str "Webhook URL: HOST/" (:url config))))


(defn start-bot
  "Starts a Ring web app on a given port"
  [handlers config]
  (print-start-log config)
  (run-jetty (-> (make-routes handlers config)
                 (wrap-json-body)
                 (wrap-json-response)) {:port (:port config)}))