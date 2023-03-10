package edu.wpi.teamb;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Bapp extends Application {
  public static ImageView lowerlevel;
  public static ImageView groundfloor;
  public static ImageView lowerlevel2;
  public static ImageView firstfloor;
  public static ImageView secondfloor;
  public static ImageView thirdfloor;
  public static final Color blue = Color.valueOf("#21357E");
  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static StackPane stackPane;
  @Setter @Getter private static BorderPane rootPane;

  @Override
  public void start(Stage primaryStage) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("views/Navigation/Root.fxml"));
    root.setId("home");
    Scene scene = new Scene(root, 1200, 650);
    scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
    scene
        .getStylesheets()
        .add(
            "https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,400;0,500;0,600;0,700;1,400;1,700&display=swap");

    Bapp.primaryStage = primaryStage;
    Bapp.stackPane = (StackPane) root;
    Bapp.rootPane = (BorderPane) stackPane.getChildren().get(0);

    Image icon = new Image(this.getClass().getResource("/media/icon.png").toString());
    primaryStage.getIcons().add(icon);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Brigham and Women's Hospital");
    primaryStage.show();
    DBSession.refreshAll();

    lowerlevel =
        new ImageView(Bapp.class.getResource("/media/Maps/00_thelowerlevel1.png").toExternalForm());
    groundfloor =
        new ImageView(Bapp.class.getResource("/media/Maps/00_thegroundfloor.png").toExternalForm());
    lowerlevel2 =
        new ImageView(Bapp.class.getResource("/media/Maps/00_thelowerlevel2.png").toExternalForm());
    firstfloor =
        new ImageView(Bapp.class.getResource("/media/Maps/01_thefirstfloor.png").toExternalForm());
    secondfloor =
        new ImageView(Bapp.class.getResource("/media/Maps/02_thesecondfloor.png").toExternalForm());
    thirdfloor =
        new ImageView(Bapp.class.getResource("/media/Maps/03_thethirdfloor.png").toExternalForm());

    Navigation.navigate(Screen.KIOSK_VIEW);
  }
}
