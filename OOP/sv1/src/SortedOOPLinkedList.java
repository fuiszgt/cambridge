public class SortedOOPLinkedList extends OOPLinkedList{
    @Override
    public void add(Integer a) {
        if(a == null){
            throw new NullPointerException();
        }
        OOPLinkedListElement element = head;
        while(element.next() != tail && element.next().getValue() > a ){
            element = element.next();
        }
        OOPLinkedListElement newNode = new OOPLinkedListElement(a, element.next());
        element.setNext(newNode);
    }
}
