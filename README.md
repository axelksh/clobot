# Clobot

[![Clojars](https://img.shields.io/clojars/v/clobot.svg)](https://clojars.org/clobot)

Library for fast and simple creating [Telegram](https://telegram.org) bots with webhook written in [Clojure](http://clojure.org) programming language.


## Installation

#### Leiningen
    [clobot "0.1.1"]
    
#### Clojure CLI/deps.edn
    clobot {:mvn/version "0.1.1"}
    
#### Maven
    <dependency>
       <groupId>clobot</groupId>
       <artifactId>clobot</artifactId>
       <version>0.1.1</version>
    </dependency>
    
#### Greadle
    compile 'clobot:clobot:0.1.1'


## Usage

### Full code example

```Clojure
(ns clobot.example
  (:require [clobot.core :as bot]
            [clobot.api :as api]
            [clobot.util :as util]
            [clojure.java.io :as io]))


(def token "PUT:YOUR:TOKEN:HERE")


(defn say-hello
  [msg chat-id]
  (api/text
    "Hi there from the Clojure bot!"
    token chat-id))


(defn send-document
  [msg chat-id]
  (api/document
    (io/file "Document.txt")
    token chat-id))


(defn send-photo
  [msg chat-id]
  (api/photo
    (io/file "Photo.jpg")
    token chat-id))


(defn send-video
  [msg chat-id]
  (api/video
    (io/file "Video.avi")
    token chat-id))


(defn send-audio
  [msg chat-id]
  (api/audio
    (io/file "Audio.mp3")
    token chat-id))


(defn send-sticker
  [msg chat-id]
  (api/sticker
    (io/file "Sticker.png")
    token chat-id))


(defn no-command
  [msg chat-id]
  (api/text
    (format "Sorry:( Command %s is not found..." (util/get-text msg))
    token chat-id))


(def handlers {"hello" say-hello
               "document" send-document
               "photo" send-photo
               "video" send-video
               "audio" send-audio
               "sticker" send-sticker
               "no-command" no-command})


(def config {:token token
             :port  8080})


(defn -main
  []
  (bot/start-bot handlers config))
```


### Explanation


Import namespace
```Clojure
(ns clobot.example
  (:require [clobot.core :as bot]
            [clobot.api :as api]
            [clobot.util :as util]
            [clojure.java.io :as io]))
```

Create config map with your bot's token and port
```Clojure
(def token "PUT:YOUR:TOKEN:HERE")


(def config {:token token
             :port 8080})
```

Create handlers for the commands you whant your bot to perform.
```Clojure
;; Sends text message to the chat
(defn say-hello
  [msg chat-id]
  (api/text
    "Hi there from the Clojure bot!"
    token chat-id))


;; Sends document to the chat
(defn document
  [msg chat-id]
  (api/document
    (io/file "Document.txt")
    token chat-id))


;; Sends document to the chat
(defn send-photo
  [msg chat-id]
  (api/photo
    (io/file "Photo.jpg")
    token chat-id))


;; Sends video to the chat
(defn send-video
  [msg chat-id]
  (api/video
    (io/file "Video.avi")
    token chat-id))


;; Sends audio to the chat
(defn send-audio
  [msg chat-id]
  (api/audio
    (io/file "Audio.mp3")
    token chat-id))


;; Sends sticker to the chat
(defn send-sticker
  [msg chat-id]
  (api/sticker
    (io/file "Sticker.png")
    token chat-id))


;; Sends bot's response message in case command user sends is not recognized
;; "no-command" is reserved name for comands error.
;; If not set will be used default handler
(defn no-command
  [msg chat-id]
  (api/text
    (format "Sorry:( Command %s is not found..." (util/get-text msg))
    token chat-id))
    

;; Create map with command as key and handler you whant to handles this command as values
(def handlers {"hello" say-hello
               "document" send-document
               "photo" send-photo
               "video" send-video
               "audio" send-audio
               "sticker" send-sticker
               "no-command" no-command})
```

Every time a handler is applied chat id and [message object](https://core.telegram.org/bots/api#message) Telegram sent to the bot will passes to the handler so you can process it
```Clojure
(defn greeting
  [msg chat-id]
  ...
  ...
```
Util function to retrieve text from the [message object](https://core.telegram.org/bots/api#message) Telegram sent to the bot
```Clojure
(util/get-text msg)
```

Start bot with the handlers and the config as parpams
```Clojure
(defn -main
  []
  (bot/start-bot handlers config))
```

Web application will start on given port. For example: 

**to get brief bot info:**
```
http://localhost:8080/info   
```
**URL for webhook:**
```
http://localhost:8080/  
```


## License

Distributed under the MIT License
