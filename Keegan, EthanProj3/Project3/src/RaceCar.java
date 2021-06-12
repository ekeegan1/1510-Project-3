/*(Racing Car) Write a program that simulates car racing, as shown in Figure 15.36a.
 * The car moves from left to right. When it hits the right end, it restarts from the 
 * left and continues the same process. You can use a timer to control animation. 
 * Redraw the car with new base coordinates (x, y), as shown in Figure 15.36b. 
 * Also let the user pause/resume the animation with a button press/release and 
 * increase/decrease the car speed by pressing the up and down arrow keys. 
 * 
 * S21 - EECS 1510 Project 3
 * 
 * 
 * Author: Ethan Keegan
 * Date: 4/29/2021
 * Class: EECS 1510
 * 
 * Note From Author:
 * This project displays my knowledge on the last couple of weeks of class. It is mainly centered around working with Java FX. In this project, I work with code including setting up 
 * the stage, scene, etc. to display the program. Additionally, I show my understanding of the inverted graph that is used in Java FX by placing a car on the bottom of the window
 * using variables bounded to it. This requires a full understanding of the orientation of the co-ordinates and how to adjust the car with them. Throughout the code I break down the
 * thought process and the logic that when into it. 
 * 
 */
 

//Here are the needed import statements 
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


//The class, RaceCar, must extend Application
public class RaceCar extends Application 
{
	
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) 
  {       
                  
	 // Create a stage for all other parts
	 CarPane car = new CarPane();
	 
	 // Create a scene and place it in the stage
	 Scene scene = new Scene(car, 200, 200);
    
     // Set the stage title
     primaryStage.setTitle("RaceCar");
    
     // Place the scene in the stage
     primaryStage.setScene(scene);
    
     // Display the stage
     primaryStage.show();
    
    
    /*
     * Instead of putting the timer and animations for the car here, I opted to put in in the CarPane Class.
     * I felt that this was more simple and cleaned up the start method by organizing the code more.
     */
     
     //must call focus in order to perform certain animations
     car.requestFocus();
     
     //here are the listeners for the car to be moved if the stage is re-sized
     car.widthProperty().addListener(e -> car.setW(car.getWidth())); 	
     car.heightProperty().addListener(e -> car.setH(car.getHeight()));     
  }
  
   // The main method is only needed for the IDE with limited
   // JavaFX support. Not needed for running from the command line.
  public static void main(String[] args) 
  {
	 launch(args);
  }

  class CarPane extends Pane 
  {
	//here are all of the variables needed/used in this class  
	private int speed = 50;
	private double w = 200;
    private double h = 200;
    private double baseX = 0;
    private double baseY = h;
    private Circle c1 = new Circle(baseX + 15, baseY - 5, 5);
    private Circle c2 = new Circle(baseX + 35, baseY - 5, 5);
    private Rectangle carBody = new Rectangle(baseX, baseY - 20, 50, 10);
    private Polygon carTop = new Polygon(baseX + 10, baseY - 20, 
    									 baseX + 20, baseY - 30,
    									 baseX + 30, baseY - 30,
    									 baseX + 40, baseY - 20);
    
    
    //create an instance of KeyFrame in order to create a timeline
    KeyFrame kf = new KeyFrame(Duration.millis(150 - speed), e -> move()); 
    
    //set-up your timeline
    Timeline animation = new Timeline(new KeyFrame(Duration.millis(150 - speed), e -> move()));
    
    

    //this is the class that places the car on the instantiated pane from the start class and controls the movement
    public CarPane() 
    {
    	carBody.setFill(Color.GREEN);						//set car fill color
        carTop.setFill(Color.RED);							//set carTop fill color
        this.getChildren().addAll(c1, c2, carBody, carTop); //add car to CarPane	
    
    
        //start of animations
        //this code is from the listings in Dr. Hobbs' Demos
        animation.setCycleCount(Timeline.INDEFINITE); 		//set the cycle count to indefinite
        animation.play(); 					 		  		//start the animation
        
        setOnMousePressed(e -> {					  		//if the mouse is pressed,
          animation.pause();								//pause the animation.
        });
        setOnMouseReleased(e -> {							//if the mouse is released,
          animation.play();									//continue the animation.
        });
        
        setOnKeyPressed(e -> {
          if (e.getCode() == KeyCode.UP) 					//if there is an 'UP' keystroke,
          {
            animation.setRate(animation.getRate() + 1);		//increase the car speed.
          } 
          else if (e.getCode() == KeyCode.DOWN) 			//if there is a 'DOWN' keystroke,
          {       
            animation.setRate(animation.getRate() - 1);		//decrease the car speed.
          }	
        });
    }
    
    
    //this method moves the car forward. If it can't move forward anymore, it goes back to the beginning.
    public void move() 			//move method
    {    	
    	if (baseX > w) 			//if there is no room to move,
        {
          baseX = -50;			//move the car to the beginning.
        } 
        else					//if there is room,
        {
          baseX += 1;			//move the car forward.
        }
         setValues();			//update position
    }
    
    
    //this method updates the car with any new values passed in other methods
    public void setValues()				
    {
      //set x, y location of 
      //the wheels (c1 and c2).
      c1.setCenterX(baseX + 15);
      c1.setCenterY(baseY - 5);
      
      c2.setCenterX(baseX + 35);
      c2.setCenterY(baseY - 5);
      
      //set the location of the carBody
      //x, y coordinates.
      carBody.setX(baseX);
      carBody.setY(baseY - 20);
      
      //clear the carTop points, then
      //set the carTop points.
      carTop.getPoints().clear();
      carTop.getPoints().addAll(baseX + 10, baseY - 20, 
				 				baseX + 20, baseY - 30,
				 				baseX + 30, baseY - 30,
				 				baseX + 40, baseY - 20);
    }
    
    
    //this method sets the width
    public void setW(double w) 
    {
      this.w = w;
      setValues();
    }
    
    //this method sets the height
    public void setH(double h) 				
    {
      this.h = h;
      baseY = h;
      setValues();
    }
  }
}
