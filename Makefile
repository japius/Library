#CC = javac
CC = '/mnt/c/Program Files (x86)/Java/jdk1.8/bin/javac.exe'
#EXEC = java
EXEC = '/mnt/c/Program Files (x86)/Java/jdk1.8/bin/java.exe'

CLASS = bin

LIB = lib

SRC = src

SRCFILE := $(shell find $(SRC) -name *.java)

JDBC = mysql_con_java.jar

MAIN = Main

FLAG = -Xlint:deprecation




all : compile

clean :
	rm $(CLASS)/*

compile : 
	$(CC) $(FLAG) -d $(CLASS) -cp $(JDBC) $(SRCFILE)

run :
	$(EXEC) -cp "$(CLASS);$(LIB)/$(JDBC)" $(MAIN)


mysql :
	sudo service mysql start

