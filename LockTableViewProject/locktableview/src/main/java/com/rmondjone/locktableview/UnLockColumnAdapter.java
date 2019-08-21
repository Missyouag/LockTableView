package com.rmondjone.locktableview;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 说明
 * 作者 郭翰林
 * 创建时间 2017/9/17.
 */

public class UnLockColumnAdapter extends RecyclerView.Adapter<UnLockColumnAdapter.UnLockViewHolder> {
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 表格数据
     */
    private ArrayList<ArrayList<String>> mTableDatas;
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
     * 是否锁定首行
     */
    private boolean isLockFirstRow = true;
    /**
     * 是否锁定首列
     */
    private boolean isLockFirstColumn = true;

    /**
     * 单元格内边距
     */
    private int mCellPaddingLeft;
    private int mCellPaddingTop;
    private int mCellPaddingRight;
    private int mCellPaddingBottom;

    /**
     * 表格内容点击类
     */
    private LockTableView.OnTableClickListener mOnTableClickListener;
    /**
     * Item点击事件
     */
    private LockTableView.OnItemClickListener mOnItemClickListener;

    /**
     * Item长按事件
     */
    private LockTableView.OnItemLongClickListener mOnItemLongClickListener;

    /**
     * Item项被选中监听(处理被选中的效果)
     */
    private TableViewAdapter.OnItemSelectedListener mOnItemSelectedListener;

    /**
     * 构造方法
     *
     * @param mContext
     * @param mTableDatas
     */
    public UnLockColumnAdapter(Context mContext, ArrayList<ArrayList<String>> mTableDatas) {
        this.mContext = mContext;
        this.mTableDatas = mTableDatas;
    }


    @Override
    public int getItemCount() {
        return mTableDatas.size();
    }

    @Override
    public UnLockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UnLockViewHolder holder = new UnLockViewHolder(LayoutInflater.from(mContext).inflate(R.layout.unlock_item, null));
        return holder;
    }

    @Override
    public void onBindViewHolder(UnLockViewHolder holder, final int position) {
        if (isLockFirstRow) {
            //第一行是锁定的
            createRowView(holder.mLinearLayout, position, false, mRowMaxHeights.get(position + 1));
        } else {
            if (position == 0) {
                holder.mLinearLayout.setBackgroundColor(ContextCompat.getColor(mContext, mFristRowBackGroudColor));
//                createRowView(holder.mLinearLayout, position, true, mRowMaxHeights.get(position));
            }
            createRowView(holder.mLinearLayout, position, position == 0, mRowMaxHeights.get(position));
        }
        //添加事件
        if (mOnItemClickListener != null) {
            holder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemSelectedListener != null) {
                        mOnItemSelectedListener.onItemSelected(v, position);
                    }
                    if (isLockFirstRow) {
                        mOnItemClickListener.onItemClick(v, position + 1);
                    } else {
                        if (position != 0) {
                            mOnItemClickListener.onItemClick(v, position);
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
                    if (isLockFirstRow) {
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
        if (mOnItemClickListener == null && mOnItemLongClickListener == null && mOnTableClickListener == null) {
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
    public int getItemViewType(int position) {
        return position;
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

    public void setLockFirstRow(boolean lockFirstRow) {
        isLockFirstRow = lockFirstRow;
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

    public void setLockFirstColumn(boolean lockFirstColumn) {
        isLockFirstColumn = lockFirstColumn;
    }

    public void setOnItemClickListener(LockTableView.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(LockTableView.OnItemLongClickListener itemLongClickListener) {
        this.mOnItemLongClickListener = itemLongClickListener;
    }

    public void setOnItemSelectedListener(TableViewAdapter.OnItemSelectedListener onItemSelectedListener) {
        this.mOnItemSelectedListener = onItemSelectedListener;
    }

    public void setCellPadding(int left, int top, int right, int bottom) {
        mCellPaddingLeft = left;
        mCellPaddingTop = top;
        mCellPaddingRight = right;
        mCellPaddingBottom = bottom;
    }

    public LockTableView.OnTableClickListener getOnTableClickListener() {
        return mOnTableClickListener;
    }

    /**
     * 注意和其他冲突
     *
     * @param onTableClickListener
     */
    public void setOnTableClickListener(LockTableView.OnTableClickListener onTableClickListener) {
        this.mOnTableClickListener = onTableClickListener;
    }

    class UnLockViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;

        public UnLockViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.unlock_linearlayout);
        }
    }

    /**
     * 构造每行数据视图
     *
     * @param linearLayout
     * @param position
     * @param isFirstRow   是否是第一行
     */
    private void createRowView(LinearLayout linearLayout, final int position, boolean isFirstRow, int mMaxHeight) {
        ArrayList<String> datas = mTableDatas.get(position);
        //设置LinearLayout
        linearLayout.removeAllViews();//首先清空LinearLayout,复用会造成重复绘制，使内容超出预期长度
        for (int i = 0; i < datas.size(); i++) {
            //构造单元格
            TextView textView = new TextView(mContext);
            if (isFirstRow) {
                textView.setTextColor(ContextCompat.getColor(mContext, mTableHeadTextColor));
            } else {
                textView.setTextColor(ContextCompat.getColor(mContext, mTableContentTextColor));
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextViewSize);
            textView.setGravity(Gravity.CENTER);
            textView.setText(datas.get(i));
            //设置布局
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            textViewParams.setMargins(mCellPaddingLeft, mCellPaddingTop, mCellPaddingRight, mCellPaddingBottom);
            textViewParams.height = DisplayUtil.dip2px(mContext, mMaxHeight);
            if (isLockFirstColumn) {
                textViewParams.width = DisplayUtil.dip2px(mContext, mColumnMaxWidths.get(i + 1));
            } else {
                textViewParams.width = DisplayUtil.dip2px(mContext, mColumnMaxWidths.get(i));
            }
            textView.setLayoutParams(textViewParams);
            linearLayout.addView(textView);
            if (null != mOnTableClickListener) {
                final int finalI = isLockFirstColumn ? i + 1 : i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mOnTableClickListener) mOnTableClickListener.onItemClick(view, position, finalI);
                    }
                });
            }
            //画分隔线
            if (i != datas.size() - 1) {
                View splitView = new View(mContext);
                ViewGroup.LayoutParams splitViewParmas = new ViewGroup.LayoutParams(DisplayUtil.dip2px(mContext, 1),
                        ViewGroup.LayoutParams.MATCH_PARENT);
                splitView.setLayoutParams(splitViewParmas);
                if (isFirstRow) {
                    splitView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
                } else {
                    splitView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_gray));
                }
                linearLayout.addView(splitView);
            }
        }
    }
}
