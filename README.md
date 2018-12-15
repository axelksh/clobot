# clobot

FIXME: description

## Installation

Download from http://example.com/FIXME.

## Usage

FIXME: explanation

    $ java -jar clobot-0.1.0-standalone.jar [args]

## Options

FIXME: listing of options this app accepts.

## Examples

```
(ns clobot.example
  (:require [clobot.core :as bot]))

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

(def handlers {"/hello" greeting
               "no-command" no-command})

(def config {:token token
             :name bot-name
             :port 8080})

(defn -main
  []
  (bot/start-bot handlers config))
  ```

### Bugs

...

### Any Other Sections
### That You Think
### Might be Useful

## License

Copyright © 2018 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
