# Clobot

Clojure library for creating bots for Telegram messenger


## Installation

#### Leiningen
    [clobot "0.1.0"]
    
#### Clojure CLI/deps.edn
    clobot {:mvn/version "0.1.0"}
    
#### Maven
    <dependency>
       <groupId>clobot</groupId>
       <artifactId>clobot</artifactId>
       <version>0.1.0</version>
    </dependency>
    
#### Greadle
    compile 'clobot:clobot:0.1.0'


## Usage

### Full code example

```
(ns telegram-bot.example
  (:require [telegram-bot.core :as bot]))

(def token "PUT:YOUR:TOKEN:HERE")

(def bot-name "awesome-bot")

(defn greeting
  [msg]
  (bot/text-message
    "Hi there from the Clojure bot!"
    token
    (bot/get-chat-id msg)))

(defn no-command
  [msg]
  (bot/text-message
    (format "Sorry:( Command %s is not found..." (bot/get-command msg))
    token
    (bot/get-chat-id msg)))

(def handlers {"hello" greeting
               "no-command" no-command})

(def config {:token token
             :name bot-name
             :port 8080})

(defn -main
  []
  (bot/start-bot handlers config))
```


### Explanation


Import namespace
```
(ns clobot.example
  (:require [clobot.core :as bot]))
```

Create config map with your bot's token, port and bot's name
```
(def token "PUT:YOUR:TOKEN:HERE")

(def bot-name "awesome-bot")

(def config {:token token
             :name bot-name
             :port 8080})
```

Create handlers for the commands you whant your bot to perform.
```
(defn greeting
  [msg]
  (bot/text-message
    "Hi there from the Clojure bot!"
    token
    (bot/get-chat-id msg)))

(defn no-command
  [msg]
  (bot/text-message
    (format "Sorry:( Command %s is not found..." (bot/get-command msg))
    token
    (bot/get-chat-id msg)))

(def handlers {"/hello" greeting
               "no-command" no-command})
```

Every time a handler is applied the message object Telegram sent to the bot will passes to the handler and you can process it
```
(defn greeting
  [msg]
  ...
  ...
```

Send messages to Telegram

**(text-message _text_ _token_ _chat-id_)**
```
(bot/text-message
    "Hi there from the Clojure bot!"
    token
    (bot/get-chat-id msg)))
```


The library provides several useful functions to handle messages

**(get-command _msg_)** - returns command or text sent by user to the bot

**(get-chat-id _msg_)** - returns chat id


Start bot with the handlers and the config as parpams
```
(defn -main
  []
  (bot/start-bot handlers config))
```

Web application will start on given port with given bot's name as path. For example: 

**to get brief bot info:**
```
http://localhost:8080/awesome-bot/info   
```
**URL for webhook:**
```
http://localhost:8080/awesome-bot/  
```

**"no-command"** is the reserved key for the hanlder in case user puts a command not defined in your bot. If not provided will be used default error handler.
```
(def handlers {"/hello" greeting
               "no-command" no-command})
```


## License

Distributed under the MIT License
