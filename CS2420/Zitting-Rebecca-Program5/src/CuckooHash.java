
// QuadraticProbing Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// bool insert( x )       --> Insert x
// bool remove( x )       --> Remove x
// bool contains( x )     --> Return true if x is present
// void makeEmpty( )      --> Remove all items


/**
 * Probing table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * @author Mark Allen Weiss
 */
public class CuckooHash<E>
{
    private HashEntry<E> [ ] firstArray;
    private HashEntry<E> [ ] secondArray;
    private int firstLocation;
    private int secondLocation;
    public int firstArraySize;
    public int secondArraySize;
    public float numOfInserts;
    public float insertsCalled;
    public float rehashCt;
    public int occupiedCt1;
    public int occupiedCt2;

    private static final int DEFAULT_TABLE_SIZE = 10000;
    private static final int MAX_LOOP = 10;

    /**
     * Construct the hash table.
     */
    public CuckooHash( )
    {
        this( DEFAULT_TABLE_SIZE );
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    public CuckooHash( int size ) {
        occupiedCt1 = 0;
        occupiedCt2 = 0;
        allocateArrays( size );
        doClear( );
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing.
     * Implementation issue: This routine doesn't allow you to use a lazily deleted location.  Do you see why?
     * @param x the item to insert.
     */
    public void insert( E x ) {
        insertsCalled++;
        if (find(x) != null) { return; }   // don’t insert duplicates
        for (int i = 0; i < MAX_LOOP; i++){
            firstLocation  = h1(x);
            if (isEmpty(firstArray, firstLocation)){
                firstArray[firstLocation] = new HashEntry<E>(x);
                numOfInserts++;
                occupiedCt1++;
                return;
            }
            E y = firstArray[firstLocation].element;
            firstArray[firstLocation] = new HashEntry<E>(x);
            secondLocation = h2(y);
            if (isEmpty(secondArray, secondLocation)) {
                secondArray[secondLocation] = new HashEntry<E>(y);
                numOfInserts++;
                occupiedCt2++;
                return;
            }
            x = secondArray[secondLocation].element; //change this to where it wants to go
            secondArray[secondLocation].element = y; //change y to next number
        }
        rehash();
        rehashCt++;
        insert(x);
    }

    public boolean isEmpty(HashEntry<E>[] hashEntries, int location){
        return hashEntries[location] == null;
    }

    private int h1( E x ) {
        int hashVal = x.hashCode( );

        hashVal %= firstArray.length;
        if( hashVal < 0 )
            hashVal += firstArray.length;

        return hashVal;
    }

    private int h2( E x ) {
        int hashVal = x.hashCode( );

        hashVal %= secondArray.length;
        if( hashVal < 0 )
            hashVal += secondArray.length;

        return hashVal;
    }

    public String toString (int limit){
        StringBuilder sb = new StringBuilder();
        int ct=0;
        for (int i=0; i < firstArray.length && ct < limit; i++){
            if (firstArray[i] != null) {
                sb.append( i + ": " + firstArray[i].element + "\n" );
                ct++;
            }
        }
        for (int i=0; i < secondArray.length && ct < limit; i++){
            if (secondArray[i] != null) {
                sb.append( i + ": " + secondArray[i].element + "\n" );
                ct++;
            }
        }
        return sb.toString();
    }

    /**
     * Expand the hash table.
     */
    private void rehash( ) {
        HashEntry<E>[] oldArray1 = firstArray;
        HashEntry<E>[] oldArray2 = secondArray;

        // Create a new double-sized, empty table
        allocateArrays( 2 * oldArray1.length );
        occupiedCt1 = 0;
        occupiedCt2 = 0;

        // Copy table over
        for( HashEntry<E> entry : oldArray1 ) {
            if (entry != null) {
                insert(entry.element);
            }
        }

        for( HashEntry<E> entry : oldArray2 ) {
            if (entry != null) {
                insert(entry.element);
            }
        }
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return true if item is found
     */
    public boolean contains( E x ) {
        int currentPos1 = h1(x);
        int currentPos2 = h2(x);
        if (firstArray[currentPos1] != null && firstArray[currentPos1].element.equals(x)){
            return true;
        } else if (secondArray[currentPos2] != null && secondArray[currentPos2].element.equals(x)){
            return true;
        } return false;
    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    public E find( E x ) {
        int currentPos1 = h1(x);
        int currentPos2 = h2(x);
        if (firstArray[currentPos1] != null && firstArray[currentPos1].element.equals(x)){
            return firstArray[currentPos1].element;
        } else if (secondArray[currentPos2] != null && secondArray[currentPos2].element.equals(x)){
            return secondArray[currentPos2].element;
        } return null;
    }

    /**
     * Make the hash table logically empty.
     */
    public void makeEmpty( ) {
        doClear( );
    }

    private void doClear( ) {
        occupiedCt1 = 0;
        occupiedCt2 = 0;
        for( int i = 0; i < firstArray.length; i++ )
            firstArray[ i ] = null;
        for( int i = 0; i < secondArray.length; i++ )
            secondArray[ i ] = null;
    }

    private static class HashEntry<E> {
        public E element;
        public HashEntry( E e )
        {
            this.element  = e;
        }
    }

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArrays(int arraySize )
    {
        firstArraySize =  nextPrime( arraySize );
        secondArraySize = nextPrime( arraySize + 1 );
        firstArray = new HashEntry[ firstArraySize ];
        secondArray = new HashEntry[ secondArraySize ];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     *
     */
    private static int nextPrime( int n )
    {
        if( n % 2 == 0 )
            n++;

        for( ; !isPrime( n ); n += 2 )
            ;

        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime( int n )
    {
        if( n == 2 || n == 3 )
            return true;

        if( n == 1 || n % 2 == 0 )
            return false;

        for( int i = 3; i * i <= n; i += 2 )
            if( n % i == 0 )
                return false;

        return true;
    }
}

