JC = javac
JVM= java
RM = rm -rf
.SUFFIXES: .java .class
build:
	$(JC) src/*.java
MAIN = main

run: 
	$(JVM) -cp src $(MAIN)  ${ARGS}
clean:
	$(RM) src/*.class
