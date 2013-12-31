# test-with-files

A Clojure library to easily write tests with files.

## Install

Add `test-with-files` to your `project.clj`, and include
`test/resources` in the dev resource path:

```cl
:profiles {:dev {:dependencies [[test-with-files "0.1.0"]]
                 :resource-paths ["test/resources"]}}
```

## Usage

#### with-files

Using with-files, you get a nice declarative way of creating files to
use in your tests.

```cl
(ns my-test
  (:require [test-with-files.core :refer [with-files public-dir]]
            [midje.sweet :refer :all]
            [clojure.java.io :as io]))

(with-files [["/abc.txt" "123"]
             ["/def.txt" "456"]]
  (fact
   (slurp (io/resource (str public-dir "/abc.txt"))) => "123"
   (slurp (io/resource (str public-dir "/def.txt"))) => "456"))
```

All files are deleted afterwards.

The files' last modified time is exposed as `*last-modified*`, in case
you need to check it.

#### with-tmp-dir

You can request a temporary directory to use with your tests, which is
cleaned up afterwards.

```cl
(ns my-test
  (:require [test-with-files.core :refer [with-tmp-dir tmp-dir]]
            [midje.sweet :refer :all]
            [clojure.java.io :as io]))

(with-tmp-dir
  (fact
   (io/as-file tmp-dir) => #(.exists %)))
```

## License

Copyright © 2013 Magnar Sveen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
