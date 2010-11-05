(require ['com.twinql.clojure.http :as 'http])  ; clj-apache-http module. For installation instructions see: http://github.com/rnewman/clj-apache-http
(require ['clojure.contrib.base64 :as 'base64])
(require ['clojure.contrib.str-utils2 :as 'str-utils])
(require ['clojure.contrib.duck-streams :as 'streams])

(defn set-credentials [headers user password]
	(assoc headers
		"Authorization"
		(str "Basic " (base64/encode-str (str user ":" password)))))

(defn default-httpclient-params []
	(http/map->params {:handle-redirects false}))

(defn generate-activation-key 
	"Generates activation key and returns new activation key resource URI"
	[]
	(let [response (http/post "https://dev-registry.rutgers.edu/api/v1/people/NETID/HR01/activation" 
							 :headers-as :map 
							 :parameters (default-httpclient-params) 
							 :headers (set-credentials {} "rats" "RAT1234"))]
		(println "Generate Activation Key call status:" (:code response))
		(println "New Activation Key resource Location:" (first ((:headers response) "Location")))
		(println "New Activation Key value:" (str-utils/tail (first ((:headers response) "Location")) 8))
		(first ((:headers response) "Location"))))

(defn verify-and-invalidate-activation-key [activation-resource-uri]
	(println "\nProcessing the activation key..............\n")		
	
	;; Verify - HTTP GET
	 (let [response (http/get activation-resource-uri  
	 						 :parameters (default-httpclient-params)
	 						 :headers (set-credentials {} "rats" "RAT1234"))]						
	 	(if (== (:code response) 204) 
	 		(println "The activation code is valid!")
			;; this is the 'else' part 
			(do (streams/copy (.getContent (:content response)) *out*)
			  	 (println)
			 )))
	;; Now invalidate - HTTP DELETE	 
	(let [response (http/delete activation-resource-uri
							 :parameters (default-httpclient-params) 
							 :headers (set-credentials {} "rats" "RAT1234"))]
		(println "The invalidation of the key returned HTTP status code:" (:code response))))

;; Main call stack - 'generate-activation-key' and 'verify-and-invalidate-activation-key' function calls
(verify-and-invalidate-activation-key (generate-activation-key))
	


