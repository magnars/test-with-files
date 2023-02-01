(ns test-with-files.tools-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is testing]]
            [test-with-files.tools :as sut]))

(deftest with-tmp-dir
  (testing "with defaults"
    (is (= (sut/with-tmp-dir tmp-dir
             (spit (io/file tmp-dir "foo.txt") "I'm here")
             (slurp (io/file tmp-dir "foo.txt")))
           "I'm here")))
  ;; Uncomment to test delete-dir false
  ;; not normally run b/c it leaves dirs behind when it's working correctly
  #_(testing "with delete-dir false"
      (is (= (sut/with-tmp-dir tmp-dir {::sut/delete-dir false}
               (spit (io/file tmp-dir "foo.txt") "I'm here")
               (println "tmp dir:" tmp-dir)
               (slurp (io/file tmp-dir "foo.txt")))
             "I'm here"))))

(deftest create-files
  (is (= (sut/with-tmp-dir tmp-dir
           (sut/create-files tmp-dir {"foo.txt" "I'm here"
                                      "bar/baz.txt" "Me too"})
           [(slurp (io/file tmp-dir "foo.txt"))
            (slurp (io/file tmp-dir "bar/baz.txt"))])
         ["I'm here" "Me too"])))

(deftest with-files
  (is (= (sut/with-files tmp-dir ["foo.txt" "I'm here"
                                  "bar/baz.txt" "Me too"]
           [(slurp (io/file tmp-dir "foo.txt"))
            (slurp (io/file tmp-dir "bar/baz.txt"))])
         ["I'm here" "Me too"])))

(deftest with-resources
  (is (= (sut/with-resources tmp-dir ["foo.txt" "I'm here"
                                      "bar/baz.txt" "Me too"]
           [(slurp (io/resource (str tmp-dir "foo.txt")))
            (slurp (io/resource (str tmp-dir "bar/baz.txt")))])
         ["I'm here" "Me too"])))
