package table;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;

public class sizeDice extends StackPane implements Comparable<sizeDice>
{

    public int value;
    public double sizeOfDice;
    public int d;

    public static Dimension size;
    public static ArrayList<sizeDice> sizeDices;
    public static ArrayList<sizeDice> zaznaczone;
    public static javafx.scene.control.Button[] buttons;


    //label i imageView musi być zadeklarowany na zewnątrz aby móc być łarwo modfikowanym
    Label onDice;
    ImageView imageView;

    //ustawia zmienne statyczne
    public static void setStatics(Dimension size1,ArrayList<sizeDice> sizeDices1,javafx.scene.control.Button[] buttons1,ArrayList<sizeDice> zaznaczone1)
    {
        size=size1;
        sizeDices=sizeDices1;
        buttons=buttons1;
        zaznaczone=zaznaczone1;
    }

    //resetuje ustawinia góźików
    public static void buttonReset()
    {
        if (zaznaczone.size() != 0)
        {
            buttons[0].setText("REROLL");
            buttons[2].setText("REMOVE");
        } else {
            buttons[0].setText("ROLL");
            buttons[2].setText("CLEAR");
        }
    }

    //trzyma wartośći losuje rozmiar ktory od teraz się nie zmieni
    sizeDice(int value,int d,String imagname)
    {
        this.value = value;
        this.sizeOfDice = Math.random() * 0.5 + 0.5;
        this.d = d;

        setAlignment(Pos.CENTER);

        int rotate = (int) (Math.random() * 365);
        this.setRotate(rotate);

        //stworzenie obrazu(trzeba roobić kolejne zewzględu na różne wielkości)
        imageView = Operations.imageViewgenerator(imagname, size.height * sizeOfDice * 0.1, size.height * sizeOfDice * 0.1);

        //stworzenie nadruku na obrazek
        onDice = new Label(Integer.toString(value));
        onDice.setTextFill(Color.WHITE);
        onDice.setUnderline(true);

        //dodanie elementów wszystkich do panelu
        getChildren().addAll(imageView, onDice);
        //DODAĆ TO W KLAISE I W INNYM MIEJSCU
        setOnMouseClicked
        (
            e ->
            {
                
                if (zaznaczone.contains(this))
                {
                    this.borderProperty().setValue(null);
                    zaznaczone.remove(this);
                }
                else
                {
                    this.setBorder
                    (
                        new Border
                        (
                            new BorderStroke
                            (
                                Color.AQUA,
                                BorderStrokeStyle.DASHED,
                                CornerRadii.EMPTY,
                                BorderWidths.DEFAULT
                            )
                        )
                    );
                    zaznaczone.add(this);
                }

                buttonReset();
                Dice.refreshAll();
            }
        );

        Operations.enlargeListenerforsizeDices(this);

        sizeDices.add(this);
    }


    //comareTo do sortowania kości
    @Override
    public int compareTo(sizeDice o)
    {
        return value-o.value;
    }
}
