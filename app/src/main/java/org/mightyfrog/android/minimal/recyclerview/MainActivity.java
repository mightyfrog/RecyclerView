package org.mightyfrog.android.minimal.recyclerview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/**
 * RecyclerView swipe-and-dimiss, drag-and-drop sample code.
 *
 * @author Shigehiro Soejima
 */
public class MainActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    private static final ArrayList<String> mList = new ArrayList<>(100);

    static {
        for (int i = 0; i < 100; i++) {
            mList.add("#" + i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
        setupItemTouchHelper();
    }

    //
    //
    //

    /**
     *
     */
    private void setupItemTouchHelper() {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder selected,
                                  RecyclerView.ViewHolder target) {
                final int from = selected.getAdapterPosition();
                final int to = target.getAdapterPosition();
                Collections.swap(mList, from, to);
                mAdapter.notifyItemMoved(from, to);

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mList.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

    //
    //
    //

    /**
     *
     */
    private static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv;

            /**
             * @param itemView The view.
             */
            public ViewHolder(View itemView) {
                super(itemView);

                tv = (TextView) itemView.findViewById(R.id.text_view);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            viewHolder.tv.setText(mList.get(i));
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_item, viewGroup, false);

            return new ViewHolder(view);
        }
    }
}
