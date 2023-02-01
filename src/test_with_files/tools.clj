(ns test-with-files.tools
  (:require [clojure.java.io :as io])
  (:import java.nio.file.Files
           java.nio.file.attribute.FileAttribute))

(defn delete-file-recursively [f]
  (let [f (io/file f)]
    (if (.isDirectory f)
      (doseq [child (.listFiles f)]
        (delete-file-recursively child)))
    (io/delete-file f)))

(defmacro with-tmp-dir
  [path-var & body]
  (let [fb         (first body)
        opts       (when (and (map? fb)
                              (= "test-with-files.tools"
                                 (-> fb keys first namespace)))
                     fb)
        delete-dir (if (nil? opts)
                     true
                     (::delete-dir opts))]
    `(let [directory# (io/file (str (Files/createTempDirectory
                                      "temp" (make-array FileAttribute 0))))
           ~path-var (str directory#)
           result# (do ~@body)]
       (when ~delete-dir
         (delete-file-recursively directory#))
       result#)))

(defn create-files [parent-path path->contents]
  (doseq [[path contents] path->contents]
    (let [file (io/file parent-path path)]
      (.mkdirs (.getParentFile file))
      (spit file contents))))

(defmacro with-files [parent-path-var files & body]
  `(with-tmp-dir ~parent-path-var
                 (create-files ~parent-path-var ~(zipmap (take-nth 2 files)
                                                         (take-nth 2 (next files))))
                 ~@body))

(defmacro with-resources [parent-path-var files & body]
  `(let [~parent-path-var (str (java.util.UUID/randomUUID) "/")
         directory# (io/file "test" "resources" ~parent-path-var)]
     (.mkdirs directory#)
     (create-files directory# ~(zipmap (take-nth 2 files)
                                       (take-nth 2 (next files))))
     (let [result# (do ~@body)]
       (delete-file-recursively directory#)
       result#)))
