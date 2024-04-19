package table;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;


public class Main extends Application
{

    @Override
    public void start(Stage stage) throws Exception
    {

        //USTAWIENIA
        //////////////////////////////////////////////////////////////////////////

            //ustawienie odległości od ścian i między elementami
            HBox root = new HBox();
            root.setSpacing(50);
            root.setPadding(new Insets(25, 25, 25, 25));

            //pobranie wielkości obrazu i dopasowanie do nich aplikacji
            Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
            root.setPrefHeight(size.getHeight()-50);
            root.setPrefWidth(size.getWidth());

            //ustawenie bacgroundu
            root.setBackground(Operations.giveback("wood.png",size.width,size.height));

            //utworzenie sceny i uniwmożliw nienie zmiany jeje wielkości
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);


            //utworzenie filcu, jego aligmentu wymiarów i backgroundyu
            GridPane filc = new GridPane();
            filc.setMinHeight(size.getHeight()*0.755);
            filc.setMinWidth(size.getWidth()*0.718);
            filc.setBackground(Operations.giveback("filc.png",size.width*1,size.height*0.9));
            // utworzenie tabeli przechowujące a) wszystkie możliwe sloty na kostki na filcu b) czy te sloty są zajęte

            //boolean[][] allowed = new boolean[(int)(size.height*0.45/40)][(int)(size.width*0.36/40)];
            //ustwienie zmiennej dimensions pozwalająca zdeterminować granice filcu w gridpanie
            Dimension dimensions = new Dimension((int)(size.width*0.36/40),(int)(size.height*0.45/40));
            ArrayList<Integer> positions = new ArrayList<Integer>();


            //utowezenie podstawki na kostki, ustawienie jeje wymiarów, aligemtnu i backgroundu
            VBox stone = new VBox();
            stone.setPrefHeight(size.getHeight()*0.7);
            stone.setPrefWidth(size.getWidth()*0.22);
            stone.setMinHeight(size.getHeight()*0.7);
            stone.setMinWidth(size.getWidth()*0.22);
            stone.setAlignment(Pos.CENTER);
            stone.setBackground(Operations.giveback("blackStone.jpg",size.width*0.95,size.height*0.9));


            //Góźiki trzeba wcześniej zadeklarować
            Button roll = new Button("ROLL");
            Button group = new Button("GROUP");
            Button clear = new Button("CLEAR");


            // ustawienie danych statycznych kostek i utworzenie kostek
            ArrayList<sizeDice> sizeDices = new ArrayList<sizeDice>();
            ArrayList<sizeDice> zaznaczone = new ArrayList<sizeDice>();
            ArrayList<Dice> allDices = new ArrayList<Dice>();
            sizeDice.setStatics(size,sizeDices,new Button[]{roll,group,clear},zaznaczone);
            Dice.setLocation(filc,size,dimensions,positions,new Button[]{roll,group,clear},zaznaczone,allDices);

            Dice d2 = new Dice(2,"d2.png");
            Dice d4 = new Dice(4,"d4.png");
            Dice d6 = new Dice(6,"d6.png");
            Dice d8 = new Dice(8,"d8.png");
            Dice d10 = new Dice(10,"d10.png");
            Dice d12 = new Dice(12,"d12.png");
            Dice d20 = new Dice(20,"d20.png");
            Dice d100 = new Dice(100,"d100.png");
            //Dice[] allDices = {d2,d4,d6,d8,d10,d12,d20,d100};


            // Góźik do rollowania, wielkość i akcja wykonywnaia
            roll.setMinWidth(size.getWidth()*0.07);
            roll.setOnAction
            (
                e->
                {
                    //jeżeli nie ma zaznaczonych elementów
                    if(roll.getText()=="ROLL")
                    {
                        clear.fire();
                        //wyczyszczenie filcu w przygotowaniu na kolejny rz
                        Operations.fillOportunities(dimensions, positions);
                        //wywołanie funkcji dla każdego typu
                        d2.roll();
                        d4.roll();
                        d6.roll();
                        d8.roll();
                        d10.roll();
                        d12.roll();
                        d20.roll();
                        d100.roll();
                    }
                    else
                    {
                        //dla wszystkich elementów wylosowane są nowe wartości i przypisane do wygladu i wartości elementów
                        for (sizeDice stpk:zaznaczone)
                        {
                            //wylosowanie nowej wartości i jej pokazanie
                            Random r = new Random();
                            stpk.value = r.nextInt(stpk.d) + 1;
                            stpk.onDice.setText(Integer.toString(stpk.value));

                            //ustawienie randomowej rotacji
                            stpk.setRotate((int) (Math.random() * 365));

                            //zmiana koloru zaznaczenia po przerolowaniu
                            stpk.setBorder
                            (
                                new Border
                                (
                                    new BorderStroke
                                    (
                                        Color.ORANGE,
                                        BorderStrokeStyle.DASHED,
                                        CornerRadii.EMPTY,
                                        BorderWidths.DEFAULT
                                    )
                                )
                            );
                        }

                        // odświerzenie wszytskich wartosci
                        for (Dice dice:allDices)
                        {
                            dice.refreshValues();
                        }
                    }
                }
            );
            roll.setOnMouseClicked( event->{Operations.clickedPulse(roll);});
            roll.setOnMousePressed(event->{Operations.releasePulse(roll);});

            //Guźik do układania elementów w kolejności
            group.setMinWidth(size.getWidth()*0.07);
            group.setOnAction
            (
                e->
                {
                    //wyczyszczenie filcu
                    filc.getChildren().clear();
                    //usunięcie zanzaczonych elementów
                    sizeDices.clear();
                    int element = 0;

                    //wuwołanie funkcji dla każdego typu
                    element = d100.displayInOrder(element);
                    element = d20.displayInOrder(element);
                    element = d12.displayInOrder(element);
                    element = d10.displayInOrder(element);
                    element = d8.displayInOrder(element);
                    element = d6.displayInOrder(element);
                    element = d4.displayInOrder(element);
                    element = d2.displayInOrder(element);
                }
            //programityczne rozwiązanie
            );
            group.setOnMouseClicked( event->{Operations.clickedPulse(group);});
            group.setOnMousePressed(event->{Operations.releasePulse(group);});


        //Guźik do układania elementów w kolejności
        clear.setMinWidth(size.getWidth()*0.07);
        clear.setOnAction
        (
            e->
            {
                if(clear.getText()=="CLEAR")
                {
                    filc.getChildren().clear();
                    sizeDices.clear();
                    d2.remove(d2.dices);
                    d4.remove(d4.dices);
                    d6.remove(d6.dices);
                    d8.remove(d8.dices);
                    d10.remove(d10.dices);
                    d12.remove(d12.dices);
                    d20.remove(d20.dices);
                    d100.remove(d100.dices);
                }
                else
                {
                    //dla wszystkich elementów wylosowane są nowe wartości i przypisane do wygladu i wartości elementów

                    //backup
                    ArrayList<sizeDice> zaznaczone2 = (ArrayList<sizeDice>)zaznaczone.clone();

                    for (sizeDice stpk:zaznaczone2)
                    {
                        //usunięcie wszystkich wystapień elementu w prokramie
                        sizeDices.remove(stpk);
                        zaznaczone.remove(stpk);
                        switch(stpk.d)
                        {
                            case 2:
                                allDices.get(0).dices.remove(stpk);
                                break;
                            case 4:
                                allDices.get(1).dices.remove(stpk);
                                break;
                            case 6:
                                allDices.get(2).dices.remove(stpk);
                                break;
                            case 8:
                                allDices.get(3).dices.remove(stpk);
                                break;
                            case 10:
                                allDices.get(4).dices.remove(stpk);
                                break;
                            case 12:
                                allDices.get(5).dices.remove(stpk);
                                break;
                            case 20:
                                allDices.get(6).dices.remove(stpk);
                                break;
                            case 100:
                                allDices.get(7).dices.remove(stpk);
                                break;
                        }
                        filc.getChildren().remove(stpk);
                    }

                    // odświerzenie wszytskich wartosci
                    for (Dice dice:allDices)
                    {
                        dice.refreshValues();
                    }

                    //po usunięciu trzeba odświerzyć góźiki aby się zmieniły
                    sizeDice.buttonReset();
                }
            }
        );
        clear.setOnMouseClicked( event->{Operations.clickedPulse(clear);});
        clear.setOnMousePressed(event->{Operations.releasePulse(clear);});



        //zebranie góźików w liste zmodyfikowanie
            Button[] rolls = {roll,group,clear};
            Operations.changebuttons(rolls);
            //dodanie hboxa z góźikami
            HBox buttons = new HBox();
            buttons.setPadding( new Insets(5,5,5,5));
            buttons.setSpacing(5);
            //dodanie hboxa z góźikami
            buttons.getChildren().addAll(roll,group,clear);


            //dodanie wszystkich elementów do panelu po prawej
            stone.getChildren().addAll
            (
                d2,
                d4,
                d6,
                d8,
                d10,
                d12,
                d20,
                d100,
                buttons
            );


            root.getChildren().addAll(filc,stone);
            stage.show();

        }


        public static void main(String[] args) {
            launch(args);
        }
    }

