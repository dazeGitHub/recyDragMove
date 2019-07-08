package com.kuyue.wechatpublishimagesdrag;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.hys.utils.ToastUtils;

import java.util.ArrayList;

/**
 * 图片拖拽 Activity
 * Created by kuyue on 2017/7/13 上午10:21.
 * 邮箱:595327086@qq.com
 **/
public class PostImagesActivity extends AppCompatActivity {

    private PostArticleImgAdapter postArticleImgAdapter;
    private ItemTouchHelper itemTouchHelper;
    private RecyclerView rcvImg;
    private TextView tv;//删除区域提示
    private ArrayList<String> pathList;


//    public static void startPostActivity(Context context, ArrayList<String> images) {
//        Intent intent = new Intent(context, PostImagesActivity.class);
//        intent.putStringArrayListExtra("img", images);
//        context.startActivity(intent);
//    }

    public static void startPostActivity(Context context) {
        Intent intent = new Intent(context, PostImagesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_images);
        initView();
    }

    private void initView() {
        rcvImg = (RecyclerView) findViewById(R.id.rcv_img);
        tv = (TextView) findViewById(R.id.tv_drag_delete);
        initRcv();
    }

    private void initRcv() {

        pathList = new ArrayList<String>();
        pathList.add("http://c2.78dm.net/1f1cf60bb84944f2dac661bf6e4718c2?imageMogr2/auto-orient/thumbnail/!80p/quality/95/interlace/1");
        pathList.add("http://c2.78dm.net/ee28fcbc5112fd243a9066bad551e0ca?imageMogr2/auto-orient/thumbnail/!80p/quality/95/interlace/1");
        pathList.add("http://c2.78dm.net/4f1e3af93cf69bbc8370dde18d08719a?imageMogr2/auto-orient/thumbnail/!80p/quality/95/interlace/1");
        pathList.add("http://c2.78dm.net/c16d28e605e9c4ff26227fd51027cb61?imageMogr2/auto-orient/thumbnail/!80p/quality/95/interlace/1");
        pathList.add("http://bbs.78dm.net/data/attachment/forum/201907/04/144328v4fjuzfdd4vvf4j5.jpg");

        postArticleImgAdapter = new PostArticleImgAdapter(this, pathList);
        rcvImg.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rcvImg.setAdapter(postArticleImgAdapter);


        MyRecyItemTouchCallBack myRecyItemTouchCallBack = new MyRecyItemTouchCallBack(postArticleImgAdapter,
                pathList,
                (int)(getResources().getDimension(R.dimen.article_post_delete)));
        itemTouchHelper = new ItemTouchHelper(myRecyItemTouchCallBack);
        itemTouchHelper.attachToRecyclerView(rcvImg);//绑定RecyclerView

        //事件监听
        rcvImg.addOnItemTouchListener(new OnRecyclerItemClickListener(rcvImg) {

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                ToastUtils.getInstance().show(MyApplication.getInstance().getContext(), "预览图片");
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //如果item不是最后一个，则执行拖拽
                if (vh.getLayoutPosition() != pathList.size() - 1) {
                    itemTouchHelper.startDrag(vh);
                }
            }
        });

        myRecyItemTouchCallBack.setDragListener(new MyRecyItemTouchCallBack.DragListener() {
            @Override
            public void deleteState(boolean delete) {
                if (delete) {
                    tv.setBackgroundResource(R.color.holo_red_dark);
                    tv.setText(getResources().getString(R.string.post_delete_tv_s));
                } else {
                    tv.setText(getResources().getString(R.string.post_delete_tv_d));
                    tv.setBackgroundResource(R.color.holo_red_light);
                }
            }

            @Override
            public void dragState(boolean start) {
                if (start) {
                    tv.setVisibility(View.VISIBLE);
                } else {
                    tv.setVisibility(View.GONE);
                }
            }

            @Override
            public void clearView() {
//                refreshLayout();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
