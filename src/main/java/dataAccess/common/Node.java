package dataAccess.common;

import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Node<T> {

    public static Node<java.lang.String> rootNode(){
        return new Node<String>("root");
    }

    private T data = null;

    private List<Node<T>> children = new ArrayList<>();

    private Node<T> parent = null;

    public Node(T data) {
        this.data = data;
    }

    protected Node(T data,Node<T> parent) {
        this.data = data;
        this.parent=parent;
    }


    public Node<T> addChild(T childData) {
        return addChild(new Node<T>(childData,this));
    }

    public Node<T> addChild(Node<T> child) {
        if(child==null){
            return this;
        }
        child.setParent(this);
        this.children.add(child);
        return this;
    }

    public Node<T> addChildrenByData(T... childrenData) {
        if(childrenData==null || childrenData[0]==null){
            return this;
        }
        List<Node<T>> children= Arrays.stream(childrenData).map(o->new Node<T>(o,this)).collect(Collectors.toList());
        this.children.addAll(children);
        return this;
    }

    public  Node<T> addChildren(Node<T>... children) {
        if(children==null || children[0]==null){
            return this;
        }
        for(Node<T> child:children) {
            child.setParent(this);
            this.children.add(child);
        }
        return this;
    }

    public  Node<T> addChildren(List<Node<T>> children) {
        if(children==null){
            return this;
        }
        for(Node<T> child:children) {
            child.setParent(this);
            this.children.add(child);
        }
        return this;
    }

    public boolean hasChildren(){
        return getChildren()==null?false:getChildren().size()>0;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public boolean containsData(T data){
        if(getData()!=null && getData().equals(data)){
            return true;
        }
        if(getChildren()==null){
            return false;
        }
        for(Node<T> child: getChildren()){
            if(child.containsData(data)){
                return true;
            }
        }
        return false;
    }

    public boolean hasData(){
        return getData()!=null;
    }

    public T getData() {
        return data;
    }

    private void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public Node<T> getParent() {
        return parent;
    }
}
