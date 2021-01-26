import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.beans.binding.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.Priority;




public class Test extends BounceBallSlider{
    public static void main(String[] args) throws Exception{
      Application.launch(args);
      }
}

class ButtonDemo extends Application {
  private static final String MEDIA_URL = "http://www.unf.edu/~n00798593/CoolResources/pingpong.mp4";
  protected Text text = new Text(50, 200, "Adrian Santos");
  boolean paused = true;

  protected BorderPane getPane() {
    //Main Border Pane
    BorderPane pane =  new BorderPane();

    //Hbox which contains Up and Down Buttons
    HBox paneForButtons = new HBox(20);
    Button btUp = new Button("Up",
      new ImageView("http://www.unf.edu/~n00798593/CoolResources/up.png"));
    Button btDown = new Button("Down",
      new ImageView("http://www.unf.edu/~n00798593/CoolResources/down.png"));
    paneForButtons.getChildren().addAll(btUp, btDown);
    paneForButtons.setAlignment(Pos.CENTER);
    paneForButtons.setStyle("-fx-border-width: 1px; -fx-border-color: gray");

    //Put the buttons on the bottom of the pane
    pane.setBottom(paneForButtons);

    //Control or actions for the up and down buttons which moves the text: Adrian Santos
    btUp.setOnAction(e -> text.setY(text.getY() - 10));
    btDown.setOnAction(e -> text.setY(text.getY() + 10));

    //Put the name inside a normal pane
    Pane paneForText = new Pane();
    paneForText.getChildren().add(text);

    //Separator instead of padding
    String topStringTest = "                                 ";
    Label lblTop = new Label (topStringTest);

    //Media Objects
    Media media = new Media(MEDIA_URL);
    MediaPlayer mediaPlayer = new MediaPlayer(media);
    MediaView mediaView = new MediaView(mediaPlayer);

    //Web Links for the Play, Pause and Rewind Glyphicons
    String playString = "http://www.unf.edu/~n00798593/CoolResources/play.png";
    String pauseString = "http://www.unf.edu/~n00798593/CoolResources/pause.png";
    String rewindString = "http://www.unf.edu/~n00798593/CoolResources/rewind.png";

    //Image View Class in order to view the Pictures that are in my studetn website
    ImageView imgPlayButton = new ImageView(playString);
    ImageView imgPauseButton = new ImageView(pauseString);
    ImageView imgRewindButton = new ImageView(rewindString);

    //Generate a play button. Pause button will also be inside here.
    Button playButton = new Button("", imgPlayButton);

    //If else for Image Play and Pause Button
    playButton.setOnAction(e -> {
      if(paused){
        mediaPlayer.play();
        playButton.setGraphic(imgPauseButton);
      }
      else{
        mediaPlayer.pause();
        playButton.setGraphic(imgPlayButton);
      }
      paused = !paused;
    });

    //Rewind Button
    Button rewindButton = new Button("", imgRewindButton);
    rewindButton.setOnAction(e -> mediaPlayer.seek(Duration.ZERO));

    //Slider
    Slider slVolume = new Slider();
    slVolume.setPrefWidth(150);
    slVolume.setMaxWidth(Region.USE_PREF_SIZE);
    slVolume.setMinWidth(30);
    slVolume.setValue(50);
    mediaPlayer.volumeProperty().bind(
      slVolume.valueProperty().divide(100));

    //Control Box which contains the play button and rewind button
    HBox hBox = new HBox(10);
    hBox.setAlignment(Pos.CENTER);
    hBox.getChildren().addAll(playButton, rewindButton,
      new Label("Volume"), slVolume);

    //Both the video and the horizontal box should be in a border pane
    BorderPane pane2 = new BorderPane();
    pane2.setCenter(mediaView);
    pane2.setBottom(hBox);

    //Set the video height and width
    mediaView.setFitWidth(600);
    mediaView.setFitHeight(300);

    //Put the border pane inside a normal pane
    Pane paneForMedia = new Pane(pane2);

    //Central Grid Pane
    GridPane gridPaneCenter = new GridPane();
    gridPaneCenter.add(paneForText, 0, 0);
    gridPaneCenter.add(lblTop, 1, 0);
    gridPaneCenter.add(paneForMedia, 2, 0);
    //gridPaneCenter.setGridLinesVisible(true); //Tests gridline area

    //Fit the Grid Pane to get the Whole Center of the border panes
    RowConstraints row = new RowConstraints();
    row.setVgrow(Priority.ALWAYS);
    gridPaneCenter.getRowConstraints().add(row);

    ColumnConstraints col = new ColumnConstraints();
    col.setHgrow(Priority.ALWAYS);
    gridPaneCenter.getColumnConstraints().add(col);

    //Put the Grid Pane inside the main border pane
    pane.setCenter(gridPaneCenter);

    return pane;
  }

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {

    //Master Scale - Set the scale onto the whole stage
    GridPane gridPaneMain = new GridPane();//Main Grid Pane

    gridPaneMain.setMaxSize(1000,500);//Pane Size
    gridPaneMain.setMinSize(1000,500);

    StackPane root = new StackPane(gridPaneMain);//Main Stack Pane
    NumberBinding maxScale = Bindings.min(root.widthProperty().divide(1000),
    root.heightProperty().divide(500));

    gridPaneMain.scaleXProperty().bind(maxScale);
    gridPaneMain.scaleYProperty().bind(maxScale);

    //Put the BorderPane inside a grid in 0,0
    gridPaneMain.add(getPane(), 0,0);

    RowConstraints row = new RowConstraints();
    row.setVgrow(Priority.ALWAYS);
    gridPaneMain.getRowConstraints().add(row);

    ColumnConstraints col = new ColumnConstraints();
    col.setHgrow(Priority.ALWAYS);
    col.setPercentWidth(100.0);
    gridPaneMain.getColumnConstraints().add(col);


    // Create a scene and place it in the stage
    Scene scene = new Scene(root, 1000, 500);
    primaryStage.setTitle("Multiple Functions"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage

  }

}

class CheckBoxDemo extends ButtonDemo {
  @Override // Override the getPane() method in the super class
  protected BorderPane getPane() {
    BorderPane pane = super.getPane();

    //Sets the text to bold, italic or both
    Font fontBoldItalic = Font.font("Calibri",
      FontWeight.BOLD, FontPosture.ITALIC, 20);
    Font fontBold = Font.font("Calibri",
      FontWeight.BOLD, FontPosture.REGULAR, 20);
    Font fontItalic = Font.font("Calibri",
      FontWeight.NORMAL, FontPosture.ITALIC, 20);
    Font fontNormal = Font.font("Calibri",
      FontWeight.NORMAL, FontPosture.REGULAR, 20);

    text.setFont(fontNormal);

    //Sets the Check Boxes for each choice of font action
    VBox paneForCheckBoxes = new VBox(20);
    paneForCheckBoxes.setPadding(new Insets(5, 5, 5, 5));
    paneForCheckBoxes.setStyle("-fx-border-width: 1px; -fx-border-color: gray");
    CheckBox chkBold = new CheckBox("Bold");
    CheckBox chkItalic = new CheckBox("Italic");
    paneForCheckBoxes.getChildren().addAll(chkBold, chkItalic);
    pane.setLeft(paneForCheckBoxes);

    //Action event for bold, italic or both
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

class RadioButtonDemo extends CheckBoxDemo {
  @Override // Override the getPane() method in the super class
  protected BorderPane getPane() {
    BorderPane pane = super.getPane();

    //Make a Vertical Box for Radio buttons which changes the color of the text
    VBox paneForRadioButtons = new VBox(20);
    paneForRadioButtons.setPadding(new Insets(5, 5, 5, 5));
    paneForRadioButtons.setStyle
      ("-fx-border-width: 1px; -fx-border-color: gray");
    RadioButton rbMagenta = new RadioButton("Magenta");
    RadioButton rbPink = new RadioButton("Pink");
    RadioButton rbChocolate = new RadioButton("Chocolate");
    paneForRadioButtons.getChildren().addAll(rbMagenta, rbPink, rbChocolate);
    pane.setRight(paneForRadioButtons);

    //Actions for the Vertical box to change the name's color
    ToggleGroup group = new ToggleGroup();
    rbMagenta.setToggleGroup(group);
    rbPink.setToggleGroup(group);
    rbChocolate.setToggleGroup(group);

    rbMagenta.setOnAction(e -> {
      if (rbMagenta.isSelected()) {
        text.setFill(Color.MAGENTA);
      }
    });

    rbPink.setOnAction(e -> {
      if (rbPink.isSelected()) {
        text.setFill(Color.PINK);
      }
    });

    rbChocolate.setOnAction(e -> {
      if (rbChocolate.isSelected()) {
        text.setFill(Color.CHOCOLATE);
      }
    });

    return pane;
  }
}

class BounceBallSlider extends RadioButtonDemo {
  @Override // Override the getPane() method in the super class
  protected BorderPane getPane() {
    BorderPane pane = super.getPane();

    //Get the rectangle properties from the Rectangle Class at the bottom (Custom Class)
    RectanglePane rectanglePane = new RectanglePane();

    //Slider which fastens the bounce of the ball
    Slider slSpeed = new Slider();
    slSpeed.setMax(20);
    rectanglePane.rateProperty().bind(slSpeed.valueProperty());

    //Use a Border pane for the rectangle and the slider
    BorderPane pane1 = new BorderPane();
    pane1.setCenter(rectanglePane);
    pane1.setBottom(slSpeed);
    pane1.setStyle
      ("-fx-border-width: 1px; -fx-border-color: gray");
    pane1.setPrefSize(200,100);


    pane.setTop(pane1);

    return pane;
}

class RectanglePane extends Pane {
  public final double width = 50;
  public final double height = 20;
  private double x = width, y = height;
  private double dx = 1, dy = 1;
  private Rectangle rectangle = new Rectangle(x,y);
  private Timeline animation;

  //Make a rectangle with a bouncing action
  public RectanglePane() {
    rectangle.setFill(Color.RED); // Set ball color
    getChildren().add(rectangle); // Place a ball into this pane

     // Create an animation for moving the ball
    animation = new Timeline(
    new KeyFrame(Duration.millis(50), e -> moveRectangle()));
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.play(); // Start animation
    }

    public void play() {
      animation.play();
    }

    public void pause(){
      animation.pause();
    }

    public void increaseSpeed(){
      animation.setRate(animation.getRate()+ 0.1);
    }

    public void decreaseSpeed(){
      animation.setRate(
        animation.getRate() > 0? animation.getRate()-0.1: 0);
    }

    public DoubleProperty rateProperty(){
      return animation.rateProperty();

    }

    protected void moveRectangle(){
      if (x < width || x > getWidth()-width){
        dx *= -1;
      }

      if (y < height || y > getHeight()-height){
        dy *= -1;
      }

      x += dx;
      y += dy;

      rectangle.setX(x);
      rectangle.setY(y);

    }
}}