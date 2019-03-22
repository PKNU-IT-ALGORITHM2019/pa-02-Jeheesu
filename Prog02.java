package prog_assign02;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Prog02 {
	private static int n;
	public static Board [] array = new Board[100];
	private static int [] count = new int[100];			// array[n].count 담을 통
	private static double realRes = 999999999;			// 기본 거리 최대
	
	public static void main(String[] args) {
		readIndex("input4.txt");		
	}

	public static void readIndex(String index) {
		try {
			Scanner str = new Scanner(new File(index));
			n = str.nextInt();
			for(int i = 0; i < n; i++) {
				array[i] = new Board(str.nextInt(), str.nextInt(), i);
			}
			str.close();
			long start = System.currentTimeMillis();
			tsp(1, 0);
			long end = System.currentTimeMillis();
			System.out.println("answer : ");
			System.out.println(realRes);
			System.out.print("[");
			for(int i = 0; i < n; i++) {
				if(i == n-1) {
					System.out.print(count[i]);
					break;
				}
				System.out.print(count[i] + ", ");
			}		
			System.out.println("]");
			System.out.println("총 걸린시간 : " + (end - start)/1000.00);		// 걸린 시간 체크용
		} catch (FileNotFoundException e) {
			System.out.println("No Files");
			System.exit(0);
		}
	}

	private static double len(int k, int i) {		// 직선 거리 구하기
		return Math.sqrt(Math.pow(array[k].x-array[i].x, 2) + Math.pow(array[k].y-array[i].y, 2));		 
	}

	private static void tsp(int k, double res) {
		if(realRes < res)							// 거리 최솟값이 이전에 구한 거리 보다 작을 땐 가지치기
			return;
		if(k == n) {
			res = res + len(k-1, 0);
			if(realRes > res) {						// 제일 최솟값이라면 순서 매기기
				realRes = res;
				for(int i = 0; i < n; i++)
					count[i] = array[i].count;
			}				
		}
		else {
			for(int i = k; i < n; i++) {
				swap(array, k, i);
				tsp(k+1, res + len(k-1,k));
				swap(array, k, i);
			}
		}
	}

	private static void swap(Board[] array, int k, int i) {
		Board tmp = array[k];
		array[k]  = array[i];
		array[i] = tmp;
	}
}