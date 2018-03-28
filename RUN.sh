rm -r bin/
mkdir bin/
find ./src -name "*.java" > sources_list.txt
javac -d bin/ -classpath "${CLASSPATH}" @sources_list.txt
rm sources_list.txt
if [ -e input.txt ]
then
	java -classpath bin GUI.ParticleWindow < input.txt
else
	java -classpath bin GUI.ParticleWindow
fi