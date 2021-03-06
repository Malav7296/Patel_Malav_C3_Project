import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.spec.PSource;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {


    Restaurant restaurant;
    RestaurantService service;
    LocalTime openingTime;
    LocalTime closingTime;

    @BeforeEach
    public void prerequisteSetup(){
       service = new RestaurantService();
       openingTime = LocalTime.parse("10:30:00");
       closingTime = LocalTime.parse("22:00:00");
       restaurant = service.addRestaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
    }

    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime timeBeforeClosingTime = LocalTime.parse("20:00:00");
        restaurant.setCurrentTime(timeBeforeClosingTime);
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        LocalTime timeAfterClosingTime = LocalTime.parse("23:00:00");
        restaurant.setCurrentTime(timeAfterClosingTime);
        assertFalse(restaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void display_total_cost_of_all_selected_items() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Pizza", 300);
        restaurant.addToMenu("Sandwich", 100);
        List <String> itemList = new  ArrayList<String>();
        itemList.add("Vegetable lasagne");
        itemList.add("Pizza");
        itemList.add("Sweet corn soup");
        assertEquals("Your order will cost: 688",restaurant.displayTotalCost(itemList));
    }

    @Test
    public void display_error_message_when_item_passed_to_displayTotalCost_is_not_in_Menu() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Pizza", 300);
        restaurant.addToMenu("Sandwich", 100);
        List <String> itemList = new  ArrayList<String>();
        itemList.add("Vegetable lasagne");
        itemList.add("Pizza");
        itemList.add("Hot N Sour soup");
        assertEquals("Hot N Sour soup not in Menu. Please check menu and add items appropriately",restaurant.displayTotalCost(itemList));
    }

}