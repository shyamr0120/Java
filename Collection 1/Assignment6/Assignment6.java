import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Assignment6 extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = buildRootPane();
		Scene scene = new Scene(root, 1000, 350);
      
		primaryStage.setTitle("Assignment 6"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
	
	private Pane buildRootPane() {
		GridPane rootPane = new GridPane();

		Pane clock = buildClockPane();
		clock.setPrefHeight(5000);
		clock.setPrefWidth(5000);
		
		Pane fan = buildFanPane();
		fan.setPrefHeight(5000);
		fan.setPrefWidth(5000);
		
		Pane hangman = buildHangmanPane();
		hangman.setPrefHeight(5000);
		hangman.setPrefWidth(5000);

		rootPane.setAlignment(Pos.CENTER);
		rootPane.add(clock, 1, 0);
		rootPane.add(fan, 2, 0);
		rootPane.add(hangman, 3, 0);

		return rootPane;
	}
	
	private Pane buildClockPane() {
		// Create a clock and a label
		Random rand = new Random();
		int hour = rand.nextInt(23) + 0;
		int minute = rand.nextInt(60) + 0;
		int second = rand.nextInt(60) + 0;

		Text name = new Text("Shyam Rajendren");

		ClockPane clock = new ClockPane(hour, minute, second);
		String timeString = clock.getHour() + ":" + clock.getMinute() + ":" + clock.getSecond();
		Label lblCurrentTime = new Label(timeString);

		StackPane stack = new StackPane();
		stack.getChildren().addAll(clock, name);
		
		// Place clock and label in border pane
		BorderPane pane = new BorderPane();
		pane.setCenter(stack);
		pane.setBottom(lblCurrentTime);
		BorderPane.setAlignment(lblCurrentTime, Pos.TOP_CENTER);
      
		return pane;
	}

	
	private Pane buildFanPane() {
		GridPane grid = new GridPane();
		FanPane fan00 = new FanPane();
		fan00.setPrefHeight(5000);
		fan00.setPrefWidth(5000);
		
		FanPane fan01 = new FanPane();
		fan01.setPrefHeight(5000);
		fan01.setPrefWidth(5000);
		
		FanPane fan10 = new FanPane();
		fan10.setPrefHeight(5000);
		fan10.setPrefWidth(5000);
		
		FanPane fan11 = new FanPane();
		fan11.setPrefHeight(5000);
		fan11.setPrefWidth(5000);	

		grid.setAlignment(Pos.CENTER);
		grid.add(fan00, 0, 0);
		grid.add(fan01, 0, 1);
		grid.add(fan10, 1, 0);
		grid.add(fan11, 1, 1);
      
		return grid;
	}
	
	private Pane buildHangmanPane() {
		Pane hangman = new HangmanPane();
		BorderPane border = new BorderPane();
		border.setCenter(hangman);
		return border;
	}
	
	
	class FanPane extends Pane {
		FanPane() {
			paintFan();
		}

		public void paintFan() {
			double xCenter = getWidth() / 2;
			double yCenter = getHeight() / 2;
			double radius = (Math.min(getWidth(), getHeight()) * 0.4);
			Circle circle = new Circle(xCenter, yCenter, radius);
			circle.setFill(Color.WHITE);
			circle.setStroke(Color.BLACK);

			getChildren().clear();
			getChildren().add(circle);

			for (int i = 60; i < 360; i = i + 90) {
				Arc arc = new Arc(getWidth() / 2, getHeight() / 2, radius * 0.90, radius * 0.90, i, -30);
				arc.setFill(Color.BLACK);
				arc.setType(ArcType.ROUND);
				getChildren().add(arc);
			}

		}

		@Override
		public void setWidth(double width) {
			super.setWidth(width);
			paintFan();
		}

		@Override
		public void setHeight(double height) {
			super.setHeight(height);
			paintFan();
		}
	}
	
	class ClockPane extends Pane {
		  private int hour;
		  private int minute;
		  private int second;
		 
		  /** Construct a default clock with the current time*/
		  public ClockPane() {
		    setCurrentTime();
		    paintClock();
		  }
		 
		  /** Construct a clock with specified hour, minute, and second */
		  public ClockPane(int hour, int minute, int second) {
		    setHour(hour);
		    setMinute(minute);
		    setSecond(second);
		    paintClock();
		  }
		 
		  /** Return hour */
		  public int getHour() {
		    return hour;
		  }
		 
		  /** Set a new hour */
		  public void setHour(int hour) {
		    this.hour = hour;
		    paintClock();
		  }
		 
		  /** Return minute */
		  public int getMinute() {
		    return minute;
		  }
		 
		  /** Set a new minute */
		  public void setMinute(int minute) {
		    this.minute = minute;
		  }
		 
		  /** Return second */
		  public int getSecond() {
		    return second;
		  }
		 
		  /** Set a new second */
		  public void setSecond(int second) {
		    this.second = second;
		    paintClock();
		  }
		 
		  /* Set the current time for the clock */
		  public void setCurrentTime() {
		    // Construct a calendar for the current date and time
		    Calendar calendar = new GregorianCalendar();
		 
		    // Set current hour, minute and second
		    this.hour = calendar.get(Calendar.HOUR_OF_DAY);
		    this.minute = calendar.get(Calendar.MINUTE);
		    this.second = calendar.get(Calendar.SECOND);
		  }
		 
		  /** Paint the clock */
		  private void paintClock() {
		    // Initialize clock parameters
		    double clockRadius = Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
		    double centerX = getWidth() / 2;
		    double centerY = getHeight() / 2;
		    // Draw circle
		    Circle circle = new Circle(centerX, centerY, clockRadius);
		    circle.setFill(Color.WHITE);
		    circle.setStroke(Color.BLACK);
		    Text t1 = new Text(centerX - 5, centerY - clockRadius + 12, "12");
		    Text t2 = new Text(centerX - clockRadius + 3, centerY + 5, "9");
		    Text t3 = new Text(centerX + clockRadius - 10, centerY + 3, "3");
		    Text t4 = new Text(centerX - 3, centerY + clockRadius - 3, "6");
		              
  		    // Draw second hand
		    double sLength = clockRadius * 0.8;
		    double secondX = centerX + sLength * Math.sin(second * (2 * Math.PI / 60));
		    double secondY = centerY - sLength * Math.cos(second * (2 * Math.PI / 60));
		    Line sLine = new Line(centerX, centerY, secondX, secondY);
		    sLine.setStroke(Color.RED);
		 
		    // Draw minute hand
		    double mLength = clockRadius * 0.65;
		    double xMinute = centerX + mLength * Math.sin(minute * (2 * Math.PI / 60));
		    double minuteY = centerY - mLength * Math.cos(minute * (2 * Math.PI / 60));
		    Line mLine = new Line(centerX, centerY, xMinute, minuteY);
		    mLine.setStroke(Color.BLUE);
		   
		    // Draw hour hand
		    double hLength = clockRadius * 0.5;
		    double hourX = centerX + hLength * Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
		    double hourY = centerY - hLength * Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
		    Line hLine = new Line(centerX, centerY, hourX, hourY);
		    hLine.setStroke(Color.GREEN);
		   
		    getChildren().clear(); 
		    getChildren().addAll(circle, t1, t2, t3, t4, sLine, mLine, hLine);
		  }
		 
		  @Override
		  public void setWidth(double width) {
		    super.setWidth(width);
		    paintClock();
		  }
		 
		  @Override
		  public void setHeight(double height) {
		    super.setHeight(height);
		    paintClock();
		  }
	}
	
	class HangmanPane extends Pane {
		HangmanPane() {
			paint();
		}
		
		private void paint() {
			Line line1 = new Line();
			line1.startXProperty().bind(layoutXProperty().add(10));
			line1.startYProperty().bind(layoutYProperty().add(10));
			line1.endXProperty().bind(layoutXProperty().add(getWidth()*0.5));
			line1.endYProperty().bind(layoutYProperty().add(10));
			
			Line line2 = new Line();
			line2.startXProperty().bind(layoutXProperty().add(10));
			line2.startYProperty().bind(layoutYProperty().add(10));
			line2.endXProperty().bind(layoutXProperty().add(10));
			line2.endYProperty().bind(layoutYProperty().add(getHeight()*0.9));
			
			Circle circle = new Circle();
			circle.centerXProperty().bind(layoutXProperty().add(getWidth()*0.5));
			circle.centerYProperty().bind(layoutYProperty().add(getHeight()*0.2));
			circle.radiusProperty().bind(layoutXProperty().add(getWidth()*0.1));
			circle.setFill(Color.WHITE);
			circle.setStroke(Color.BLACK);
			
			Line line3 = new Line();
			line3.startXProperty().bind(circle.centerXProperty());
			line3.startYProperty().bind(layoutYProperty().add(10));
			line3.endXProperty().bind(circle.centerXProperty());
			line3.endYProperty().bind(circle.centerYProperty().subtract(circle.getRadius()));
			
			Line line4 = new Line();	//body
			line4.startXProperty().bind(circle.centerXProperty());
			line4.startYProperty().bind(circle.centerYProperty().add(circle.getRadius()));
			line4.endXProperty().bind(circle.centerXProperty());
			line4.endYProperty().bind(line4.startYProperty().add(getHeight()*0.3));
			
			Line line5 = new Line();	//left arm
			line5.startXProperty().bind(circle.centerXProperty().subtract(getWidth()*0.05));
			line5.startYProperty().bind(line4.startYProperty().subtract(getHeight()*0.009));
			line5.endXProperty().bind(line5.startXProperty().subtract(getWidth()*0.1));
			line5.endYProperty().bind(line5.startYProperty().add(getHeight()*0.05));
			
			Line line6 = new Line();	//right arm
			line6.startXProperty().bind(circle.centerXProperty().add(getWidth()*0.05));
			line6.startYProperty().bind(line4.startYProperty().subtract(getHeight()*0.009));
			line6.endXProperty().bind(line5.startXProperty().add(getWidth()*0.2));
			line6.endYProperty().bind(line5.startYProperty().add(getHeight()*0.05));
			
			Line line7 = new Line();	//left leg
			line7.startXProperty().bind(line4.endXProperty());
			line7.startYProperty().bind(line4.endYProperty());
			line7.endXProperty().bind(line7.startXProperty().subtract(getWidth()*0.15));
			line7.endYProperty().bind(line7.startYProperty().add(getHeight()*0.09));
			
			Line line8 = new Line();	//right leg
			line8.startXProperty().bind(line4.endXProperty());
			line8.startYProperty().bind(line4.endYProperty());
			line8.endXProperty().bind(line7.startXProperty().add(getWidth()*0.15));
			line8.endYProperty().bind(line7.startYProperty().add(getHeight()*0.09));
			
			Arc arc = new Arc();
         arc.centerXProperty().bind(line2.endXProperty().add(getWidth()*0.005));
         arc.centerYProperty().bind(line2.endYProperty().add(getHeight()*0.10));
         arc.radiusXProperty().bind(layoutXProperty().add(getWidth()*0.15));
         arc.radiusYProperty().bind(layoutYProperty().add(getHeight()*0.15));
         arc.setStartAngle(30);
         arc.setLength(120);
         arc.setFill(Color.WHITE);
         arc.setStroke(Color.BLACK);
         
			getChildren().clear();
			getChildren().addAll(line1, line2, circle, line3, line4, line5, line6, line7, line8, arc);
		}
		
		  @Override
		  public void setWidth(double width) {
		    super.setWidth(width);
		    paint();
		  }
		 
		  @Override
		  public void setHeight(double height) {
		    super.setHeight(height);
		    paint();
		  }
	}
}