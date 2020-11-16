package uk.ac.cam.gtf23.prejava.ex4;

public class FibonacciCache {

    // Uninitialised array
    public static Long[] fib = null;

    public static void store() { //? Why do I not need to add the throws NullPointerException to the signature?
        if(fib == null){
            throw new NullPointerException("Fib is null");
        }

        for(int i = 0; i < Math.min(2, fib.length); i++){
            fib[i] = 1L;
        }

        for(int i = 2; i < fib.length; i++){
            fib[i] = fib[i-1] + fib[i-2];
        }

    }

    public static void reset(int cachesize) {
        fib = new Long[cachesize];

        for(int i = 0; i<fib.length;i++){
           fib[i] = 0L;
        }
    }

    public static long get(int i) throws Exception {
        if(fib == null){
            throw new Exception("fib is null");
        }

        try{
            return fib[i];
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {
        //TODO: catch exceptions as appropriate and print error messages
        reset(20);

        try{
            store();
        } catch (NullPointerException exception){
            exception.printStackTrace();
        }

        int i = Integer.decode(args[0]);
        try {
            System.out.println(get(i));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}