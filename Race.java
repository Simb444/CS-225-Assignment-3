import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class which displays the race that happens between four cars
 * @author Jovan Rodriguez
 */
public class Race extends Application{
    private Car car1, car2, car3, car4; //The four cars in the race
    private Button start; //The button that starts the race
    private Circle raceTrack; //The track all the cars will race on
    private Text racerInfo,//name, speed, and path of each car to be displayed during race
            winnerInfo,//announces the winner and times of all racers after completion
            stopA, stopB, stopC, stopD; //The four stops on the track

    //Default constructor for race class
    public Race(){

        //Initializing car objects with random integers for calculating speed
        car1 = new Car("Red", "A, B, C, D, A", Color.RED, ThreadLocalRandom.current()
                .nextInt(10, 30 + 1), ThreadLocalRandom.current()
                .nextInt(10, 30 + 1));
        car2 = new Car("Blue", "B, C, D, A, B", Color.BLUE, ThreadLocalRandom.current()
                .nextInt(10, 30 + 1), ThreadLocalRandom.current()
                .nextInt(10, 30 + 1));
        car3 = new Car("Orange", "C, D, A, B, C", Color.ORANGE, ThreadLocalRandom.current()
                .nextInt(10, 30 + 1), ThreadLocalRandom.current()
                .nextInt(10, 30 + 1));
        car4 = new Car("Purple", "D, A, B, C, D", Color.PURPLE, ThreadLocalRandom.current()
                .nextInt(10, 30 + 1), ThreadLocalRandom.current()
                .nextInt(10, 30 + 1));


        start = new Button("Click To Start Race");//initializing start button

        raceTrack = new Circle(150, Color.WHITE);//initializing circle that will represent the racetrack
        raceTrack.setStroke(Color.GREEN);
        raceTrack.setStrokeWidth(80);

        //initializing racerInfo with data on each racer to be displayed during the race
        racerInfo = new Text("Red's speed: " + car1.getSpeed() + " MPH\nRed's path: " + car1.getPath() +
                "\nBlue's speed: " + car2.getSpeed() + " MPH\nBlue's path: " + car2.getPath() +
                "\nOrange's speed: " + car3.getSpeed() + " MPH\nOrange's path: " + car3.getPath() +
                "\nPurple's speed: " + car4.getSpeed() + " MPH\nPurple's path: " + car4.getPath()
        );





        //Initializing the stops, setting their size and placement
        stopA = new Text("A");
        stopA.setX(330);
        stopA.setY(190);
        stopA.setScaleX(5);
        stopA.setScaleY(5);

        stopB = new Text("B");
        stopB.setX(185);
        stopB.setY(340);
        stopB.setScaleX(5);
        stopB.setScaleY(5);

        stopC = new Text("C");
        stopC.setX(40);
        stopC.setY(190);
        stopC.setScaleX(5);
        stopC.setScaleY(5);

        stopD = new Text("D");
        stopD.setX(185);
        stopD.setY(40);
        stopD.setScaleX(5);
        stopD.setScaleY(5);

    }

    //Overriding start method to set up elements of GUI
    @Override
    public void start(Stage primaryStage) {
        try { //Try statement for exception handling

            Scene startScreen = new Scene(start, 800, 800);
            primaryStage.setResizable(true);
            primaryStage.setScene(startScreen);
            primaryStage.show();

            start.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    HBox UI = new HBox(raceTrack, racerInfo);//Setting up the race track and info next to each other

                    //transition for every car on the track
                    PathTransition car1Transition = new PathTransition();
                    PathTransition car2Transition = new PathTransition();
                    PathTransition car3Transition = new PathTransition();
                    PathTransition car4Transition = new PathTransition();

                    //arrays for cars and their respective transitions
                    Car[] carList = {car1, car2, car3, car4};

                    PathTransition[] transitionList = {car1Transition, car2Transition, car3Transition,
                            car4Transition};

                    Circle circle = new Circle(150);//path by which each car will drive along
                    circle.setLayoutX(180);
                    circle.setLayoutY(180);

                    int rotation = 0;//for rotating circle so each car starts at its proper place


                    //Giving every car a transition that corresponds to its speed
                    for(int i = 0; i < 4; i++){
                        transitionList[i].setNode(carList[i].getCarBody());
                        transitionList[i].setDuration(Duration.seconds(70-carList[i].getSpeed()));
                        transitionList[i].setPath(circle);
                        transitionList[i].setCycleCount(1);
                        transitionList[i].play();
                        rotation += 90;
                        circle.setRotate(rotation);
                    }

                    //setting up elements to be displayed during the race
                    Group root = new Group(UI, car1.getCarBody(), car2.getCarBody(),car3.getCarBody(),
                            car4.getCarBody(), stopA, stopB, stopC, stopD);

                    root.setLayoutX(200);
                    root.setLayoutY(200);

                    Scene displayRace = new Scene(root, 800, 800);
                    Stage raceStage = new Stage();
                    raceStage.setScene(displayRace);
                    raceStage.setResizable(true);
                    raceStage.show();//displaying the race animation
                    primaryStage.close();//closing the start screen

                    //double and PathTransition value for finding the longest animation
                    double max = 0;
                    PathTransition slowest = null;

                    for(PathTransition p : transitionList){//for loop to find the longest animation
                        if(p.getDuration().toSeconds() > max){
                            max = p.getDuration().toSeconds();
                            slowest = p;
                        }
                    }

                    //when the longest animation is finished(all cars are finished driving), the results are shown
                    slowest.setOnFinished(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            Alert.AlertType type = Alert.AlertType.INFORMATION;
                            Alert alert = new Alert(type, "");

                            alert.initModality(Modality.APPLICATION_MODAL);

                            alert.getDialogPane().setContentText(winnerInfo.getText());

                            alert.getDialogPane().setHeaderText("Race Finished!");

                            alert.show(); //Displays an alert window for the user

                        }
                    });
                }
            });

        } catch (Exception e) { //Catch statement for exception handling

            e.printStackTrace();

        }
    }
    public static void main(String[] args) {
        launch(args);
    }

}
