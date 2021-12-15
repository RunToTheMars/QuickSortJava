import Sort.*;
import Utils.*;

import java.util.Arrays;

public class Main {
    static private int m_array_count = 20000;
    static private int m_array_max_value = 100000;
    static private int m_array_min_value = 0;

    public static <T> T[] run_on_array  (String title, Comporators.AbstractCountingComparator<T> comparator, T[] array, Sorter.AbstractSorter<T> sorter, T[] target_array)
    {
        System.out.println(title);

        T[] array_copy = array.clone();
        System.out.print("Before sort: ");
        Array_Utils.Array.Print(array_copy);
        long time_elapsed = sorter.Sort(array_copy, comparator);
        System.out.print("After sort:  ");
        Array_Utils.Array.Print(array_copy);

        System.out.println("Cmp Count: " + comparator.Count());
        System.out.println("Elapsed time: " + time_elapsed / 1000.);

        if (target_array != null)
        {
            boolean equal = Arrays.equals (array_copy, target_array);
            System.out.println(equal ? "Is ok" : "Is not ok");
        }

        System.out.println();

        return array_copy;
    }



    public static void main (String[] args) {
        Integer[] array = new Integer[m_array_count];
        Integer[] target_array = null;
        Generator.AbstractGenerator<Integer> generator = new Generator.IntGenerator (1, m_array_min_value, m_array_max_value);

        Array_Utils.Array.Fill (array, generator);

        Comporators.AbstractCountingComparator<Integer> cmp = new Comporators.IntCountingComparator ();

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.JavaSorter<Integer> ();
            target_array = run_on_array ("Java Sort", cmp, array, sorter, null);
        }

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, true, 3, true);
            run_on_array ("Own Quick Sort (Ins 1 (3), mof3 1)", cmp, array, sorter, target_array);
        }

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, true, 5, true);
            run_on_array ("Own Quick Sort (Ins 1 (5), mof3 1)", cmp, array, sorter, target_array);
        }

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, true, 5, true);
            run_on_array ("Own Quick Sort (Ins 1 (10), mof3 1)", cmp, array, sorter, target_array);
        }


        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, true, 3,false);
            run_on_array ("Own Quick Sort (Ins 1 (3), mof3 0)", cmp, array, sorter, target_array);
        }

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, true, 5, false);
            run_on_array ("Own Quick Sort (Ins 1 (5), mof3 0)", cmp, array, sorter, target_array);
        }

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, true, 10, false);
            run_on_array ("Own Quick Sort (Ins 1 (10), mof3 0)", cmp, array, sorter, target_array);
        }

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, false, 0, true);
            run_on_array ("Own Quick Sort (Ins 0, mof3 1)", cmp, array, sorter, target_array);
        }

        {
            Sorter.AbstractSorter<Integer> sorter = new Sorter.QuickSorter<Integer>(0, false, 0, false);
            run_on_array ("Own Quick Sort (Ins 0, mof3 0)", cmp, array, sorter, target_array);
        }

        Tests.do_test();
    }
}
