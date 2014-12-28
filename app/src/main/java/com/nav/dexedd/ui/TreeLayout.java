package com.nav.dexedd.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.nav.dexedd.adapter.TreeAdapter;

import java.util.List;

/**
 * A simple tree-like layout.
 *
 * @author Eduardo Naveda
 * @since 0.0.1
 */
public class TreeLayout extends LinearLayout {

    private TreeAdapter treeAdapter;

    public TreeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    public void setTreeAdapter(TreeAdapter treeAdapter) {
        this.treeAdapter = treeAdapter;
        setViews();
    }

    @SuppressWarnings("unchecked")
    private void setViews() {
        List<View> views = treeAdapter.prepareViews();
        for (View view : views) {
            addView(view);
        }
    }

}
