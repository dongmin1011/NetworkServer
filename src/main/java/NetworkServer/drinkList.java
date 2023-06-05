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

    public List<String> getDrinkMoneyList() {
        return drinkMoneyList;
    }

    public void setDrinkMoneyList(List<String> drinkMoneyList) {
        this.drinkMoneyList = drinkMoneyList;
    }
}
