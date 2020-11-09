#CC = javac
CC = 'javac'
#EXEC = java
EXEC = 'java'

CLASS = bin

LIB = lib

SRC = src

SRCFILE := $(shell find $(SRC) -name *.java)

JDBC = mysql_con_java.jar

JM = jmetro.jar

MAIN = Main

FLAG = -Xlint:deprecation	

USER = mejane

PASSWORD = password




all : compile

clean :
	rm $(CLASS)/*.class

compile : 
	$(CC) $(FLAG) -d $(CLASS) -cp "$(JDBC):$(LIB)/$(JM)" $(SRCFILE)

run :
	$(EXEC) -cp "$(CLASS):$(LIB)/$(JDBC):$(LIB)/$(JM)" $(MAIN) $(USER) $(PASSWORD)


mysql :
	sudo service mysql start

initBDD :
	sudo mysql < ./init_bdd.sql
	sudo mysql < fill_bdd.sql



