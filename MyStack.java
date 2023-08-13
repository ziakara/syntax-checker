// implementation of a stack that uses an array to store and resize data.

@SuppressWarnings("unchecked")
public class MyStack<T> implements MyStackInterface<T>{
    
    private static final int DEFAULT_CAPACITY = 10;
    private T[] theItems;
    private int theSize;

    public MyStack(){
        theItems = (T[]) new Object[DEFAULT_CAPACITY];
        theSize = 0;
    }
    
    // ensures the stack has enough capacity
    public void ensureCapacity(int newCapacity){
        if (newCapacity < theSize){
            return;
        }
        T[] oldItems = theItems;
        theItems = (T[]) new Object[newCapacity];
        for(int i = 0; i < size(); i++){
            theItems[i] = oldItems[i];
        }
    }
    
    // places item on top of the stack
    public void push(T x){
        if (size() == theItems.length){
            ensureCapacity(size()*2+1);
        }
        theItems[theSize++] = x;
    }
    
    // removes top of stack
	public T pop(){
        T temp = peek();
        theSize--;
        return temp;
    }
    
    // checks the top of the stack
	public T peek(){
        if(isEmpty()){
            return null;
        }
        return theItems[theSize-1];
    }
    
    // if it's empty, will return true, else false
	public boolean isEmpty(){
        if (size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    
    // number of elements
	public int size(){
        return theSize;
    } 
}