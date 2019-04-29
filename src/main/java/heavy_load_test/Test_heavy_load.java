package heavy_load_test;

public class Test_heavy_load
{
    public static void main(String[] args)
    {
        for(int i = 0; i < 50; i++){

            RunnableDemo runD = new RunnableDemo("t" + (i + 1));
            runD.start();
        }
    }
}