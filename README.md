### 1. Application description
Swingy - is a text-based RPG based on the gameplay and conditions described
below. The program needs to follow the Model-View-Controller architecture and allow
switching between the console view and GUI view

####Main project requirements:
1) Respect the [Model-View-Controller design pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)
2) Automated build with Maven
3) Annotation based user input validation using [Hibernate validator](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#validator-gettingstarted)
4) A user’s heroes and their state will be preserved, when the user exits the game  in a relational database
5) Switch between console view and GUI view at runtime, without closing the
   game

> Detailed information see in [swingy.en.subject.pdf](files/swingy.en.subject.pdf)

---

### 2. Program requirements:
- Maven
- Java 8

---

### 3. Run program
1) Build executable jar package
```
mvn clean package
```
2) Run app in console or gui mode
```
java -jar swingy-1.0-SNAPSHOT-jar-with-dependencies.jar console
java -jar swingy-1.0-SNAPSHOT-jar-with-dependencies.jar gui
```

---

###4. Application screens:

####Console views are present below:
1) Hero create view
   ![Hero select view](files/create_hero_console.PNG)

2) Hero select view
   ![Hero select view](files/select_hero_console.PNG)

3) Game view
   ![Game view](files/game_console.PNG)

4) Fight and artefact dialog views
   ![Fight view](files/fight_console.PNG)


####GUI views are present below:
1) Hero create view
   ![Hero select view](files/create_hero_gui.PNG)

2) Hero select view
   ![Hero select view](files/select_hero_gui.PNG)

3) Game view
   ![Game view](files/game_gui.PNG)

4) Fight dialog view
   ![Fight view](files/fight_gui.PNG)

5) Artefact dialog view
   ![Fight view](files/fight_gui.PNG)

6) Artefact dialog view
   ![Fight view](files/artefact_gui.PNG)