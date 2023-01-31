(defproject com.magnars/test-with-files "2021-02-17"
  :description "Easily write tests with files."
  :url "http://github.com/magnars/test-with-files"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies []
  :profiles {:dev {:dependencies [[org.clojure/clojure "1.11.1"]
                                  [lambdaisland/kaocha "1.76.1230"]
                                  [kaocha-noyoda "2019-06-03"]]
                   :resource-paths ["test/resources"]}}
  :aliases {"kaocha" ["run" "-m" "kaocha.runner"]})
