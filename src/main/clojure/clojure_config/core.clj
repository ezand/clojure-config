(ns clojure-config.core)

(defn load-config
  [file] (let [file-content (slurp file)] (read-string file-content)))

(defn get-value
  [file key] (-> key ((load-config file))))

(defn set-value
  [file key value] (-> (load-config file) (assoc key value)))

(defn remove-key
  [file key] (-> (load-config file) (dissoc key)))
