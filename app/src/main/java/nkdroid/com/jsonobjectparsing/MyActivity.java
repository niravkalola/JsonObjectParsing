package nkdroid.com.jsonobjectparsing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.util.ArrayList;

public class MyActivity extends Activity {

    private ArrayList<BeanTopic> topicArrayList;
    private ProgressDialog progressDialog;
    private TextView txtSubject, txtPrice,txtAuther,txtTopicsList;
    private StringBuffer topicList;
    BeanSubject beanSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        txtSubject=(TextView)findViewById(R.id.txtSubject);
        txtPrice =(TextView)findViewById(R.id.txtDate);
        txtAuther=(TextView)findViewById(R.id.txtAuther);
        txtTopicsList=(TextView)findViewById(R.id.txtTopicsList);


        new AsyncTask<Void,Void,Void>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog=new ProgressDialog(MyActivity.this);
                progressDialog.setCancelable(false);
                progressDialog.setMessage("Loading...");
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                Reader reader=API.getData("http://beta.json-generator.com/api/json/get/OkS85Le");
                beanSubject = new GsonBuilder().create().fromJson(reader, BeanSubject.class);
                Log.e("Subject: ", beanSubject.getSubject()+"");
                Log.e("Auther: ", beanSubject.getAuther()+"");
                Log.e("Price: ", beanSubject.getPrice()+"");
                topicArrayList=beanSubject.getBeanTopics();
                topicList=new StringBuffer();
                for(BeanTopic topic: topicArrayList){
                    Log.e("topic title: ",topic.getTitle()+"");
                    topicList.append("* "+topic.getTitle()+"\n");
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressDialog.dismiss();

                txtSubject.setText("Subject: "+beanSubject.getSubject());
                txtPrice.setText("price: "+beanSubject.getPrice());
                txtAuther.setText("Auther: "+beanSubject.getAuther());
                txtTopicsList.setText("Topics: "+"\n"+topicList);
            }
        }.execute();
    }
}
