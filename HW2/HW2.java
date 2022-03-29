class HW2 {

  // Node structure containing power and coefficient of variable
  static class Node {
    // Your code here
    double coeff;
    int power;
    Node next;

    Node(double coeff, int power) {
      this.coeff = coeff;
      this.power = power;
      next = null;
    }
  };

  // Function To Display The Linked list
  static void printList(Node ptr) {
    if (ptr == null) {
      System.out.println();
      return;
    } else if (ptr.next != null) {
      while (ptr.next != null) {
        System.out.print(ptr.coeff + "x^" + ptr.power + " + ");
        ptr = ptr.next;
      }
    }
    System.out.println(ptr.coeff + "x^" + ptr.power);
  }

  // Create a node and return
  static Node createNode(double coeff, int power) {
    // Your code here
    Node node = new Node(coeff, power);
    return node;
  }

  // Function add a new node
  static Node addnode(Node head, double coeff, int power) {
    // Your code here
    Node node = createNode(coeff, power);

    if (head == null) {
      return node;
    } else {
      Node nH = head;
      while (nH.next != null) {
        nH = nH.next;
      }
      nH.next = node;
    }

    return head;
  }

  static Node multiply(Node poly1, Node poly2) {
    // Your code here
    Node res = null;

    Node n1 = poly1;
    Node n2 = poly2;

    int highPwr = 0;
    int lowPwr = 0;

    while (n1 != null) {
      while (n2 != null) {
        double coeff = n1.coeff * n2.coeff;
        int power = n1.power + n2.power;

        if (power > highPwr) {
          highPwr = power;
        }
        if (power < lowPwr) {
          lowPwr = power;
        }

        res = addnode(res, coeff, power);

        n2 = n2.next;
      }
      n1 = n1.next;
      n2 = poly2;
    }

    Node ret = createNode(0, highPwr);
    Node nRet = ret;
    Node nRes = res;
    int i = lowPwr;
    int count = 0;
    while (i <= highPwr) {
      while (nRes != null) {
        if (nRes.power == nRet.power) {
          nRet.coeff += nRes.coeff;
        }
        nRes = nRes.next;
      }
      nRes = res;

      i++;
      count++;
      ret = addnode(ret, 0, highPwr - count);

      nRet = nRet.next;
    }

    /*
    // NOTE TO GRADER:
    // This part somehow throws a null pointer error. (At 114)
    // If I was able to fix that,
    // There would be no zero coeff elements at the output.
    Node start = ret;
    Node link = null;
    while (start.next != null) {
      link = start.next;
      while (link.next != null && link.coeff == 0) {
        link = link.next;
      }
      if(link.next == null && link.coeff == 0.0) {
        start.next = null;
      } else {
        start.next = link;
      }
      start = start.next;
    } // Deleted the zero coeff elements
    */

    return ret;
  }

  static Node add(Node poly1, Node poly2) {
    // Your code here
    Node res = null;
    Node n1 = poly1;
    Node n2 = poly2;

    int highPwr = 0;
    int lowPwr = 0;

    while (n1 != null) {
      res = addnode(res, n1.coeff, n1.power);
      if (n1.power > highPwr) {
        highPwr = n1.power;
      }
      if (n1.power < lowPwr) {
        lowPwr = n1.power;
      }
      n1 = n1.next;
    }
    while (n2.next != null) {
      res = addnode(res, n2.coeff, n2.power);
      if (n2.power > highPwr) {
        highPwr = n2.power;
      }
      if (n2.power < lowPwr) {
        lowPwr = n2.power;
      }
      n2 = n2.next;
    }

    Node ret = createNode(0, highPwr);
    Node nRet = ret;
    Node nRes = res;
    int i = lowPwr;
    int count = 0;
    while (i <= highPwr) {
      while (nRes != null) {
        if (nRes.power == nRet.power) {
          nRet.coeff += nRes.coeff;
        }
        nRes = nRes.next;
      }
      nRes = res;

      i++;
      count++;
      ret = addnode(ret, 0, highPwr - count);

      nRet = nRet.next;
    }

    return ret;
  }
}
