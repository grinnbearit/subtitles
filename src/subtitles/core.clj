(ns subtitles.core
  #^{:author "Sidhant Godiwala (grinnbearit)"}
  (:require [clojure.string :as str])
  (:import [java.io StringReader BufferedReader Reader]
           [org.joda.time Period]))


(defn- subtitles-read
  [reader]
  (letfn [(parse-timing [s]
            (let [[h m s ms] (str/split s #":|,")]
              (Period. (Integer/parseInt h)
                       (Integer/parseInt m)
                       (Integer/parseInt s)
                       (Integer/parseInt ms))))]

    (for [[ctr tmng txt] (->> (BufferedReader. reader)
                              (line-seq)
                              (filter seq)
                              (partition 3))
          :let [counter (Integer/parseInt (re-find #"\d+" ctr))
                [_ strt nd] (re-find #"([^\s]+) --> ([^\s]+)" tmng)
                start (parse-timing strt)
                end (parse-timing nd)]]

      {:counter counter
       :start start
       :end end
       :text txt})))


(defprotocol Read-Subtitles-From
  (read-subtitles-from [input]))


(extend-protocol Read-Subtitles-From
  String
  (read-subtitles-from [s]
    (subtitles-read (StringReader. s)))

  Reader
  (read-subtitles-from [reader]
    (subtitles-read reader)))


(defn read-subtitles
  "Reads subtitle data from srt format into a lazy-sequence of maps"
  [input]
  (read-subtitles-from input))


(defn write-subtitles
  "Writes subtitle data in srt format"
  [writer data]
  (letfn [(period->str [p]
            (format "%02d:%02d:%02d,%03d"
                    (.getHours p) (.getMinutes p) (.getSeconds p) (.getMillis p)))]

    (with-open [w writer]
      (doseq [section data]
        (.write w (format "%d\n%s --> %s\n%s\n\n"
                          (:counter section)
                          (period->str (:start section))
                          (period->str (:end section))
                          (:text section)))))))
