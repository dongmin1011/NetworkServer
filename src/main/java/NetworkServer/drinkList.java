package NetworkServer;



import java.io.Serializable;
import java.util.List;

public class drinkList {


    private int uniqueNum;
    private String [] drinksName ;
    private int [] drinkPrice ;
    private int [] drinkStock ;

    private List<String > drinkMoneyList;

    public drinkList(int uniqueNum) {
        this.uniqueNum = uniqueNum;
    }

    public drinkList(int c, String[] names, int[] price, int[] stock){
        uniqueNum =c;
        drinksName = names;
        drinkPrice = price;
        drinkStock = stock;
    }

    public void setUniqueNum(int uniqueNum) {
        this.uniqueNum = uniqueNum;
    }

    public void setDrinksName(int index, String drinksName) {
        this.drinksName[index] = drinksName;
    }

    public void setDrinkPrice(int index, int drinkPrice) {
        this.drinkPrice[index] = drinkPrice;
    }

    public void setDrinkStock(int[] drinkStock) {
        this.drinkStock = drinkStock;
    }

    public void setDrinkStockNotArray(int index ,int drinkStock) {
        this.drinkStock[index] = drinkStock;
    }

    public int getUniqueNum() {
        return uniqueNum;
    }

    public String[] getDrinksName() {
        return drinksName;
    }

    public String getDrinksNameNotArray(int index) {
        return this.drinksName[index];
    }

    public int[] getDrinkPrice() {
        return drinkPrice;
    }

    public int getDrinksPriceNotArray(int index) {
        return this.drinkPrice[index];
    }

    public int[] getDrinkStock() {
        return drinkStock;
    }

    public int getDrinksStockNotArray(int index) {
        return this.drinkStock[index];
    }

    public List<String> getDrinkMoneyList() {
        return drinkMoneyList;
    }

    public void setDrinkMoneyList(List<String> drinkMoneyList) {
        this.drinkMoneyList = drinkMoneyList;
    }

}
