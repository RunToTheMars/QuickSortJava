package Utils;

public class Array_Utils {
    public class Array
    {
        public static <T> void Fill (T[] array, Generator.AbstractGenerator<T> generator)
        {
            Fill (array, 0, array.length - 1, generator);
        }

        private static <T> void Fill (T[] array, int left, int right, Generator.AbstractGenerator<T> generator)
        {
            for (int i = left; i < array.length && i <= right; i++)
            {
                array[i] = generator.NextValue ();
            }
        }

        private static int m_max_print = 10;

        public static <T> void Print (T[] array)
        {
            if (array.length > m_max_print)
            {
                int shift = m_max_print / 2;
                Print (array, 0, shift);
                System.out.print("... ");
                Print (array, array.length - 1 - shift, array.length);
                System.out.println();
            }
            else
            {
                Print (array, 0, array.length);
                System.out.println();
            }

        }

        private static <T> void Print (T[] array, int left, int right)
        {
            for (int i = left; i < array.length && i <= right; i++)
            {
                System.out.print(array[i] + " ");
            }
        }
    }
}
