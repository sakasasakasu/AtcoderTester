import java.util.Scanner;
class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int[] arr = new int[len];
        int x = sc.nextInt();
        for (int idx = 0; idx < arr.length; idx++) {
            arr[idx] = sc.nextInt();    
            if(arr[idx]<x){
                x = arr[idx];
                System.out.println(1);
            }        
            else{
                System.out.println(0);
            }
        }
    }
    
}
