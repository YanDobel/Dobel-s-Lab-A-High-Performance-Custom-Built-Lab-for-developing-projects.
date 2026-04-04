package Teste;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        String line;
        int caseNum = 1;

        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line);
            int n = Integer.parseInt(st.nextToken());
            int q = Integer.parseInt(st.nextToken());
            if (n == 0 && q == 0) break;

            int marbles[] = new int[n];
            for (int i = 0; i < n; i++) {
                marbles[i] = Integer.parseInt(br.readLine());
            }

            Arrays.sort(marbles);
            out.print("CASE# " + caseNum + ":");

            for (int j = 0; j < q; j++) {
                int query = Integer.parseInt(br.readLine());

                int pos = find(marbles, query);

                if (pos != -1) {
                    out.println(query + " found at " + (pos + 1));
                } else {
                    out.println(query + " not found");
                }
            }
            caseNum++;
        }
        out.flush();
    }
    private static int find(int arr[], int target) {
        int low = 0, high = arr.length - 1;
        int result = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] == target) {
                result = mid;
                high = mid - 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
}
