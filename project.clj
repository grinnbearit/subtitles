(defproject subtitles "0.1.0"
  :description "Reads and Writes the srt format"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :url "https://github.com/grinnbearit/subtitles/"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.8.0"]]
  :profiles {:dev {:plugins [[lein-midje "3.1.3"]]
                   :dependencies [[midje "1.6.3"]]}})
