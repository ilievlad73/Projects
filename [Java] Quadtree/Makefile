JC = javac
JVM= java
RM = rm -rf
.SUFFIXES: .java .class
build:
	$(JC) src/*.java
MAIN = test

run: 
	$(JVM) -cp src $(MAIN)
clean:
	$(RM) src/*.class
