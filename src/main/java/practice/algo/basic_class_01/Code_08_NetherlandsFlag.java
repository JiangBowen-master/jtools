package practice.algo.basic_class_01;

public class Code_08_NetherlandsFlag {

	/**
	 *
	 * @param arr 数组
	 * @param l 左边界下标
	 * @param r 右边界下标
	 * @param p 比较基准数
	 * @return 中间边界位置
	 */
	public static int[] partition(int[] arr, int l, int r, int p) {
		// 划分后左部分和右部分可能是无序的
		// 小于区间最右端下标
		int less = l - 1;
		// 大于区间最左端下标
		int more = r + 1;
		while (l < more) {
			if (arr[l] < p) {
				// l下标的值和小于区间的下一个值交换, l下标加1
				swap(arr, ++less, l++);
			} else if (arr[l] > p) {
				// l下标的值和大于区间的前一个值交换, l下标不变
				swap(arr, --more, l);
			} else {
				l++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}

	// for test
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// for test
	public static int[] generateArray() {
		int[] arr = new int[10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 7);
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] test = generateArray();

		printArray(test);
		int[] res = partition(test, 0, test.length - 1, 4);
		printArray(test);
		System.out.println(res[0]);
		System.out.println(res[1]);

	}
}
