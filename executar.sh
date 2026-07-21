mkdir -p bin
javac -encoding UTF-8 -d bin $(find src -name "*.java")
java -cp bin mercado.app.Main