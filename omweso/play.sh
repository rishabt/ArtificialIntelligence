#!/bin/sh

PATH="./jar/omweso.jar:./example_submission/src/"

/usr/bin/javac -cp ./jar/omweso.jar:./example_submission/src/ ./example_submission/src/s260448450/s260448450Player.java ./example_submission/src/s260448450/mytools/MyTools.java

/usr/bin/java -cp jar/omweso.jar:./example_submission/src/ boardgame.Server -p 8123 -t 300000 -b omweso.CCBoard &
/usr/bin/java -cp jar/omweso.jar:./example_submission/src/ boardgame.Client omweso.CCRandomPlayer localhost 8123 &
/usr/bin/java -cp jar/omweso.jar:./example_submission/src/ boardgame.Client s260448450.s260448450Player localhost 8123 &
