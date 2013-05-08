(ns clojure-config.core-test
  (:require [clojure.test :refer :all ]
            [clojure-config.core :refer :all :as c]
            [me.raynes.fs :as fs]))

(def ^:private config-file
  (let [base-path "src/test/resources/"] (fs/copy (str base-path "settings_source.clj") (str base-path "settings.clj"))))

(prn config-file)

(deftest test-read-config
  (testing "Reading configuration"
    (let [conf (load-config config-file)]
      (is (not= nil conf))
      (is (= "bar" (:foo conf)))
      (is (= 2 (count (:y conf)))))))

(deftest test-read-value
  (testing "Reading value"
    (let [conf (load-config config-file)]
      (is (= "bar" (get-value config-file :foo ))))))

(deftest test-update-value
  (testing "Updating configuration"
    (let [conf (load-config config-file)]
      (is (= 1 (:x conf)))
      (let [upd-conf (set-value config-file :x 2)]
        (is (= 2 (:x upd-conf)))))))

(deftest test-remove-key
  (testing "Removing key"
    (let [conf (remove-key config-file :x )]
      (not (contains? conf :x ))
      (is (= nil (:x conf))))))
