package mk.ukim.finki.wpaud.bootstrap;

import lombok.Getter;
import mk.ukim.finki.wpaud.model.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class DataHolder {
    public static List<Category> categories= new ArrayList<>();
    public static List<User> users=new ArrayList<>();
    public static List<Manufacturer> manufacturers=new ArrayList<>();
    public static List<Product> products=new ArrayList<>();
    public static List<ShoppingCart> shoppingCarts= new ArrayList<>();

    @PostConstruct
    public void init(){
//        this.categories.add(new Category("Software","Software Description"));
//        this.categories.add(new Category("Books","Books Description"));
//
//        Manufacturer manufacturer=new Manufacturer("Nike","NY NY");
//        Manufacturer manufacturer1=new Manufacturer("Adidas","PHI USA");
//        Manufacturer manufacturer2=new Manufacturer("Puma","SK MK");
//        manufacturers.add(manufacturer);
//        manufacturers.add(manufacturer1);
//        manufacturers.add(manufacturer2);
//        Category category=new Category("Sport" , "Sport Category");
//        this.categories.add(category);
//        this.products.add(new Product("Ball",30.5,7,category,manufacturer));
//        this.products.add(new Product("Ball1",21.0,7,category,manufacturer));
//        this.products.add(new Product("Ball2",41.1,7,category,manufacturer));
//
//
//
//        users.add(new User("damjan.boshkovski","dame123","Damjan","Boskovski"));
//        users.add(new User("mila.boshkovska","mila123","Mila","Boskovska"));
    }
}
