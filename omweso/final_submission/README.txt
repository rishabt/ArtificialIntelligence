Given that PROVIDEDCODE = ./path/to/provided/omweso.jar
You can use the following UNIX based command line to compile the example submission:

javac -cp $PROVIDEDCODE:./src/ ./src/s260448450/s260448450Player.java

You can launch the client using this player by using the following UNIX based command line:

java -cp $PROVIDEDCODE:./src/ boardgame.Client s260448450.s260448450Player

Note that this command should also work with windows if you correct the classpath (what comes immediately after -cp). You might have to correct the file path format and replace ':' with ';'.
