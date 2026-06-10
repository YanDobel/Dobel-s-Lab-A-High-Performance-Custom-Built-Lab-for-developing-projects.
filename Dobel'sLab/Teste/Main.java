package Teste;

import Lists.Stacks.DynamicStack;

import java.util.*;

public class
Main {
    public static void main(String[] agrs) {
        Scanner sc = new Scanner(System.in);
        DynamicStack<String> stack = new DynamicStack<>();
        stack.push("yan");
        stack.push("martins");
        stack.push("dobel");
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
        sc.close();
    }
}