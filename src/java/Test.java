/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author TN041502
 */
import java.util.Scanner;
class Test{
  public static void main (String[] args){
      
      
      
      
      
    Scanner in = new Scanner(System.in);
    System.out.println("Enter text");
    String text = in.nextLine();
    if(text.matches("^[a-zA-Z0-9 ()+,.-]+$")){
      System.out.println("Yes it matches");
    }else{
        System.out.println("It does not");
    }
    String[] aa = {"a, c,d"};
    if (aa[0].trim().length() == 0){
        System.out.println("trimmed");
    }else{
        System.out.println("No a value exists");
    }
  }
}






















