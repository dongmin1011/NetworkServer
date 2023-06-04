package NetworkServer;



import java.io.Serializable;

public class drinkList {


    private int uniqueNum;
    private String [] drinksName ;
    private int [] drinkPrice ;
    private int [] drinkStock ;

    public drinkList(int c, String[] names, int[] price, int[] stock){
        uniqueNum =c;
        drinksName = names;
        drinkPrice = price;
        drinkStock = stock;
    }

    public void setUniqueNum(int uniqueNum) {
        this.uniqueNum = uniqueNum;
    }

    public void setDrinksName(String[] drinksName) {
        this.drinksName = drinksName;
    }

    public void setDrinkPrice(int[] drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public void setDrinkStock(int[] drinkStock) {
        this.drinkStock = drinkStock;
    }

    public int getUniqueNum() {
        return uniqueNum;
    }

    public String[] getDrinksName() {
        return drinksName;
    }

    public int[] getDrinkPrice() {
        return drinkPrice;
    }

    public int[] getDrinkStock() {
        return drinkStock;
    }
}
