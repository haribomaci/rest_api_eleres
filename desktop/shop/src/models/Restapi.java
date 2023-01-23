package models;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Restapi {

    public Restapi() {
        // String str = this.getProductsAsString();
        // System.out.println("Adatok");
        // System.out.println(str);


    }
    
    public String getProductsAsString(){
        String text = "";
        try {
            text = this.tryGetProductsAsString();
        } catch (IOException e) {
            System.err.println("hiba a RestApi lekérdezése sikertelen");
        }
        return text;
    }
    public String tryGetProductsAsString()throws IOException{
        String host= "http://[::1]:3000/";
        String endpoint = "products";
        String urlStr = host + endpoint;
        URL url = new URL(urlStr);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("GET");
        http.connect();
        http.getResponseCode();

        int responseCode = http.getResponseCode();
        StringBuilder text = new StringBuilder();
        if(responseCode == 200 ){

            InputStream inputStream = http.getInputStream();
            Reader reader = new InputStreamReader(inputStream, "UTF-8");
            Scanner scan = new Scanner(reader);
            while(scan.hasNextLine()){
                text.append(scan.nextLine());
            }
            scan.close();
        }else{
            System.err.println("Hiba, a http lekérdezés sikertelen");
        }
        return text.toString();
    }
    public ArrayList<Product> getProducts(){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Product[] products = gson.fromJson(getProductsAsString(), Product[].class) ;
        ArrayList<Product>productList = new ArrayList<>(Arrays.asList(products));
        return productList;
    }
}
