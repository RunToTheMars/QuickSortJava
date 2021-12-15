import Sort.*;
import Sort.*;
import java.util.Arrays;

import Utils.Array_Utils;
import Utils.Generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    public static void do_test () {
        testSortNames();
        testSortBigDoubles();
    }

    @Test
    @DisplayName("Sort Names")
    public static void testSortNames() {
        String[] names = {"Stas", "Lybov", "Gleb", "Elena", "Alexey", "Sergey", "David"};
        Comporators.AbstractCountingComparator<String> comparator = new Comporators.StringCountingComparator();

        String[] names_copy_java = names.clone();
        Arrays.sort(names_copy_java, comparator);

        Sorter.AbstractSorter<String> qsort = new Sorter.QuickSorter<String> (0,false, 0, true);
        String[] names_copy_qsort = names.clone();
        qsort.Sort(names_copy_qsort, comparator);

        assertTrue(Arrays.equals(names_copy_java, names_copy_qsort));
    }

    @Test
    @DisplayName("Sort big double")
    public static void testSortBigDoubles() {

        Generator.AbstractGenerator<Double> generator = new Generator.DoubleGenerator (1, 0, 1000);

        Double[] array = new Double[100000];
        Array_Utils.Array.Fill (array, generator);

        Comporators.AbstractCountingComparator<Double> comparator = new Comporators.DoubleCountingComparator();

        Double[] copy_java = array.clone();
        Arrays.sort(copy_java, comparator);

        Sorter.AbstractSorter<Double> qsort = new Sorter.QuickSorter<Double> (0,false, 0, true);
        Double[] copy_qsort = array.clone();
        qsort.Sort(copy_qsort, comparator);

        assertArrayEquals(copy_java, copy_qsort);
    }

}