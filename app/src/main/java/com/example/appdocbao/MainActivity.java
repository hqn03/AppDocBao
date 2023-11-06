package com.example.appdocbao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

public class MainActivity extends AppCompatActivity {
    ListView lvTieuDe;
    ArrayList<String> arrayLink;
    ArrayList<TinTuc> arrayNews;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTieuDe = findViewById(R.id.listviewTieuDe);
        arrayLink = new ArrayList<>();
        arrayNews = new ArrayList<>();

        adapter = new CustomAdapter(MainActivity.this, R.layout.item_news, arrayNews);
        lvTieuDe.setAdapter(adapter);
        Log.i("size array",""+arrayNews.size());
        new ReadRSS().execute("https://vnexpress.net/rss/oto-xe-may.rss");
        lvTieuDe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                intent.putExtra("linkTinTuc",arrayLink.get(position));
                startActivity(intent);
            }
        });
    }
    private class ReadRSS extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                InputStreamReader inputStreamReader = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line="";
                while ((line=bufferedReader.readLine())!=null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String tieuDe = "";
            String anh ="";

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                tieuDe = parser.getValue(element, "title");
                //get URL image
                NodeList nodeList1 = element.getElementsByTagName("description");
                Element line = (Element) nodeList1.item(0);
                Matcher matcher = Pattern.compile("<img src=\"([^\"]+)").matcher(getCharacterDataFromElement(line));
                if(matcher.find()) {
//                    Log.i("img url: ", "" + matcher.group(1));
                    anh = matcher.group(1);
                }
                    arrayLink.add(parser.getValue(element, "link"));
                    arrayNews.add(new TinTuc(tieuDe,anh));
                }
                adapter.notifyDataSetChanged();
            }
        }
        //Lay du lieu tu Element
        private String getCharacterDataFromElement(Element line) {
            // TODO Auto-generated method stub
            Node child = line.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
            return "";
        }
}