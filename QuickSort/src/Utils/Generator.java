package Utils;

import java.util.Random;

public class Generator {

    public static abstract class AbstractGenerator<T>
    {
        public abstract T NextValue ();
    }

    public static class IntGenerator extends AbstractGenerator<Integer>
    {
        private Random m_random;
        private int m_min, m_max;

        public IntGenerator (int seed, int min, int max)
        {
            m_random = new Random(seed);
            m_min = min;
            m_max = max;
        }

        @Override
        public Integer NextValue ()
        {
            return m_random.nextInt(m_max - m_min) + m_min;
        }
    }

    public static class DoubleGenerator extends AbstractGenerator<Double>
    {
        private Random m_random;
        private int m_min, m_max;

        public DoubleGenerator (int seed, int min, int max)
        {
            m_random = new Random(seed);
            m_min = min;
            m_max = max;
        }

        @Override
        public Double NextValue ()
        {
            return m_random.nextDouble(m_max - m_min) + m_min;
        }
    }
}
