package com.nav.dexedd.adapter;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

import com.google.common.collect.TreeTraverser;
import com.nav.dexedd.structure.Tree;

import java.util.ArrayList;
import java.util.List;

public abstract class TreeAdapter<T> {

    public static enum TraversalType {
        BREADTH_FIRST, PRE_ORDER, POST_ORDER;
    }

    private Context context;
    protected int layoutResourceId;
    private Tree<T> tree;
    private TraversalType traversalType;


    public TreeAdapter(Context context, int layoutResourceId, Tree<T> tree, TraversalType traversalType) {
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.tree = tree;
        this.traversalType = traversalType;
    }

    public List<View> prepareViews() {
        TreeTraverser<Tree<T>> traverser = new TreeTraverser<Tree<T>>() {
            @Override
            public Iterable<Tree<T>> children(Tree<T> root) {
                return root.getChildren();
            }
        };

        Iterable<Tree<T>> iterable;

        switch (this.traversalType) {
            case BREADTH_FIRST:
                iterable = traverser.breadthFirstTraversal(tree);
                break;
            case PRE_ORDER:
                iterable = traverser.preOrderTraversal(tree);
                break;
            case POST_ORDER:
                iterable = traverser.postOrderTraversal(tree);
                break;
            default:
                iterable = traverser.breadthFirstTraversal(tree);
        }

        List<View> views = new ArrayList<>();

        for (Tree<T> i : iterable) {
            View view = getView(layoutResourceId, i);

            // todo process margin think on branch icons
            views.add(view);
        }

        return views;
    }

    public abstract View getView(int layoutResourceId, Tree<T> i);

    public Context getContext() {
        return context;
    }

}
