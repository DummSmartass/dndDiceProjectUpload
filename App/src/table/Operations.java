package table;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.Size;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;


import java.awt.*;

public class Operations
{
    //funkcja do zwracanie bacgroundu z pliku i wymiarów
    public static Background giveback (String imagename, double width,double height)
    {
        //utworzenie obrazu z nazwy wielkości możliwości resizowania go
        Image image = new Image(imagename,width,height,false,true);

        //imageView pozwala używać obrazu ustawia się jego wymiary(oraz to czy proporcje mają być achowane) i rotacje
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        //jest odzielna klasa bacground image którą trzeba poustawiać
        BackgroundImage bImg = new BackgroundImage(image,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);

        //zwracam Background wytworzony z backGround imagu
        return new Background(bImg);
    }



    //generate imageView
    public static ImageView imageViewgenerator (String imagename, double width,double height)
    {
        //utworzenie obrazu z nazwy wielkości możliwości resizowania go
        Image image = new Image(imagename,width,height,false,true);

        //imageView pozwala używać obrazu ustawia się jego wymiary(oraz to czy proporcje mają być achowane) i rotacje
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(false);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);

        //zwrócenie imageView
        return imageView;
    }



    //aplikacja do dostosowania góźników z listy aby pasowały do artstylu
    public static void changebuttons(Button[] buttons)
    {
        for (Button button:buttons)
        {
            //ustawienie przezroczystego bacgroundu, białego textu i  obramoówki wraz z jej obramówki
            button.setBackground(Background.fill(Color.rgb(255,255,255,0)));
            button.setStyle("-fx-text-fill: white;");
            button.setBorder
            (
                new Border(new BorderStroke
                    (Color.WHITE,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    BorderWidths.DEFAULT))
            );
        }
    }



    //aplikacja do dostosowania góźników z listy aby pasowały do artstylu
    public static void fillOportunities(Dimension dimensions, ArrayList<Integer> positions)
    {
        positions.clear();
        for (int i = 0; i < dimensions.height*dimensions.width; i++)
        {
            positions.add(i);
        }
    }



    public static void releasePulse(Button button)
    {
        button.setBackground(Background.fill(Color.rgb(255,255,255,1)));
        button.setStyle("-fx-text-fill: black;");
    }



    public static void clickedPulse(Button button)
    {
        button.setBackground(Background.fill(Color.rgb(255,255,255,0)));
        button.setStyle("-fx-text-fill: white;");
    }



    public static void enlargeListenerforsizeDices(sizeDice sd)
    {
        sd.setOnMouseEntered
        (
            event ->
            {
                sd.imageView.setFitWidth(sd.imageView.getFitWidth()*2);
                sd.imageView.setFitHeight(sd.imageView.getFitHeight()*2);

                sd.onDice.setFont(new Font(24));
            }
        );

        sd.setOnMouseExited
        (
            event ->
            {
                sd.imageView.setFitWidth(sd.imageView.getFitWidth()/2);
                sd.imageView.setFitHeight(sd.imageView.getFitHeight()/2);

                sd.onDice.setFont(new Font(12));
            }
        );
    }
}
