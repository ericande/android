package com.example.recyclerdemo;

import android.view.View;

@FunctionalInterface
interface ItemClickListener {
    void onItemClick(View aView, int aPosition);
}
