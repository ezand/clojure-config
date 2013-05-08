(ns clojure-config.core-test
  (:require [clojure.test :refer :all ]
            [clojure-config.core :refer :all ]))

(deftest a-test
  (testing "Reading configuration"
    (let [config (load-config "src/test/resources/settings.clj")]
      (is (not= nil config))
      (is (= "bar" (:foo config))))))
