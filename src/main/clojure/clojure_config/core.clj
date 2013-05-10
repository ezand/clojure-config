(ns clojure-config.core)

(defn load-config
  [file] (let [file-content (slurp file)] (read-string file-content)))

(defn persist-config
  [file config] (spit file (str config)) config)

(defn get-value
  [file key] (-> key ((load-config file))))

(defn set-value
  [file key value & {:keys [persist?] :or {persist? true}}]
  (let [config (-> (load-config file) (assoc key value))]
    (if persist? (persist-config file config) config)))

(defn remove-key
  [file key & {:keys [persist?] :or {persist? true}}]
  (let [config (-> (load-config file) (dissoc key))]
    (if persist? (persist-config file config) config)))
