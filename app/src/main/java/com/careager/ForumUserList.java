package com.careager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.careager.Adapter.ForumUserListAdapter;
import com.careager.Adapter.ProfileSaleAdapter;
import com.careager.BE.DealerProfileBE;
import com.careager.Configuration.Util;
import com.careager.Constant.Constant;
import com.careager.UI.RecyclerItemClickListener;
import com.careager.careager.R;

public class ForumUserList extends AppCompatActivity {
    ForumUserListAdapter objForumUserListAdapter;

    RecyclerView recList;

    ProgressDialog progressDialog;

    Toolbar toolbar;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_user_list);
        initialize();



        recList.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(getApplicationContext(),ForumChat.class);
                        intent.putExtra("ID",Constant.forumUserID[position]);
                        intent.putExtra("NAME",Constant.forumUserName[position]);
                        startActivity(intent);


                    }

                }));

    }

    private void initialize(){


        recList = (RecyclerView) findViewById(R.id.forum_user_list);
        recList.setHasFixedSize(true);

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog=new ProgressDialog(ForumUserList.this);

        userID= Util.getSharedPrefrenceValue(getApplicationContext(), Constant.SP_LOGIN_ID);

        if(userID!=null){
            String userType=Util.getSharedPrefrenceValue(getApplicationContext(),Constant.SP_LOGIN_TYPE);
            if(userType!=null)
                if(userType.equalsIgnoreCase(Constant.strLoginUser)){
                    new GetForumList().execute(userID);
                }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {

            //Toast.makeText(getApplicationContext(),"BAck Clicked",Toast.LENGTH_SHORT).show();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetForumList extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute() {
            progressDialog.show();
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... params) {

            objForumUserListAdapter=new ForumUserListAdapter(getApplicationContext(),params[0]);
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                recList.setAdapter(objForumUserListAdapter);
            }
            catch (Exception e){
                e.printStackTrace();
            }finally {
                progressDialog.dismiss();
            }

        }
    }

}
