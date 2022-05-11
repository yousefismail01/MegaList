# Megalist

In this assignment you will implement a generically-typed data structure termed a *megalist*. In addition to practice in rolling your own (node-link style) list implementation, you will also practice:
* implementing interfaces according to their contracts,
* writing defensive/sanity checked methods when appropriate,
* creating UML class diagrams, 
* and thoroughly unit testing your implementation using jUnit.

Start early on this assignment! (*Start now!*)

## Conceptualizing the `IMegaList` Interface

A *megalist* is a variant of a singly linked list that uses the notion of a "handle" to specify insertion and deletion sites for various elements stored in the list.

The `IMegaList` interface conceptualizes this structure as a tuple (ordered pair) of two generic sequences (seq) of type `T`:
> ( left : seq of T, right : seq of T )

Upon initialization, the `left` and `right` sequences that make up the structure are empty.

The *handle* part of the list can be thought of as sitting "in between" `left` and `right`. Visually, the structure is summarized below (assuming `T` has been instantiated by some hypothetical *Tree* type):

<img src="https://github.com/dtwelch/resources/blob/master/311fa21/asg2/mega-list-abstraction.png" width="480">

The handle in the above picture is the pink bar separating both the left and right sides of the megalist.

### Interface Operations

The structure's primary operations are visually summarized in the picture below.

<img src="https://github.com/dtwelch/resources/blob/master/311fa21/asg2/mega-list-abstraction2.png" width="650">

See the `IMegaList` interface included with the starter kit, which includes the full contracts for each method.

## 1. Providing a Singly-Linked Implementation

This section contains notes on one potential way you might implement this structure using a singly linked approach. You must use a node, link/pointer style approach. That is, you are **not** allowed to implement the structure using `java.util.List`.

### 1a. Getting started on the implementation

For your implementation, create a class, call it `SLMegaList` and make sure the header of your class matches the following in order to propogate the generic `E` for your implementation up into the `IMegaList` interface:
```java
public class SLMegaList<E> implements IMegaList<E> { 
   // fields, the constructor, the method overrides, 
   // and the private MNode class detailed below go in here
}
```
Next, add an *inner class* to `SLMegaList` above. We'll use this class to encapsulate the information stored in each node of our list:

```java
private final class MNode {
   E data;     // the generic payload stored in this node
   MNode next; // pointer to next node (or null)
   
   public MNode(MNode prev, E data) {
       this.prev = prev;
       this.data = data;
   }
   
   // this is called a "ternary" if-stmt; the part after '?' produces the string specified;
   // and everything after the colon ':' serves as the else part. So ternary if-stmts 
   // return/produce values and can be written in a single line (in this case, values of type String)
   @Override toString() { return data == null ? "<preFirst>" : data.toString(); }
}
```
Note that we mark it `private` to prevent clients from instantiating internal `MNode` objects (as 'nodes' are strictly an internal implementation detail), and `final` to preclude extension of the `MNode` class.

Next, the `SLMegaList` class itself should track the following as fields (so declare these above the constructor):
* `lenLeft`, `lenRight`: integers denoting the length of the left and right sides of the list, respectively
* `lastInLeft`: a reference of type `MNode` to the last node on the left side of the megalist
* `last` : the very last node in the megalist
* `preFirst` : a so-called 'dummy node' that always sits in front of the leftmost node in the chain

The constructor the `SLMegaList` class should take zero arguments and initialize the fields detailed above as follows:
```java
public SLMegaList() {
   this.preFirst = new MNode(null, null); // dummy node (its next and data hold null initially)
   this.last = preFirst; // last node points to dummy node initially
   this.lastInLeft = preFirst;
   // lenLeft and lenRight both automatically get initialized to 0 if you declare them with type int
}
```

### 2a. Implementing `addToRightAtFront` and `toString`

Next, I recommend starting by writing the `addToRightAtFront(..)` method. Here's a rough sketch of one potential way to implement this:

```
addToRightAtFront (e): // this is pseudocode (not Java! :-)
   create a new MNode m whose next points to the lastInLeft 
       and whose data holds entry e
   
   make the lastInLeft's next field point to m
   
   if the lenRight is zero then
       assign last to m
   end
   
   increment the lenRight field by 1
```

We want our list rendered in the same way it looks in the pictures above. For example, here's some client code for the megalist that uses the above:
```java
IMegaList<Integer> a = new SLMegaList<>();
a.addToRightAtFront(33);
System.out.println(a);   // prints <><33>
a.addToRightAtFront(22);
System.out.println(a);   // prints <><22, 33>
a.addToRightAtFront(11);
System.out.println(a);   // prints <><11, 22, 33>
```
Before we extensively test this method and others, we'll need to implement `toString()` for the `SLMegaList` class. 

Here's a sketch of one idea for rendering the object as a string (again, in pseudocode):
```
toString ():
   declare a string variable res and initialize it to "<"
   declare an MNode variable curr and initialize it preFirst.next
   
   while the curr is not equal to lastInLeft.next do
      res = res + curr.data 
      curr = curr.next      // advance to the next node in the left chain
   end
   
   res = ">" + "<"; // start the opening angle for the right list now
   
   while curr is not null do
      res = res + curr.data 
      curr = curr.next
   end
   return res + ">"
```
**Note:** You'll have to handle the addition of the comma's, see how it was done in class for the doubly linked list (with the boolean flag `first`). You might also consider making the `res` variable a `StringBuilder`, as this would be more efficient than raw immutable Java strings.

*Try to implement both addToRightAtFront(..) **as well as** toString(..) then immediately start writing jUnit tests for addToRightAtFront(..) and begin the inevitable debugging process -- expect plenty of crashes when starting out*

### visualizing the implementation node view

If you're wondering what this implementation might look like when drawn, here's a node like style drawing of the following megalist: `<1><2, 3, 4>`:


<img src="https://github.com/dtwelch/resources/blob/master/311fa21/asg2/node-view.png" width="650">


## 2. jUnit Testing

As you implement each method of the assignment, you should (for the method most recently implemented) start writing some tests exercising the method.

Here's an example a `testFundamentals1()` unit test method I wrote:

```java
@Test // this is in the class which will contain all of your unit tests
public void testFundamentals1() {
   IMegaList<Integer> a = new SLMegaList<>();

   Assertions.assertEquals(0, a.leftLength());
   Assertions.assertEquals(0, a.rightLength());
   Assertions.assertEquals("<><>", a.toString());
   
   a.addToRightAtFront(4);
   Assertions.assertEquals(0, a.leftLength());
   Assertions.assertEquals(1, a.rightLength());
   Assertions.assertEquals("<><4>", a.toString());
}
```

As shown, it's not a bad idea to sometimes write some tests where calls to `rightLength()` and `leftLength()` are 'interleaved' between additions (or removals) from the list to help rule out cases where one side of the list isn't updated correctly.

As stated in class, you should strive to extensively test this structure. Try to break it! I will look for *failure* test cases --i.e.: those that go against a `requires` clause, *edge-case/boundary* tests, and routine tests (like the one shown above).

All defensive/sanity checks you do in this assignment can be performed in constant time, O(1), so do them!

### UML 

Lastly create a UML class diagram showing the interface, the implementation, and your test class. Make sure this committed and pushed in the root directory of your project by the deadline.

## Handin and Grading
When you are ready to submit, commit your work by typing:

> git commit -am "message goes here"

then follow this up with a

> git push origin main

You will be graded on the quality of your implementation and its correctness, as determined through the quality and breadth of your jUnit tests.
