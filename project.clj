(defproject test-with-files "0.1.1"
  :description "Easily write tests with files."
  :url "http://github.com/magnars/test-with-files"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :profiles {:dev {:dependencies [[midje "1.8.3"]]
                   :plugins [[lein-midje "3.2"]]
                   :resource-paths ["test/resources"]}})
