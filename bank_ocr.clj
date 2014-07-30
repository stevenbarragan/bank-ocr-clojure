(use 'clojure.string)

(defn lines [input]
  ; break a block of OCR'd text into lines, removing the leading linebreak
  (rest
    (split input #"\n")))

(defn cols [line]
  ; breaks a string representing a line into a list of 3-string groups
  (let [list-of-chars-lists (partition 3 line)]
    (map
      (partial apply str)
        list-of-chars-lists)))

(defn parse-it [input]
  ; breaks a block of digits into a list of list of 3-string groups
  (map cols (lines input)))

(defn get-col [lines,n]
  ; get a column from the parsed input
  (apply str
    (map
      (fn [line] (nth line n))
        lines)))

(def value-map {
  " _ | ||_|" 0
  "     |  |" 1
  " _  _||_ " 2
  " _  _| _|" 3
  "   |_|  |" 4
  " _ |_  _|" 5
  " _ |_ |_|" 6
  " _   |  |" 7
  " _ |_||_|" 8
  " _ |_| _|" 9
  })

(def str-map {
  "AAAaaaAAA" \A
  "BBBbbbBBB" \B
  "CCCcccCCC" \C
  "DDDdddDDD" \D
  "EEEeeeEEE" \E
  "FFFfffFFF" \F
  "GGGgggGGG" \G
  "HHHhhhHHH" \H
  "IIIiiiIII" \I
  })

(defn value-of [digit]
  ; returns the numeric value given a 3x3 matrix of strings
  (str-map digit))

(defn read-digits [digits]
  ; reads a block of digits and returns a vector of the digits that were parsed
  (map value-of
    (map
      (partial get-col (parse-it digits))
        (range 9)
     )
       )
  )

(def one-through-nine
"
    _  _     _  _  _  _  _ 
  | _| _||_||_ |_   ||_||_|
  ||_  _|  | _||_|  ||_| _|")


(def abcd
"
AAABBBCCCDDDEEEFFFGGGHHHIII
aaabbbcccdddeeefffggghhhiii
AAABBBCCCDDDEEEFFFGGGHHHIII")

(apply str (read-digits abcd))
