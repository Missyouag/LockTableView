package com.rmondjone.locktableview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 说明 第一列数据
 * 作者 郭翰林
 * 创建时间 2017/9/17.
 */

public class LockRowAdapter extends RecyclerView.Adapter<LockRowAdapter.LockViewHolder> {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 第一列数据
     */
    private ArrayList<String> mLockColumnDatas;
    /**
     * 第一行背景颜色
     */
    private int mFristRowBackGroudColor;
    /**
     * 表格头部字体颜色
     */
    private int mTableHeadTextColor;
    /**
     * 表格内容字体颜色
     */
    private int mTableContentTextColor;
    /**
     * 是否锁定首行
     */
    private boolean isLockFristRow = true;
    /**
     * 记录每列最大宽度
     */
    private ArrayList<Integer> mColumnMaxWidths = new ArrayList<Integer>();
    /**
     * 记录每行最大高度
     */
    private ArrayList<Integer> mRowMaxHeights = new ArrayList<Integer>();

    /**
     * 单元格字体大小
     */
    private int mTextViewSize;
    /**
     * 单元格内边距
     */
    private int mCellPaddingLeft;
    private int mCellPaddingTop;
    private int mCellPaddingRight;
    private int mCellPaddingBottom;
    /**
     * Item点击事件
     */
    private LockTableView.OnItemClickListener mOnItemClickListenter;

    /**
     * Item长按事件
     */
    private LockTableView.OnItemLongClickListener mOnItemLongClickListener;

    /**
     * Item项被选中监听(处理被选中的效果)
     */
    private TableViewAdapter.OnItemSelectedListener mOnItemSelectedListener;


    public LockRowAdapter(Context mContext, ArrayList<String> mLockColumnDatas) {
        this.mContext = mContext;
        this.mLockColumnDatas = mLockColumnDatas;
    }


    @Override
    public LockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LockViewHolder holder = new LockViewHolder(LayoutInflater.from(mContext).inflate(R.layout.lock_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(LockViewHolder holder, final int position) {
        //设置布局
        holder.mTextView.setText(mLockColumnDatas.get(position));
        holder.mTextView.setTextSize(mTextViewSize);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.mTextView.getLayoutParams();
        layoutParams.width = mColumnMaxWidths.get(0);
        layoutParams.height = mRowMaxHeights.get(isLockFristRow ? position + 1 : position);
        layoutParams.setMargins(mCellPaddingLeft, mCellPaddingTop, mCellPaddingRight, mCellPaddingBottom);
        holder.mTextView.setLayoutParams(layoutParams);
        //设置颜色
        if (!isLockFristRow && position == 0) {
            holder.mLinearLayout.setBackgroundColor(ContextCompat.getColor(mContext, mFristRowBackGroudColor));
            holder.mTextView.setTextColor(ContextCompat.getColor(mContext, mTableHeadTextColor));
        } else {
            holder.mTextView.setTextColor(ContextCompat.getColor(mContext, mTableContentTextColor));
        }
        //添加事件
        if (mOnItemClickListenter != null) {
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(v, position);
                    }
                    if (isLockFristRow) {
                        mOnItemClickListenter.onItemClick(v, position + 1);
                    } else {
                        if (position != 0) {
                            mOnItemClickListenter.onItemClick(v, position);
                        }
                    }
                }
            });
        }
        if (mOnItemLongClickListener != null) {
            holder.mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(v, position);
                    }
                    if (isLockFristRow) {
                        mOnItemLongClickListener.onItemLongClick(v, position + 1);
                    } else {
                        if (position != 0) {
                            mOnItemLongClickListener.onItemLongClick(v, position);
                        }
                    }
                    return true;
                }
            });
        }
        //如果没有设置点击事件和长按事件
        if (mOnItemClickListenter == null && mOnItemLongClickListener == null) {
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(v, position);
                    }
                }
            });
            holder.mLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(v, position);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLockColumnDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class LockViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        LinearLayout mLinearLayout;

        public LockViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.lock_text);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.lock_linearlayout);
        }
    }


    //取得每行每列应用高宽
    public void setColumnMaxWidths(ArrayList<Integer> mColumnMaxWidths) {
        this.mColumnMaxWidths = mColumnMaxWidths;
    }

    public void setRowMaxHeights(ArrayList<Integer> mRowMaxHeights) {
        this.mRowMaxHeights = mRowMaxHeights;
    }

    public void setTextViewSize(int mTextViewSize) {
        this.mTextViewSize = mTextViewSize;
    }

    public void setLockFristRow(boolean lockFristRow) {
        isLockFristRow = lockFristRow;
    }

    public void setCellPadding(int left, int top, int right, int bottom) {
        mCellPaddingLeft = left;
        mCellPaddingTop = top;
        mCellPaddingRight = right;
        mCellPaddingBottom = bottom;
    }

    public void setFristRowBackGroudColor(int mFristRowBackGroudColor) {
        this.mFristRowBackGroudColor = mFristRowBackGroudColor;
    }

    public void setTableHeadTextColor(int mTableHeadTextColor) {
        this.mTableHeadTextColor = mTableHeadTextColor;
    }

    public void setTableContentTextColor(int mTableContentTextColor) {
        this.mTableContentTextColor = mTableContentTextColor;
    }

    public void setOnItemClickListener(LockTableView.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListenter = onItemClickListener;
    }

    public void setOnItemLongClickListener(LockTableView.OnItemLongClickListener itemLongClickListener) {
        this.mOnItemLongClickListener = itemLongClickListener;
    }

    public void setOnItemSelectedListener(TableViewAdapter.OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }
}
