rm -r bin/
mkdir bin/
find ./src -name "*.java" > sources_list.txt
javac -d bin/ -classpath "${CLASSPATH}" @sources_list.txt
rm sources_list.txt
java -classpath bin GUI.Window
