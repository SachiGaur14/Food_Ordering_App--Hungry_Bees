package com.example.foodorderingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import com.example.foodorderingapp.Adaptor.CategoryAdaptor;
import com.example.foodorderingapp.Adaptor.PopularAdaptor;
import com.example.foodorderingapp.Domain.CategoryDomain;
import com.example.foodorderingapp.Domain.FoodDomain;
import com.example.foodorderingapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter,adapter2;

    private RecyclerView recyclerViewCategoryList,recyclerViewPopularList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
    }

    private void bottomNavigation(){
        FloatingActionButton floatingActionButton=findViewById(R.id.cartBtn);
        LinearLayout homeBtn=findViewById(R.id.homebtn);
        LinearLayout supportBtn=findViewById(R.id.supportbtn);
        LinearLayout profileBtn=findViewById(R.id.profilebtn);
        LinearLayout settingBtn=findViewById(R.id.settingbtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });

        supportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SupportActivity.class));
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
            }
        });

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });
    }

    private void recyclerViewCategory(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList=findViewById(R.id.cartView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category=new ArrayList<>();
        category.add(new CategoryDomain("Pizza", "cat_1"));
        category.add(new CategoryDomain("Burger", "cat_2"));
        category.add(new CategoryDomain("Donut", "cat_3"));
        category.add(new CategoryDomain("Drinks", "cat_4"));
        category.add(new CategoryDomain("HotDog", "cat_5"));

        adapter=new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recyclerViewPopular(){
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList=findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodList=new ArrayList<>();
        foodList.add(new FoodDomain("Pepperoni pizza","pop_1","Pepperoni, Mozzarella cheese, fresh oregano, pizza sauce",249));
        foodList.add(new FoodDomain("Cheese Burger","pop_2","Patty and cheese with tomato and onion slices, special sauce",99));
        foodList.add(new FoodDomain("Veggie pizza","pop_3","Roasted red peppers, baby spinach, onions, mushrooms, tomatoes, and black olives",299));
        foodList.add(new FoodDomain("Oreo shake","pop_4","Oreo milkshake with chocolate syrup",149));
        foodList.add(new FoodDomain("RedSauce pasta","pop_5","Tomato Puree, Onion, Spices, olive oil, Fresh herbs",199));
        foodList.add(new FoodDomain("Noodles","pop_6","Noodles, chilli sauce, cabbage, soy sauce, beans",149));

        adapter2=new PopularAdaptor(foodList);
        recyclerViewPopularList.setAdapter(adapter2);
    }
}