package table;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.Size;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;


public class Dice extends HBox
{

    //statyczne zmienne dzielone pomiędzy wszystkimi obiektami takie jak ich destynacja, wielkość ekranu do dyspozycji, oraz lista dostępnych miejsc na kostki
    static GridPane gp;
    static Dimension size;
    //static boolean allowed[][];
    static Dimension dimensions;
    //przechowuje dostepne pozycje przy rozkładaniu kośic
    static ArrayList<Integer> positions;
    //przechowuje wszystkie kości do operacji statycznych
    public static ArrayList<Dice> allDices;

    static ArrayList<sizeDice> zaznaczone = new ArrayList<sizeDice>();
    static Button[] externButtons;

    //d reprezentuje ilość stron kostki a string przekazuje reprezentujące je grafike
    int d;
    String diceImage;

    // lista do przechowania istniejących kości
    public ArrayList<sizeDice> dices = new ArrayList<sizeDice>();

    // zmienne przechowują odpowiadające ich nazwą wartści
    int sum = 0;
    int min = d;
    int max = 0;

    //przechowuje ilość kości do rzutu
    int amount = 0;
    //int amount2 = 0;

    //ondice przechowuje wartośc wyświetlaną na kostce głównej
    Label onDice = new Label();

    //wszystkie góźiki muszą być zadeklarowane wcześniej
    Button amountbutton = new Button("AMOUNT");
    Button sumButton = new Button("SUM");
    Button minButton = new Button("MIN");
    Button maxButton = new Button("MAX");

    Button[] buttons = new Button[]{amountbutton,sumButton,minButton,maxButton};
    int chosenButon = 4;



    public static void refreshAll()
    {
        for (Dice dice:allDices)
        {
            dice.refreshValues();
        }
    } 



    //rollOne służy do wylosowania pojedyńczej wartości kostki
    int rollOne()
    {
        Random r = new Random();
        return r.nextInt(d) + 1;
    }



    //roll służy do rzucenia wszystkimi określoną prze amount ilością kości
    void roll()
    {
        // wyczyszczenie listy z zapisanych kości
        //żucanie określoną ilością kości
        for (int i = 0; i < amount; i++)
        {
            //wylosowanie wartości wyrzucanej kości oi dodanie jej do listy żuconych kości
            int rolled = rollOne();
            dices.add(new sizeDice(rolled,d,diceImage));
        }

        //refreshing vales and displaying
        refreshValues();
        display();
    }



    //refreshValues odśweierza zmienne min, max i sumt=y
    void refreshValues()
    {
        if(zaznaczone.size()==0)
        {
            //resetowanie zmiennych do wyświetlania
            sum = 0;
            min = d;
            max = 0;

            for (sizeDice diceValue : dices)
            {
                sum += diceValue.value;

                if (diceValue.value < min)
                    min = diceValue.value;

                if (diceValue.value > max)
                    max = diceValue.value;
            }
        }
        else
        {
            //resetowanie zmiennych do wyświetlania
            sum = 0;
            min = d;
            max = 0;

            for (sizeDice diceValue: zaznaczone)
            {
                if(dices.contains(diceValue))
                {
                    sum += diceValue.value;

                    if (diceValue.value < min)
                        min = diceValue.value;

                    if (diceValue.value > max)
                        max = diceValue.value;
                }
            }

        }

        //odświerzeniie góźików
        if (chosenButon != 4)
        {
            buttons[chosenButon].fire();
        }
    }



    //displays wyświetla wszystkie kostki
    void display ()
    {

        int x;
        int y;

        //iteracja po elementach
        for (sizeDice sd:dices)
        {
            if (positions.size()!=0)
            {
                //wylosowanie elementu z listy reprezentującego pozycje i usunięcie go z dostępnych pozycji
                Random rand = new Random();
                int randomElement = positions.get(rand.nextInt(positions.size()));
                positions.remove(Integer.valueOf(randomElement));

                x = randomElement%dimensions.width;
                y = randomElement/dimensions.width;

                //dodanie panelu do filcu w wylosowanym randomowym miejscu
                gp.add(sd, x, y);


            }
            else
                break;
        }

/*        //odświerzeniie góźików
        if (chosenButon!=4)
        {
            buttons[chosenButon].fire();
        }*/
    }



    //displayInOrder wyświetla elementy posortowane wielkością od lewej do prawej i z góry na dół
    int displayInOrder (int element)
    {
        int limit = dimensions.height*dimensions.width-1;
        Collections.sort(dices);
        Collections.reverse(dices);


            for (sizeDice sd : dices)
            {
                //zwracanie bez wykonania akcji w przypadku osiągnięcia limitu
                if (element > limit)
                    return element;

                //wyprosotwoanie kosći
                sd.setRotate(0);

                //dodawnie odpowiednich miejscach gradów
                gp.add(sd, element % (int) (dimensions.width), (int) (element / dimensions.width));
                positions.remove(Integer.valueOf(element));

                element++;
            }


        return element;
    }



    //remove usówa zaznaczone elementy
    void remove(ArrayList<sizeDice> toRemove)
    {
        //backup na wypadek podania listy dice
        ArrayList<sizeDice> toRemovebackup = (ArrayList<sizeDice>) toRemove.clone();

        for (sizeDice diceToRemove:toRemovebackup)
        {
            dices.remove(diceToRemove);
        }

        toRemove.clear();
        refreshValues();
    }



    //ustawianie wartości statycznych
    static void setLocation(GridPane gp1,Dimension size1,Dimension dimensions1,ArrayList<Integer> positions1, Button[] externButtons1, ArrayList<sizeDice> zaznaczone1,ArrayList<Dice> allDices1)
    {
        gp=gp1;
        size=size1;
        dimensions=dimensions1;
        positions=positions1;
        externButtons=externButtons1;
        zaznaczone=zaznaczone1;
        allDices=allDices1;
    }



    //konstruktor tworzący obiekty kości
    Dice(int sides,String diceImage)
    {
        //zainicjowanie grafiki kości
        this.diceImage=diceImage;
        // ilości stron
        this.d=sides;
        //odległości elementów od ścian //(top/right/bottom/left)
        setPadding(new Insets(5, 5, 5, 5)); //margins around the whole grid


        //wygenerowanie grafiki
        ImageView imageView = Operations.imageViewgenerator(diceImage,size.height*1/11,size.height*1/11);

        //ustwwienie pierwotnego nadrunku na kościach oraz koloru
        onDice.setText("d"+d);
        onDice.setTextFill(Color.WHITE);

        //stworzenie i ustwienie paddingu, dodanie elementów,  aligmentu oraz wielkości panu do przechowywania wizualizacji kostki
        StackPane imagePane = new StackPane();
        imagePane.setPadding(new Insets(1.5, 5, 1.5, 5));
        imagePane.getChildren().addAll(imageView,onDice);
        imagePane.setAlignment(Pos.CENTER);
        imagePane.setMinHeight(size.height*1/11);
        imagePane.setMinWidth(size.height*1/10);
        imagePane.setMaxHeight(size.height*1/11);
        imagePane.setMaxWidth(size.height*1/10);


        //ustawienie labla wyświetla
        Label amountLabel = new Label("Amount");
        amountLabel.setTextFill(Color.WHITE);
        amountLabel.setMaxWidth(size.getWidth()*0.1);
        amountLabel.setMinWidth(size.getWidth()*0.03);

        //ustawienie punktu poboru ilości elementów do wczytania, wielkości, bacgroundu, kooru liter
        TextField amounText = new TextField(Integer.toString(amount));
        amounText.setMaxWidth(size.getWidth()*0.1);
        amounText.setMinWidth(size.getWidth()*0.03);
        amounText.setBackground(Background.fill(Color.rgb(255,255,255,0)));
        amounText.setStyle("-fx-text-fill: white;");

        //dodanie listenera do zapobiegania  wczytywania lietr nieprawidłowych
        amounText.textProperty().addListener
        (
            //listener przystosowany jest na jakąkoliwek zmiane tekstu
            new ChangeListener<String>()
            {
                //madpisanie zmiany
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                {
                    //jeżeli długość jest 0 ustaw wartość na 0
                    if(newValue.length()==0)
                    {
                        amounText.setText("0");
                        amount=0;
                    }
                    //jeżeli pasuje do regexu(liczby dodatnie)
                    else if (newValue.matches("\\d*"))
                    {
                        if(oldValue.matches("0") )
                        {
                            amounText.setText(""+newValue.toCharArray()[1]);
                            amount=Integer.parseInt((""+newValue.toCharArray()[1]));
                        }
                        else
                            //zapisz to do zmiennej maount
                            amount=Integer.parseInt(newValue);
                    }
                    //jezeli nie pasuje ustaw starą wartość
                    else
                    {
                        amounText.setText(oldValue);
                    }
                }
            }
        );


        //ustawienie punktu wyboru ilości kostek, jego paddingu i spackgnu, poczym dodanie do niego wszystkich komponentów
        VBox amounts = new VBox();
        amounts.setPadding(new Insets(7, 0, 2, 2));
        amounts.setSpacing(10);
        amounts.setAlignment(Pos.CENTER);
        amounts.getChildren().addAll(amountLabel,amounText);


        Button plus = new Button("+");
        plus.setMaxWidth(size.getWidth()*0.02);
        plus.setMinWidth(size.getWidth()*0.02);
        plus.setOnAction
        (
            e->
            {
                amount++;
                amounText.setText(String.valueOf(amount));
            }
        );
        plus.setOnMouseClicked( event->{Operations.clickedPulse(plus);});
        plus.setOnMousePressed(event->{Operations.releasePulse(plus);});

        Button minus = new Button("-");
        minus.setMaxWidth(size.getWidth()*0.02);
        minus.setMinWidth(size.getWidth()*0.02);
        minus.setOnAction
        (
            e->
            {
                if(amount!=0)
                    amount--;
                amounText.setText(String.valueOf(amount));
            }
        );
        minus.setOnMouseClicked( event->{Operations.clickedPulse(minus);});
        minus.setOnMousePressed(event->{Operations.releasePulse(minus);});

        //ustawienie punktu wyboru ilości kostek, jego paddingu i spackgnu, poczym dodanie do niego wszystkich komponentów
        VBox plusMinus = new VBox();
        plusMinus.setPadding(new Insets(7, 0, 2, 2));
        plusMinus.setSpacing(10);
        plusMinus.getChildren().addAll(plus,minus);


        //Stworzneie texttfieldu do przechowywania poszukiwanychwartośdci kości
        //ustawienie jego domyślenej wartosci, wielkości,bacgroundyu,koloru liter
        TextField value = new TextField("1");
        value.setMaxWidth(size.getWidth()*0.25);
        value.setMinWidth(size.getWidth()*0.05);
        value.setBackground(Background.fill(Color.rgb(255,255,255,0)));
        value.setStyle("-fx-text-fill: white;");

        //dodanie listenera w celu zapobiegnięcia wpisywania nieporządanych wartośćci
        value.textProperty().addListener
        (
            new ChangeListener<String>()
            {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
                {
                     if(newValue.length()==0)
                     {
                         value.setText("1");
                     }
                     else if(newValue.length()==2 && oldValue.length()==1 && oldValue.matches("1") )
                     {
                         value.setText(""+newValue.toCharArray()[1]);
                     }
                     else if (newValue.matches("\\d*"))
                     {
                         if(!newValue.matches("\\d"))
                         {
                             value.setText(oldValue);
                             return;
                         }

                         if(Integer.parseInt(newValue)>d && Integer.parseInt(newValue)!=0)
                         {
                             value.setText(oldValue);
                         }
                         else
                         {
                             amount=Integer.parseInt(newValue);;
                         };
                     }
                     else
                     {
                         value.setText(oldValue);
                     }
                }
            }
        );


        amountbutton.setMaxWidth(size.getWidth()*0.05);
        amountbutton.setMinWidth(size.getWidth()*0.05);
        //podłączenie listenera do aktywacji góźika

        amountbutton.setOnAction
        (
            //ustawia text zawierający ilość kości o podanej wartości
            e->
            {
                //jeżeli nie ma elementów zanacznych
                if(zaznaczone.size()==0)
                {
                    int count = 0;
                    for (sizeDice d1 : dices)
                    {
                        if (d1.value == Integer.parseInt(value.getText()))
                        {
                            count++;
                        }
                    }
                    onDice.setText(Integer.toString(count));

                    //zmiany kolorów góźika aby zaznaczyć który góźik jest urzywany
                    amountbutton.setBackground(Background.fill(Color.rgb(255, 255, 255, 1)));
                    amountbutton.setStyle("-fx-text-fill: black;");
                    Operations.changebuttons(new Button[]{sumButton, minButton, maxButton});

                    //ustawienie wybranego góźika
                    chosenButon = 0;
                }
                else
                {
                    int count = 0;
                    for (sizeDice d1 : zaznaczone)
                    {
                        if(dices.contains(d1))
                        {
                            if (d1.value == Integer.parseInt(value.getText()))
                            {
                                count++;
                            }
                        }
                    }
                    onDice.setText(Integer.toString(count));

                    //zmiany kolorów góźika aby zaznaczyć który góźik jest urzywany
                    amountbutton.setBackground(Background.fill(Color.rgb(255, 255, 255, 1)));
                    amountbutton.setStyle("-fx-text-fill: black;");
                    Operations.changebuttons(new Button[]{sumButton, minButton, maxButton});

                    //ustawienie wybranego góźika
                    chosenButon = 0;
                }
            }
        );

        value.setOnKeyPressed
        (
            event ->
            {
                if( event.getCode() == KeyCode.ENTER )
                {
                    amountbutton.fire();

                    //ustawienie wybranego góźika
                    chosenButon=0;
                }
            }
        );
        //tworzy hbiox i ustawia jego ustawienia po czuym dodaje do niego elementy zaawansowane
        HBox advance = new HBox();
        advance.setPadding(new Insets(2, 2, 2, 2));
        advance.setSpacing(7);
        advance.getChildren().addAll(amountbutton,value);


        sumButton.setOnAction
        (
            e->
            {
                onDice.setText(Integer.toString(sum));

                //zmiany kolorów góźika aby zaznaczyć który góźik jest urzywany
                sumButton.setBackground(Background.fill(Color.rgb(255,255,255,1)));
                sumButton.setStyle("-fx-text-fill: black;");
                Operations.changebuttons(new Button[]{amountbutton,minButton,maxButton});

                //ustawienie wybranego góźika
                chosenButon=1;
            }
        );


        //góźik zwracający najmniejszy wynik kości
        minButton.setOnAction
        (
            e->
            {
                onDice.setText(Integer.toString(min));

                //zmiany kolorów góźika aby zaznaczyć który góźik jest urzywany
                minButton.setBackground(Background.fill(Color.rgb(255,255,255,1)));
                minButton.setStyle("-fx-text-fill: black;");
                Operations.changebuttons(new Button[]{amountbutton,sumButton,maxButton});


                //ustawienie wybranego góźika
                chosenButon=2;
            }
        );


        //góźik zwracający największy wynik kości
        maxButton.setOnAction
        (
            e->
            {
                onDice.setText(Integer.toString(max));

                //zmiany kolorów góźika aby zaznaczyć który góźik jest urzywany
                maxButton.setBackground(Background.fill(Color.rgb(255,255,255,1)));
                maxButton.setStyle("-fx-text-fill: black;");
                Operations.changebuttons(new Button[]{amountbutton,sumButton,minButton});


                //ustawienie wybranego góźika
                chosenButon=3;
            }
        );

        //stworzenie hboxa bazowego, ustawienie jego ustawień i dodanie do niego podstawowych góźików
        HBox base = new HBox();
        base.setPadding(new Insets(2, 2, 2, 2));
        base.setSpacing(7);
        base.getChildren().addAll(minButton,sumButton,maxButton);


        //zebranie góźików w liste i przesłanie ich do funkcji dostosowującej
        Button[] buttons = {plus,minus,amountbutton,minButton,maxButton,sumButton};
        Operations.changebuttons(buttons);

        //ustawienie ostatecznego vboxa i dodanie do niego wszystkiego
        VBox setting = new VBox();
        setting.setPadding(new Insets(2, 2, 2, 2));
        setting.setSpacing(7);
        setting.getChildren().addAll(advance,base);
        getChildren().addAll(imagePane,plusMinus,amounts,setting);

        //dodanie do listy wszystkich kostek
        allDices.add(this);
    }

}
