package edu.wpi.teamb;

import edu.wpi.teamb.Database.DBSession;
import edu.wpi.teamb.Navigation.Navigation;
import edu.wpi.teamb.Navigation.Screen;
import java.io.IOException;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("views/Navigation/Root.fxml"));
    root.setId("home");
    Scene scene = new Scene(root, 1200, 650);
    scene.getStylesheets().addAll(this.getClass().getResource("/css/style.css").toExternalForm());
    scene
        .getStylesheets()
        .add("https://fonts.googleapis.com/css2?family=Nunito:wght@700&display=swap");

    Bapp.primaryStage = primaryStage;
    Bapp.rootPane = (BorderPane) root;

    Image icon = new Image(this.getClass().getResource("/media/icon.png").toString());
    primaryStage.getIcons().add(icon);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Brigham and Women's Hospital");
    primaryStage.show();
    Navigation.navigate(Screen.SIGN_IN);
    DBSession.refreshAll();

    final Task<Void> l =
        new Task<>() {
          @Override
          protected Void call() {
            lowerlevel =
                new ImageView(
                    Bapp.class.getResource("/media/Maps/00_thelowerlevel1.png").toExternalForm());
            groundfloor =
                new ImageView(
                    Bapp.class.getResource("/media/Maps/00_thegroundfloor.png").toExternalForm());
            lowerlevel2 =
                new ImageView(
                    Bapp.class.getResource("/media/Maps/00_thelowerlevel2.png").toExternalForm());
            firstfloor =
                new ImageView(
                    Bapp.class.getResource("/media/Maps/01_thefirstfloor.png").toExternalForm());
            secondfloor =
                new ImageView(
                    Bapp.class.getResource("/media/Maps/02_thesecondfloor.png").toExternalForm());
            thirdfloor =
                new ImageView(
                    Bapp.class.getResource("/media/Maps/03_thethirdfloor.png").toExternalForm());
            return null;
          }
        };
    new Thread(l).start();
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
