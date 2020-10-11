package prog09;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class SortTest<E extends Comparable<E>> {
  public static void main (String[] args) {
    tests(10);
  }

  public static void tests (int n) {
    Integer[] array = new Integer[n];
    Random random = new Random(0);
    for (int i = 0; i < n; i++)
      array[i] = random.nextInt(n);

    SortTest<Integer> tester = new SortTest<Integer>();
    if (n < 1000000)
     tester.test(new InsertionSort<Integer>(), array);
     tester.test(new HeapSort<Integer>(), array);
     tester.test(new QuickSort<Integer>(), array);
     tester.test(new MergeSort<Integer>(), array);
  }

  public void test (Sorter<E> sorter, E[] array) {
    System.out.println(sorter + " on array of length " + array.length);

    if (isSorted(array))
      System.out.println("array is already sorted!");

    E[] copy = array.clone();
    long time1 = System.nanoTime();
    sorter.sort(copy);
    long time2 = System.nanoTime();
    System.out.println((time2-time1)/1000.0 + " microseconds");

    if (!sameContents(array, copy))
      System.out.println("sorted array does not have the same elements!");

    if (!isSorted(copy))
      System.out.println("sorted array is not sorted");

    if (array.length < 100) {
      print(array);
      print(copy);
    }
  }

  public void print (E[] array) {
    String s = "";
    for (E e : array)
      s += e + " ";
    System.out.println(s);
  }

  /** Check if array is sorted. */
  public boolean isSorted (E[] array) {
    // EXERCISE
	  for (int i = 1; i < array.length; i++) {
		  if (array[i].compareTo(array[i-1]) < 0) {
			  //so array[i] is less than array[i-1]
			  return false;
		  }
	  }

    return true;
  }
 
  public boolean sameContents (E[] array1, E[] array2) {
    // EXERCISE
    // Create two Map from E to Integer. Use the HashMap implementation.
	  Map<E, Integer> map1 = new HashMap<E, Integer>();
	  Map<E, Integer> map2 = new HashMap<E, Integer>();

    // For each item in the first array, if it is not a key in the
    // first map, make it map to 1.  If it is already a key, increment
    // the integer it maps to.
	  for (int i = 0; i < array1.length; i++) {
		  if (!map1.containsKey(array1[i])) {
			  map1.put(array1[i],1);
		  }
		  else {
			  map1.put(array1[i],map1.get(array1[i])+1);
		  }
	  }
	 

    // Ditto second array and second map.
	  for (int j = 0; j < array2.length; j++) {
		  if (!map2.containsKey(array2[j])) {
			  map2.put(array2[j],1);
		  }
		  else {
			  map2.put(array2[j],map2.get(array2[j])+1);
		  }
	  }

    // For each item in the first array, check that it maps to the
    // same integer in both maps.  If not, return false.
	  
	  for (int x = 0; x < array1.length; x++) {
		  if (map1.get(array1[x]) != map2.get(array1[x])) {
			  return false;
		  }
	  }
	  
	  
    return true;
  }
}

class InsertionSort<E extends Comparable<E>> implements Sorter<E> {
  public void sort (E[] array) {
    for (int n = 0; n < array.length; n++) { //loop through every element of parameter array
      E data = array[n]; //data becomes each element of array
      int i = n; 

      // EXERCISE
      // while array[i-1] > data move array[i-1] to array[i] and
      // decrement i
      while(i>0 && array[i-1].compareTo(data) > 0) { //compare current element to previous element
    	  array[i] = array[i-1]; //switch the one before to the current
    	  i--; //look at elements before .. going backwards
      }

      array[i] = data;
    }
  }
}

class HeapSort<E extends Comparable<E>>
  implements Sorter<E> {

  private E[] array;
  private int size;

  public void sort (E[] array) {
    this.array = array;
    this.size = array.length;

    for (int i = getParent(array.length - 1); i >= 0; i--)
      swapDown(i);

    while (size > 1) {
      swap(0, size-1);
      size--;
      swapDown(0);
    }
  }

  public void swapDown (int index) {
    // EXERCISE
	  
    // While index is larger than one of its children, swap it with
    // its larger child.  Use the helper methods I provide below:
    // compare, getLeft, getRight, and isNull.
	  
	  while((!isNull(getLeft(index))) && compare(index, getLeft(index)) > 0 || 
			  (!isNull(getRight(index)) && compare(index, getRight(index)) > 0 )){
		  if((!isNull(getRight(index))) && compare(getLeft(index), getRight(index)) > 0) { 
			  swap(index, getRight(index));
			  index = getRight(index);
		  }
		  else if (!isNull(getLeft(index))){
			  swap(index, getLeft(index));
			  index = getLeft(index);
		  }

	  }
  }

  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }

  private int compare (int i, int j) { return array[j].compareTo(array[i]); }
  private int getLeft (int i) { return 2 * i + 1; }
  private int getRight (int i) { return 2 * i + 2; }
  private int getParent (int i) { return (i - 1) / 2; }
  private boolean isNull (int i) { return i >= size; }
}

class QuickSort<E extends Comparable<E>>
  implements Sorter<E> {

  private E[] array;

  private InsertionSort<E> insertionSort = new InsertionSort<E>();

  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }

  public void sort (E[] array) {
    this.array = array;
    sort(0, array.length-1);
  }

  private void sort(int left, int right) {
    if (left >= right)
      return;

    swap(left, (left + right) / 2);
    E pivot = array[left];
    //the middle one!

    int a = left + 1;
    int b = right;
    while (a <= b) {
      // EXERCISE

      // Move a forward if array[a] <= pivot
    	if (array[a].compareTo(pivot) <= 0) {
    		a++;
    		//first base case (increment a)
    	}
    	
      // Move b backward if array[b] > pivot
    	else if (array[b].compareTo(pivot) > 0) {
    		b--;
    		//second base case (increment b)
    	}
    	
      // Otherwise swap array[a] and array[b]
    	else {
    		swap(a,b);
    		//recursion
    	}
    }

    swap(left, b);

    sort(left, b-1);
    sort(b+1, right);
  }
}

class MergeSort<E extends Comparable<E>>
  implements Sorter<E> {

  private E[] array, array2;

  public void sort (E[] array) {
    this.array = array;
    array2 = array.clone();
    sort(0, array.length-1);
  }

  private void sort(int left, int right) {
    if (left >= right)
      return;

    int middle = (left + right) / 2;
    sort(left, middle);
    sort(middle+1, right);

    int i = left;
    int a = left;
    int b = middle+1;
    while (a <= middle || b <= right) {
      // EXERCISE
    	if (a > middle) {
    		array2[i] = array[b];
    		b++;
    		i++;
    	}
    	else if(b > right) {
    		array2[i] = array[a];
    		a++;
    		i++;
    	}
    	else if(array[a].compareTo(array[b]) >= 0) {
    		array2[i] = array[b];
    		b++;
    		i++;
    	}
    	else {
    		array2[i] = array[a];
    		a++;
    		i++;
    	}
    	
    	//check that the file isn't empty (there isn't always n elements to read... just always up to n elements)
    	//don't allocate memory by putting files into arrays!! no credit...it's supposed to be external
    }

    System.arraycopy(array2, left, array, left, right - left + 1);
  }
}
