package com.nav.dexedd.structure;

import java.util.List;

/**
 * Tree-like generic structure.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class Tree<T> {

    private T data;
    private Integer rank;
    private List<Tree<T>> children;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public List<Tree<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Tree<T>> children) {
        this.children = children;
    }

}
