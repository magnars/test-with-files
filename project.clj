(defproject test-with-files "0.1.0"
  :description "Easily write tests with files."
  :url "http://github.com/magnars/test-with-files"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :profiles {:dev {:dependencies [[midje "1.5.0"]]
                   :plugins [[lein-midje "3.0.0"]]
                   :resource-paths ["test/resources"]}})
