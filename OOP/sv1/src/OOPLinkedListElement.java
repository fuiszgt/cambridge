public class OOPLinkedListElement {
    private Integer value;
    private OOPLinkedListElement next;

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setNext(OOPLinkedListElement next) {
        this.next = next;
    }

    public OOPLinkedListElement(Integer value, OOPLinkedListElement next) {
        this.value = value;
        this.next = next;
    }

    public Integer getValue() {
        return value;
    }

    public OOPLinkedListElement next() {
        return next;
    }
}
