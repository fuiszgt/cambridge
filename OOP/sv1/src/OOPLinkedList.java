public class OOPLinkedList {
    protected final OOPLinkedListElement tail = new OOPLinkedListElement(null, null);
    protected final OOPLinkedListElement head = new OOPLinkedListElement(null, tail);
    private int length = 0;

    // Add element after head
    public void add(Integer a){
        head.setNext(new OOPLinkedListElement(a, head.next()));
        length++;
    }

    public Integer nth(int n){
        OOPLinkedListElement element = head.next();
        if(length<=n){
            throw new ArrayIndexOutOfBoundsException();
        }
        while(element != tail && n>0){
            n--;
            element= element.next();
        }
        if(n==0){
            return element.getValue();
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
