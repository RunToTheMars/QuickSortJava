package Sort;

import java.util.Comparator;

public class Comporators
{
    public static abstract class AbstractCountingComparator<T> implements Comparator<T> {

        private int m_count = 0;
        public int Count() {
            return m_count;
        }
        public void Reset()
        {
            m_count = 0;
        }

        @Override
        public int compare(T t1, T t2) {
            m_count++;
            return ComparePrivate(t1, t2);
        }

        public abstract int ComparePrivate(T t1, T t2);
    }

    public static class IntCountingComparator extends AbstractCountingComparator<Integer> {
        @Override
        public int ComparePrivate(Integer t1, Integer t2)
        {
            return t1 - t2;
        }
    }

    public static class DoubleCountingComparator extends AbstractCountingComparator<Double> {
        @Override
        public int ComparePrivate(Double t1, Double t2)
        {
            return t1 - t2 > 0 ? 1 : -1;
        }
    }

    public static class StringCountingComparator extends AbstractCountingComparator<String> {
        @Override
        public int ComparePrivate(String t1, String t2)
        {
            return t1.compareToIgnoreCase(t2);
        }
    }
}


