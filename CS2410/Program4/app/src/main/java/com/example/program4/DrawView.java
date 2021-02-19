package com.example.program4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

public class DrawView extends View {

    private int length;
    Paint paint = new Paint();
    Tree tree = new Tree();
    Context context;
    public DrawView(Context context, int length){
        super(context);
        this.context = context;
        this.length = length;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        tree.generate(length, getWidth() / 2, getHeight(), getWidth() / 2, getHeight() -100);
        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        paint.setStrokeWidth(50);
        drawTree(tree.getRoot(), canvas, 50);
    }

    private void drawTree(Tree.TreeNode node, Canvas canvas, int strokeWidth){
        if (node == null) return;
        paint.setStrokeWidth(strokeWidth);
        node.line.draw(canvas, paint);

        drawTree(node.getLeft(), canvas, strokeWidth / 2);
        drawTree(node.getRight(), canvas, strokeWidth / 2);
    }
}
