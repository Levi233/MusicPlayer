package com.chenhao.musicplayer.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenhao.musicplayer.R;
import com.chenhao.musicplayer.bean.CommentInfo;
import com.chenhao.musicplayer.utils.DateUtils;
import com.chenhao.musicplayer.view.GlideRoundTransform;

/**
 * Created by chenhao on 2016/11/27.
 */

public class CommentAdapter extends SingleRecyclerAdapter<CommentInfo> {

    public CommentAdapter(Context context, CommentInfo commentInfo, int type, Handler handler) {
        super(context, commentInfo, type, handler);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(getContext()).inflate(R.layout.fragment_comment_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Glide.with(getContext())
                .load(getItem().getU_pic())
                .transform(new GlideRoundTransform(getContext()))
                .placeholder(R.mipmap.ic_launcher)
                .into(((CommentHolder)holder).comment_user_pic);
        ((CommentHolder)holder).comment_user_name.setText(getItem().getU_name());
        ((CommentHolder)holder).comment_user_time.setText(DateUtils.commentTimeFormat(getItem().getTime()*1000,true));
        ((CommentHolder)holder).comment_user_msg.setText(getItem().getMsg());

        CommentInfo reply = getItem().getReply();
        if(reply != null){
            ((CommentHolder)holder).reply_layout.setVisibility(View.VISIBLE);
            ((CommentHolder)holder).reply_name_tv.setText("@"+reply.getU_name()+":");
            ((CommentHolder)holder).reply_msg_tv.setText(reply.getMsg());
        }else{
            ((CommentHolder)holder).reply_layout.setVisibility(View.GONE);
        }
    }

    private static class CommentHolder extends RecyclerView.ViewHolder{
        ImageView comment_user_pic;
        TextView comment_user_name;
        TextView comment_user_time;
        TextView comment_user_msg;
        LinearLayout reply_layout;
        TextView reply_name_tv;
        TextView reply_msg_tv;

        public CommentHolder(View itemView) {
            super(itemView);
            comment_user_pic = (ImageView) itemView.findViewById(R.id.comment_user_pic);
            comment_user_name = (TextView) itemView.findViewById(R.id.comment_user_name);
            comment_user_time = (TextView) itemView.findViewById(R.id.comment_user_time);
            comment_user_msg = (TextView) itemView.findViewById(R.id.comment_user_msg);
            reply_name_tv = (TextView) itemView.findViewById(R.id.reply_name_tv);
            reply_msg_tv = (TextView) itemView.findViewById(R.id.reply_msg_tv);
            reply_layout = (LinearLayout) itemView.findViewById(R.id.reply_layout);
        }
    }
}
