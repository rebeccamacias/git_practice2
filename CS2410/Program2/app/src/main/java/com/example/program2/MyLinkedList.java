package com.example.program2;

public class MyLinkedList {
    public urlNode<String> head;

    private class urlNode<String> {
        public String url;
        public urlNode<String> next;
        public urlNode<String> prev;

        public urlNode(String url){
            this.url = url;
            this.next = null;
            this.prev = null;
        }
    }

    public void insert(String url){
        if(head == null) { //first case
            head = new urlNode<String>(url);
        }
        else {
            urlNode<String> currNode = head;
            urlNode<String> newNode = new urlNode<String>(url);
            while (currNode.next != null) {
                currNode = currNode.next;
            }
            currNode.next = newNode;
            newNode.prev = currNode;
        }
    }

    public urlNode<String> getNode(String url){
        if(head == null){
            return null;
        }
        urlNode<String> currNode = head;
        while (!currNode.url.equals(url)){
            currNode = currNode.next;
        }
        return currNode;
    }

    public String forward(urlNode<String> current){
        if(current == null) {
            return null;
        }
        if(current.next == null){
            return null;
        }
        return current.next.url;
    }
    public String backward(urlNode<String> current){
        if(current == null){
            return null;
        }
        if(current.prev == null){
            return null;
        }
        return current.prev.url;
    }
}
