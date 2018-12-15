(defproject clobot "0.1.0"
  :description "Clobot is a library for creating bots for Telegram messenger"
  :url "https://github.com/axelksh/clobot"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.1"]
                 [ring "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "3.9.1"]]
  :main ^:skip-aot clobot.example
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
