package Sort;
import java.util.Random;
import java.util.Arrays;

public class Sorter {

    public static abstract class AbstractSorter<T>
    {
        private boolean m_ignore_reset = false;
        public <T> void  IgnoreReset ()
        {
            m_ignore_reset = true;
        }

        public <T> long  Sort (T[] array,  Comporators.AbstractCountingComparator<T> comporator)
        {
            return Sort (array, 0, array.length - 1, comporator);
        }

        public <T> long  Sort (T[] array, int left, int right, Comporators.AbstractCountingComparator<T> comporator)
        {
            if (!m_ignore_reset)
                comporator.Reset();

            long start_time = System.currentTimeMillis();
            SortPrivate (array, 0, array.length - 1, comporator);
            return System.currentTimeMillis() - start_time;
        }

        public abstract <T> void SortPrivate (T[] array, int left, int right, Comporators.AbstractCountingComparator<T> comporator);
    }

    public static class JavaSorter<T> extends AbstractSorter<T>
    {
        @Override
        public <T> void SortPrivate (T[] array, int left, int right, Comporators.AbstractCountingComparator<T> comporator) {
            Arrays.sort (array, left, right + 1, comporator);
        }
    }

    public static class InsertionSorter<T> extends AbstractSorter<T>
    {
        @Override
        public <T> void SortPrivate (T[] array, int left, int right, Comporators.AbstractCountingComparator<T> comporator) {
            for (int i = left + 1; i <= right; i++) {
                T value = array[i];
                int j = i - 1;
                while (j >= left && comporator.compare(array[j], value) > 0) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = value;
            }
        }
    }

    public static class QuickSorter<T> extends AbstractSorter<T>
    {
        private Random m_rnd;
        private boolean m_use_insertion = false;
        private boolean m_use_median_of_3 = true;
        private int m_min_count_for_insertion_sort = 3;
        private InsertionSorter<T> m_isorter = null;

        public QuickSorter (int seed, boolean use_insertion, int min_count_for_insertion_sort, boolean use_median_of_3)
        {
            m_rnd = new Random (seed);
            m_use_insertion = use_insertion;
            m_use_median_of_3 = use_median_of_3;
            m_min_count_for_insertion_sort = min_count_for_insertion_sort;

            if (use_insertion)
            {
                m_isorter = new InsertionSorter<T> ();
                m_isorter.IgnoreReset ();
            }

        }

        @Override
        public <T> void SortPrivate (T[] array, int left, int right, Comporators.AbstractCountingComparator<T> comporator)
        {
            while (right > left)
            {
                if (right - left < m_min_count_for_insertion_sort && m_use_insertion)
                {
                    m_isorter.Sort(array, left, right, comporator);
                    return;
                }
                else
                {
                    int i = left;
                    int lt = left;
                    int gt = right;

                    T pivot;
                    if (m_use_median_of_3)
                        pivot = TukeysNinther(array, left, right, comporator);
                    else
                        pivot = array[left];

                    while (i <= gt) {
                        int cmpRes = comporator.compare(array[i], pivot);
                        if (cmpRes < 0)
                            Swap(array, lt++, i++);
                        else if (cmpRes > 0)
                            Swap(array, i, gt--);
                        else
                            i++;
                    }

                    if (lt - left < right - gt) {
                        SortPrivate(array, left, lt - 1, comporator);
                        left = gt + 1;
                    } else {
                        SortPrivate(array, gt + 1, right, comporator);
                        right = lt - 1;
                    }
                }
            }
        }

        private <T> T MedianOf3(T first, T second, T third, Comporators.AbstractCountingComparator<T> comporator) {

            int cmp1to2 = comporator.compare(first, second);
            int cmp1to3 =   comporator.compare(first, third);
            int cmp2to3 = comporator.compare(second, third);

            if ((cmp1to2 > 0) != (cmp1to3 > 0))
                return first;
            else if ((cmp1to2 < 0) != (cmp2to3 > 0))
                return second;
            else
                return third;
        }

        private <T> T MedianOf3(T[] array, int min, int max, Comporators.AbstractCountingComparator<T> comporator) {
            if (max == min + 1)
                return array[min];

            T first  = array[m_rnd.nextInt(max + 1 - min) + min];
            T second = array[m_rnd.nextInt(max + 1 - min) + min];
            T third  = array[m_rnd.nextInt(max + 1 - min) + min];

            return MedianOf3 (first, second, third, comporator);
        }

        private <T> T TukeysNinther (T[] a, int min, int max, Comporators.AbstractCountingComparator<T> comporator) {
            T pivot1 = MedianOf3(a, min, max, comporator);
            T pivot2 = MedianOf3(a, min, max, comporator);
            T pivot3 = MedianOf3(a, min, max, comporator);

            return MedianOf3 (pivot1, pivot2, pivot3, comporator);
        }

        private static <T> void Swap(T[] a, int i, int j) {
            T tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }
}
