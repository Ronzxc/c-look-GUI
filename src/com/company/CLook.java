package com.company;

import java.util.*;


public class CLook {
    static Scanner scan = new Scanner(System.in);  // Create a Scanner object

    static int direction;

    static int request_size, disk_size;
    static int distance, cur_track;
    static int seek_count = 0;

    static Vector<Integer> left = new Vector<Integer>();
    static Vector<Integer> right = new Vector<Integer>();
    static Vector<Integer> seek_sequence = new Vector<Integer>();

    // Function to perform C-LOOK on the request
    // array starting from the given head
    public static void CLOOK(ArrayList<Integer> request_arraylist, int head) {
        //int seek_count = 0;
        //int distance, cur_track;


        for(int i = 0; i < request_size; i++) {
            //System.out.println("index " + i + request_arraylist.get(i));

            if ((request_arraylist.get(i)) < head)
                left.add((request_arraylist.get(i)));
            if ((request_arraylist.get(i))  >  head)
                right.add((request_arraylist.get(i)));
        }

//	    System.out.println("left = " + left);
//	    System.out.println("right = " + right);

        sorting(head, left, right);
    }


    public static void sorting(int head, Vector<Integer> left, Vector<Integer> right) {

        // Sorting left and right vectors
        Collections.sort(left);
        Collections.sort(right);

        if (direction == 0) {
            GoingDown(head, left, right);
            print();
        }

        else{
            GoingUp(head, left, right);
            print();
        }

    }

    // First service the requests
    // on the right side of the
    // head
    public static void GoingUp (int head,  Vector<Integer> left, Vector<Integer> right) {
        for(int i = 0; i < right.size(); i++) {
            cur_track = right.get(i);

            // Appending current track
            // to seek sequence
            seek_sequence.add(cur_track);

            // Calculate absolute distance
            distance = Math.abs(cur_track - head);

            // Increase the total count
            seek_count += distance;

            // Accessed track is now new head
            head = cur_track;
        }

        // Once reached the right end
        // jump to the last track that
        // is needed to be serviced in
        // left direction
        seek_count += Math.abs(head - left.get(0));
        head = left.get(0);

        for(int i = 0; i < left.size(); i++) {
            cur_track = left.get(i);

            // Appending current track to
            // seek sequence
            seek_sequence.add(cur_track);

            // Calculate absolute distance
            distance = Math.abs(cur_track - head);

            // Increase the total count
            seek_count += distance;

            // Accessed track is now the new head
            head = cur_track;
        }


    }

    public static void GoingDown (int head,  Vector<Integer> left, Vector<Integer> right) {
        // Now service the requests again
        // which are left
        for(int i = left.size()-1; i >= 0; i--) {
            cur_track = left.get(i);

            // Appending current track to
            // seek sequence
            seek_sequence.add(cur_track);

            // Calculate absolute distance
            distance = Math.abs(cur_track - head);

            // Increase the total count
            seek_count += distance;

            // Accessed track is now the new head
            head = cur_track;
        }
        seek_count += Math.abs(head - right.get(0));
        head = right.get(0);

        for(int i = right.size()-1; i >=0 ; i--) {
            cur_track = right.get(i);

            // Appending current track
            // to seek sequence
            seek_sequence.add(cur_track);

            // Calculate absolute distance
            distance = Math.abs(cur_track - head);

            // Increase the total count
            seek_count += distance;

            // Accessed track is now new head
            head = cur_track;
        }


    }

    public static void print () {
        System.out.println("Total number of seek " +"operations = " + seek_count);

        System.out.println("Seek Sequence is");

        for(int i = 0; i < seek_sequence.size(); i++) {
            System.out.println(seek_sequence.get(i));
        }
    }

    public static void request () {
        int head, input;
        ArrayList<Integer> request_arraylist = new ArrayList<Integer>();

        System.out.println("Input disk size: ");
        disk_size = scan.nextInt();

        System.out.println("Input head start: ");
        head = scan.nextInt();

        System.out.println("Input number of requests: ");
        request_size = scan.nextInt();

        System.out.println("Input requests: ");
        for (int i = 0; i < request_size ; i++) {
            input = scan.nextInt();
            request_arraylist.add(input);
        }
        System.out.println("What direction? (Press 0 if going down/going left, Press 1 if going up/going right)");
        direction = scan.nextInt();
        seek_sequence.add(head);

        //System.out.println("array list " + request_arraylist);

        CLOOK(request_arraylist, head);
    }

    // Driver code
    public static void main(String []args) {
        request();

    }
}
