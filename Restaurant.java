

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private String name;
    private String location;
    public LocalTime openingTime;
    public LocalTime closingTime;
    public LocalTime currentTime = LocalTime.now();
    private List<Item> menu = new ArrayList<Item>();


    public Restaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        this.name = name;
        this.location = location;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isRestaurantOpen() {
        if (this.getCurrentTime().isBefore(this.closingTime)){ // change to this.closingTime
            return true;
        }else {
            return false;
        }


    }

    public LocalTime getCurrentTime(){ return  this.currentTime; }

    public void setCurrentTime(LocalTime currentTime){ this.currentTime = currentTime; }

    public List<Item> getMenu() {
        return menu;
        //DELETE ABOVE RETURN STATEMENT AND WRITE CODE HERE
    }

    private Item findItemByName(String itemName){
        for(Item item: menu) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void addToMenu(String name, int price) {
        Item newItem = new Item(name,price);
        menu.add(newItem);
    }


    public void removeFromMenu(String itemName) throws itemNotFoundException {

        Item itemToBeRemoved = findItemByName(itemName);
        if (itemToBeRemoved == null)
            throw new itemNotFoundException(itemName);

        menu.remove(itemToBeRemoved);
    }
    public void displayDetails(){
        System.out.println("Restaurant:"+ name + "\n"
                +"Location:"+ location + "\n"
                +"Opening time:"+ openingTime +"\n"
                +"Closing time:"+ closingTime +"\n"
                +"Menu:"+"\n"+getMenu());

    }

    // TDD method to display total cost
    public String displayTotalCost (List<String> itemList) {
        int sumOfPrice = 0;
        boolean flag = false;
        for(String item : itemList){
            if (this.findItemByName(item) == null){
                return item+" not in Menu. Please check menu and add items appropriately";
            }else{
                sumOfPrice += this.findItemByName(item).getPrice();
            }

        }return "Your order will cost: "+sumOfPrice;
    }

    public String getName() {
        return name;
    }


}
