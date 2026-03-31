package Beecrowd;
import Trees.AVL.AVL_SucEsq.AVL;

import java.util.stream.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AVL<String> avl = new AVL<>();
        while(sc.hasNext()) {
            if (!sc.hasNextInt()) break;
            int n = sc.nextInt();
            avl.clear();
            for (int i = 0; i < n; i++) {
                avl.add(sc.next());
            }
            avl.forEach(System.out::println);
        }
        sc.close();
    }
}
