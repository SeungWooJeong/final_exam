package com.example.sm.problem3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<CustomerThread> list = new ArrayList<CustomerThread>();
        Manager manager = new Manager();

        for(int i = 0 ; i < 10 ; i++){
            Customer customer = new Customer("Customer" + i);
            CustomerThread ct = new CustomerThread(customer);
            list.add(ct);
            manager.add_customer(customer);
            ct.start();
        }


        for(CustomerThread ct : list){
            ct.run();
            try {
                Thread.sleep((int)(Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        manager.sort();

        MyBaseAdapter adapter = new MyBaseAdapter(this, manager.list);
        ListView listview = (ListView) findViewById(R.id.listView1) ;
        listview.setAdapter(adapter);
    }
}

class CustomerThread extends Thread{

    Customer customer;
    CustomerThread(Customer customer){
        this.customer = customer;
    }

    public void run(){
        for(int i=0; i< 10; i++)
        {
            customer.work();
        }
    }
    // need something here
}

abstract class Person{

    static int money = 100000; // 공유자원
    int spent_money = 0;
    abstract void work();

}


class Customer extends Person{

    String name;
    Customer(String name){
        this.name = name;
    }

    Random rnd = new Random();
    int i = rnd.nextInt(1000);

    void work(){
        spent_money += i;
    }
    // need something here
}


class Manager extends Person{
    ArrayList <Customer> list = new ArrayList<Customer>();

    void add_customer(Customer customer) {
        list.add(customer);
    }


    void sort(){ // 직접 소팅 알고리즘을 이용하여 코딩해야함. 자바 기본 정렬 메소드 이용시 감
        for(int i=0; i<list.size(); i++) {
            for(int j=0; j<list.size(); j++) {
                if (list.get(j).spent_money > list.get(j+1).spent_money){
                    int temp;
                    temp = list.get(j).spent_money;
                    list.get(j+1).spent_money = list.get(j).spent_money;
                    list.get(j).spent_money = temp;
                }
            }
        }
    }

    @Override
    void work() {
        sort();
    }
}

// need something here

