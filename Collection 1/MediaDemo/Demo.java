import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;
import javafx.beans.binding.*;
import javafx.stage.Stage;

public class Demo extends Application
{   
   public static void main(String[] args)
    {
      Application.launch(args);
    }
	@Override
	public void start(Stage primaryStage)
   {  
       GridPane mainGrid = new GridPane();

       mainGrid.setMaxSize(1000,500);
       mainGrid.setMinSize(1000,500);
   
       StackPane root = new StackPane(mainGrid);
       NumberBinding maxScale = Bindings.min(root.widthProperty().divide(1000), root.heightProperty().divide(500));
   
       mainGrid.scaleXProperty().bind(maxScale);
       mainGrid.scaleYProperty().bind(maxScale);
   
       MediaDemo obj = new MediaDemo();
       mainGrid.add(obj.getPane(), 0,0);
   
       RowConstraints row = new RowConstraints();
       row.setVgrow(Priority.ALWAYS);
       mainGrid.getRowConstraints().add(row);
   
       ColumnConstraints col = new ColumnConstraints();
       col.setHgrow(Priority.ALWAYS);
       col.setPercentWidth(100);
       mainGrid.getColumnConstraints().add(col);

       Scene scene = new Scene(root, 1000, 500);
       primaryStage.setTitle("Assignment 7"); // Set the stage title
       primaryStage.setScene(scene); // Place the scene in the stage
       primaryStage.show(); // Display the stage
   }
   Text text = new Text(50, 50, "Shyam Rajendren"); 
   GridPane paneForCenterText = new GridPane();
   protected BorderPane getPane()
   {
       HBox paneForButtons = new HBox(20);
       Button btUp = new Button("Up");
       Button btDown = new Button("Down");   
       paneForButtons.getChildren().addAll(btUp, btDown);
       paneForButtons.setAlignment(Pos.CENTER);
       paneForButtons.setStyle("-fx-border-color: green");
   
       BorderPane pane = new BorderPane();
       pane.setBottom(paneForButtons);
              
       Pane paneForText = new Pane();
       paneForText.getChildren().add(text);
       
       paneForCenterText.add(paneForText,0,0);
       
       RowConstraints row = new RowConstraints();
       row.setVgrow(Priority.ALWAYS);
       paneForCenterText.getRowConstraints().add(row);

       ColumnConstraints col = new ColumnConstraints();
       col.setHgrow(Priority.ALWAYS);
       paneForCenterText.getColumnConstraints().add(col);
       pane.setCenter(paneForCenterText);
       
       btUp.setOnAction(e -> text.setY(text.getY() - 10));
       btDown.setOnAction(e -> text.setY(text.getY() + 10));
       
       return pane;
   }
}
   
class Checkbox extends n01185608
{
    @Override // Override the getPane() method in the super class
    protected BorderPane getPane() {
    BorderPane pane = super.getPane();

    Font fontBoldItalic = Font.font("Calibri",FontWeight.BOLD, FontPosture.ITALIC, 20);
    Font fontBold = Font.font("Calibri",FontWeight.BOLD, FontPosture.REGULAR, 20);
    Font fontItalic = Font.font("Calibri",FontWeight.NORMAL, FontPosture.ITALIC, 20);
    Font fontNormal = Font.font("Calibri",FontWeight.NORMAL, FontPosture.REGULAR, 20);
    
    text.setFont(fontNormal);
    
    VBox paneForCheckBoxes = new VBox(20);
    paneForCheckBoxes.setPadding(new Insets(5, 5, 5, 5)); 
    paneForCheckBoxes.setStyle("-fx-border-color: green");
    CheckBox chkBold = new CheckBox("Bold");
    CheckBox chkItalic = new CheckBox("Italic");
    paneForCheckBoxes.getChildren().addAll(chkBold, chkItalic);
    pane.setLeft(paneForCheckBoxes);

    EventHandler<ActionEvent> handler = e -> { 
      if (chkBold.isSelected() && chkItalic.isSelected()) {
        text.setFont(fontBoldItalic); // Both check boxes checked
      }
      else if (chkBold.isSelected()) {
        text.setFont(fontBold); // The Bold check box checked
      }
      else if (chkItalic.isSelected()) {
        text.setFont(fontItalic); // The Italic check box checked
      }      
      else {
        text.setFont(fontNormal); // Both check boxes unchecked
      }
    };
    
    chkBold.setOnAction(handler);
    chkItalic.setOnAction(handler);
    
    return pane; // Return a new pane
  }
}
class Radiobox extends Checkbox
{
    @Override // Override the getPane() method in the super class
    protected BorderPane getPane() {
    BorderPane pane = super.getPane();
    
    VBox paneForRadioButtons = new VBox(20);
    paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5)); 
    paneForRadioButtons.setStyle
      ("-fx-border-width: 2px; -fx-border-color: green");
    
    RadioButton rbGold = new RadioButton("Gold");
    RadioButton rbOrange = new RadioButton("Orange");
    RadioButton rbPurple = new RadioButton("Purple");
    paneForRadioButtons.getChildren().addAll(rbGold, rbOrange, rbPurple);
    pane.setRight(paneForRadioButtons);

    ToggleGroup group = new ToggleGroup();
    rbGold.setToggleGroup(group);
    rbOrange.setToggleGroup(group);
    rbPurple.setToggleGroup(group);
    
    rbGold.setOnAction(e -> {
      if (rbGold.isSelected()) {
        text.setFill(Color.GOLD);
      }
    });
    
    rbOrange.setOnAction(e -> {
      if (rbOrange.isSelected()) {
        text.setFill(Color.ORANGE);
      }
    });

    rbPurple.setOnAction(e -> {
      if (rbPurple.isSelected()) {
        text.setFill(Color.PURPLE);
      }
    });
    
    return pane;
  }
}
class BouncingRectangle extends Radiobox
{
   @Override
   protected BorderPane getPane()
   {
      BorderPane pane = super.getPane();
      RectanglePane ballPane = new RectanglePane();
      Slider slSpeed = new Slider();
      slSpeed.setMax(20);
      ballPane.rateProperty().bind(slSpeed.valueProperty());
       
      BorderPane pane1 = new BorderPane();
      pane1.setCenter(ballPane);
      pane1.setBottom(slSpeed);
      pane1.setPrefWidth(100);
      pane1.setPrefHeight(100);
      pane.setTop(pane1);
      
      return pane;
   }
}
class RectanglePane extends Pane
{
     public final double width = 50;
     public final double height = 20;
     private double x = width, y = height;
     private double dx = 1, dy = 1;
     private Rectangle rectangle = new Rectangle(x, y);
     private Timeline animation;
   
     public RectanglePane() {
       rectangle.setFill(Color.RED); // Set ball color
       getChildren().add(rectangle); // Place a ball into this pane
   
       // Create an animation for moving the ball
       animation = new Timeline(
         new KeyFrame(Duration.millis(50), e -> moveBall()));
       animation.setCycleCount(Timeline.INDEFINITE);
       animation.play(); // Start animation
     }
   
     public void play() {
       animation.play();
     }
   
     public void pause() {
       animation.pause();
     }
   
     public void increaseSpeed() {
       animation.setRate(animation.getRate() + 0.1);
     }
   
     public void decreaseSpeed() {
       animation.setRate(
         animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);
     }
   
     public DoubleProperty rateProperty() {
       return animation.rateProperty();
     }
     
     protected void moveBall() {
       // Check boundaries
       if (x < width || x > getWidth() - width) {
         dx *= -1; // Change ball move direction
       }
       if (y < height || y > getHeight() - height) {
         dy *= -1; // Change ball move direction
       }
   
       // Adjust ball position
       x += dx;
       y += dy;
       rectangle.setX(x);
       rectangle.setY(y);
     }
 }
class MediaDemo extends BouncingRectangle
{
  private static final String MEDIA_URL = 
    "http://www.unf.edu/~n01185608/2017-11-26-VIDEO-00066571.mp4";

  @Override 
  protected BorderPane getPane()
  {
    Media media = new Media(MEDIA_URL);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    MediaView mediaView = new MediaView(mediaPlayer);

    mediaView.setFitWidth(600);
    mediaView.setFitHeight(300);
    
    Button playButton = new Button(">");
    playButton.setOnAction(e -> {
      if (playButton.getText().equals(">")) {
        mediaPlayer.play();
        playButton.setText("||");
      } else {
        mediaPlayer.pause();
        playButton.setText(">");
      }
    });

    Button rewindButton = new Button("<<");
    rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));
    
    Slider slVolume = new Slider();
    slVolume.setPrefWidth(150);
    slVolume.setMaxWidth(Region.USE_PREF_SIZE);
    slVolume.setMinWidth(30);
    slVolume.setValue(50);
    mediaPlayer.volumeProperty().bind(
      slVolume.valueProperty().divide(100));

    HBox hBox = new HBox(10);
    hBox.setAlignment(Pos.CENTER);
    hBox.getChildren().addAll(playButton, rewindButton,
      new Label("Volume"), slVolume);

    BorderPane pane = super.getPane();
    BorderPane pane2 = new BorderPane();
    pane2.setCenter(mediaView);
    pane2.setBottom(hBox);
    
    Pane paneForMedia = new Pane(pane2);
    
    paneForCenterText.add(paneForMedia,1,0);
    pane.setCenter(paneForCenterText);
    
    return pane;
 
  }
}