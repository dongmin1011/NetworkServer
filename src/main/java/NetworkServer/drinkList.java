package NetworkServer;



import java.io.Serializable;

public class drinkList implements Serializable {
    private String [] drinksName = new String[5];
    private int [] drinkPrice = new int[5];

//    drinkList(VendingMachine vendingMachine){
//        for(int i =0 ;i <5; i++){
//            drinksName[i] = vendingMachine.getDrinks(i).getName();
//            drinkPrice[i] = vendingMachine.getDrinks(i).getPrice();
//        }
//    }

}
