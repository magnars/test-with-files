# test-with-files

A Clojure library to easily write tests with files.

## Install

Leiningen / Boot:

```clj
[com.magnars/test-with-files "2021-02-17"]
```

Clojure CLI/deps.edn:

```clj
com.magnars/test-with-files {:mvn/version "2021-02-17"}
```

## Usage

#### with-tmp-dir

You can request a temporary directory to use with your tests, which is
cleaned up afterwards.

```clj
(ns my-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]
            [test-with-files.tools :refer [with-tmp-dir]]))

(deftest with-tmp-dir-test
  (is (= (with-tmp-dir tmp-dir
           (spit (io/file tmp-dir "foo.txt") "I'm here")
           (slurp (io/file tmp-dir "foo.txt")))
         "I'm here")))
```

The first parameter `tmp-dir` passed to `with-tmp-dir` is a binding, exposing the
temporary directory path to be used in the body of the macro.

#### with-files

Using `with-files`, you get a nice declarative way of creating files to
use in your tests.

```clj
(ns my-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]
            [test-with-files.tools :refer [with-files]]))

(deftest with-files-test
  (is (= (with-files tmp-dir ["foo.txt" "I'm here"
                              "bar/baz.txt" "Me too"]
           [(slurp (io/file tmp-dir "foo.txt"))
            (slurp (io/file tmp-dir "bar/baz.txt"))])
         ["I'm here" "Me too"])))
```

Like `with-tmp-dir`, the first parameter passed to `with-files` is a binding,
exposing the temporary directory path to be used in the body of the macro.

The second parameter is a vector of relative file path and file content pairs,
inspired by the clojure.core `with-redefs` signature.

All files are deleted afterwards.

#### with-resources

If your code is looking for resources on the class path, instead of files on the
file system, you can use `with-resources` instead.

```clj
(ns my-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]
            [test-with-files.tools :refer [with-resources]]))

(deftest with-resources-test
  (is (= (with-resources tmp-dir ["foo.txt" "I'm here"
                                  "bar/baz.txt" "Me too"]
           [(slurp (io/resource (str tmp-dir "foo.txt")))
            (slurp (io/resource (str tmp-dir "bar/baz.txt")))])
         ["I'm here" "Me too"])))
```

The signature is the same. `tmp-dir` will now be bound to the relative path.

In order to use this, make sure that `test/resources` is on your resource path.
Using leiningen, that would be done like this:

```clj
:profiles {:dev {:resource-paths ["test/resources"]}}
```

You should probably also add a `.gitkeep` file to `test/resources` if
you have no other files in there - or it is not properly added to the
classpath when leiningen starts.

## Old version

There's an outdated version of this API to be found under
`test-with-files.core`. If you're stilling using that, the [README is here](old-readme.md).

## License

Copyright © 2021 Magnar Sveen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
