(ns test-with-files.core-test
  (:require [test-with-files.core :refer :all]
            [midje.sweet :refer :all]
            [clojure.java.io :as io]))

(fact
 "You can request a temporary directory to use with your tests, which
  is cleaned up afterwards."

 (io/as-file tmp-dir) => #(not (.exists %))

 (with-tmp-dir
   (io/as-file tmp-dir) => #(.exists %))

 (io/as-file tmp-dir) => #(not (.exists %)))

(fact
 "There's a nice declarative way of creating files to use in your tests.
  They're deleted afterwards as well."

 (with-files [["/abc.txt" "123"]
              ["/def.txt" "456"]]

   (slurp (io/resource (str public-dir "/abc.txt"))) => "123"
   (slurp (io/resource (str public-dir "/def.txt"))) => "456")

 (io/resource (str public-dir "/abc.txt")) => nil
 (io/resource (str public-dir "/def.txt")) => nil)

(fact
 "The files' last-modified time is exposed as *last-modified*, in case
  you need to check it."

 (defn file-last-modified [resource]
   (let [url-connection (.openConnection resource)
         modified (.getLastModified url-connection)]
     (.close (.getInputStream url-connection))
     modified))

 (with-files [["/abc.txt" "123"]]
   (file-last-modified (io/resource (str public-dir "/abc.txt"))) => *last-modified*))

(fact
 "You can pass in an empty list of files, without test-with-files blowing up."

 (with-files []
   :no-exceptions-please) => :no-exceptions-please)
