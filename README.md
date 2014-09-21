# subtitles

A Clojure library to read and write the srt subtitle format

## Usage

conforms to the protocol set by [clojure/clojure.data.csv](https://github.com/clojure/data.csv)

    (require '[subtitles.core :as srt])

Get a lazy seq of maps from an srt file:

    (srt/read-subtitles (reader "filename"))

or directly from a string:

    (srt/read-subtitles "1\n01:00:00,000 --> 02:00:00,000\nspeech\n")
    => ({:counter 1
         :start (Period. 1 0 0 0)
         :end (Period. 2 0 0 0)
         :text "speech"})

or from anything that implements `Read-Subtitles-From`, really

Uses [org.joda.time.Period](http://joda-time.sourceforge.net/apidocs/org/joda/time/Period.html) to keep track of time periods

## Installation

Leiningen:

    [subtitles "0.1.1"]

Maven:

    <dependency>
      <groupId>subtitles</groupId>
      <artifactId>subtitles</artifactId>
      <version>0.1.1</version>
    </dependency>

## License

Copyright © 2014 Sidhant Godiwala (grinnbearit)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
