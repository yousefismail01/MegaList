package edu.psu.ist311.megalist;

import java.util.NoSuchElementException;

public class SLMegaList<E> implements IMegaList<E> {

    private int lenLeft;
    private int lenRight;
    private MNode preFirst;
    private MNode last;
    private MNode lastInLeft;

    private final class MNode {
        E data;     // the generic payload stored in this node
        MNode next; // pointer to next node (or null)

        public MNode(MNode next, E data) {
            this.next = next;
            this.data = data;
        }

        // this is called a "ternary" if-stmt; the part after '?' produces the string specified;
        // and everything after the colon ':' serves as the else part. So ternary if-stmts
        // return/produce values and can be written in a single line (in this case, values of type String)
        @Override
        public String toString() {
            return data == null ? "<preFirst>" : data.toString();
        }
    }

    public SLMegaList() {
        this.preFirst = new MNode(null, null); // dummy node (its next and data hold null initially)
        this.last = preFirst; // last node points to dummy node initially
        this.lastInLeft = preFirst;
        // lenLeft and lenRight both automatically get initialized to 0 if you declare them with type int
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("<");
        MNode curr = preFirst.next;
        boolean first = true;
        while (curr != lastInLeft.next) {
            if (first) {
                res.append(curr.data);
                first = false;
            } else {
                res.append(", ").append(curr.data);
            }
            curr = curr.next;  //advance to the next node in the left chain

        }
        res.append(">").append("<");
        first = true;
        while (curr != null) {
            if (first) {
                res.append(curr.data);
                first = false;
            } else {
                res.append(" ").append(curr.data);
            }
            curr = curr.next;
        }
        return res.append(">").toString();


    }


    @Override
    public void addToRightAtFront(E e) {
        //create a new MNode m whose next points to the lastInLeft and whose data holds entry e
        MNode m = new MNode(lastInLeft.next, e);
        //make the lastInLeft's next field point to m
        this.lastInLeft.next = m;
        //if the lenRight is zero then assign last to m
        if (lenRight == 0) {
            last = m;
        }
        lenRight++;

    }

    @Override
    public E removeFromRightAtFront() {
        if (lenRight == 0) {
            throw new NoSuchElementException("Empty right list.");
        }
        MNode curr = lastInLeft.next;
        E dataToReturn = curr.data;
        // <1><2,3,4>
        //Dealing with more than one element
        if (curr.next != null) {
            curr = null;
        } else if (curr == last) {
            last = null;
        }
        return dataToReturn;
    }

    @Override
    public void moveToVeryBeginning() {



        MNode curr = preFirst.next;
        while (lenLeft != 0) {
            addToRightAtFront((E) curr);
            curr = preFirst.next;
        }
    }

    @Override
    public void moveForward() {
        MNode curr = lastInLeft;
        if (lenRight > 0) {
            curr.next = curr;
            lenLeft++;
            lenRight--;
        } else {
            throw new IllegalStateException("Empty right list!!!");
        }

    }

    @Override
    public int leftLength() {
        return lenLeft;
    }

    @Override
    public int rightLength() {
        return lenRight;
    }

    @Override
    public void clear() {
        lastInLeft = null;
        last = null;
        preFirst = null;
        lenRight = 0;
        lenLeft = 0;
    }
}

