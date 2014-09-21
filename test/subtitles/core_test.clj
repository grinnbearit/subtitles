(ns subtitles.core-test
  (:require [midje.sweet :refer :all]
            [subtitles.core :refer :all])
  (:import [java.io StringWriter]
           [org.joda.time Period]))


(def ^{:private true} sample
  "1
00:01:00,000 --> 00:02:00,000
- A minute passes

2
00:02:00,000 --> 00:03:00,000
- Another minute passes
- ...")


(facts
 "reading"
 (read-subtitles sample)
 => [{:counter 1
      :start (Period. 0 1 0 0)
      :end (Period. 0 2 0 0)
      :text ["- A minute passes"]}
     {:counter 2
      :start (Period. 0 2 0 0)
      :end (Period. 0 3 0 0)
      :text ["- Another minute passes"
             "- ..."]}])


(facts
 "reading-and-writing"
 (let [string-writer (StringWriter.)]
   (write-subtitles string-writer (read-subtitles sample))

   (.trim (str string-writer))
   => sample))
