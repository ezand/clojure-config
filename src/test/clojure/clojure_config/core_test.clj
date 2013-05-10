(ns clojure-config.core-test
  (:require [clojure.test :refer :all ]
            [clojure-config.core :refer :all :as c]
            [me.raynes.fs :as fs]))

(defn- copy-from-original []
  (let [base-path "src/test/resources/"] (fs/copy (str base-path "settings_source.clj") (str base-path "settings.clj"))))

(def ^:dynamic ^:private config-file (copy-from-original))

(deftest test-read-config
  (testing "Reading configuration"
    (let [conf (load-config config-file)]
      (is (not= nil conf))
      (is (= "bar" (:foo conf)))
      (is (= 2 (count (:y conf)))))))

(deftest test-get-value
  (testing "Getting value"
    (let [conf (load-config config-file)]
      (is (= "bar" (get-value config-file :foo ))))))

(deftest test-set-value-and-persist
  (testing "Updating configuration and persiting changes"
    (let [conf (load-config config-file)]
      (is (= 1 (:x conf)))
      (let [upd-conf (set-value config-file :x 2)]
        (is (= 2 (:x upd-conf)))))
    (let [conf (load-config config-file)]
      (is (= 2 (:x conf))))
    (alter-var-root (var config-file) (constantly (copy-from-original)))))

(deftest test-set-value-without-persist
  (testing "Updating configuration without persisting changes"
    (let [conf (load-config config-file)]
      (is (= 1 (:x conf)))
      (let [upd-conf (set-value config-file :x 2 :persist? false)]
        (is (= 2 (:x upd-conf)))))
    (let [conf (load-config config-file)]
      (is (= 1 (:x conf))))))

(deftest test-remove-key-and-persist
  (testing "Removing key and persisting changes"
    (let [conf (remove-key config-file :x)]
      (is (= (contains? conf :x ) false))
      (is (= nil (:x conf))))
    (let [conf (load-config config-file)]
      (is (= (contains? conf :x ) false)))
    (alter-var-root (var config-file) (constantly (copy-from-original)))))

(deftest test-remove-key-without-perist
  (testing "Removing key without persisting changes"
    (let [conf (remove-key config-file :x :persist? false)]
      (is (= (contains? conf :x ) false))
      (is (= nil (:x conf))))
    (let [conf (load-config config-file)]
      (is (= (contains? conf :x ) true)))
    (alter-var-root (var config-file) (constantly (copy-from-original)))))
