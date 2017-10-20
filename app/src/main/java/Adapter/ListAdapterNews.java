package Adapter;

import android.content.Context;
//import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.burcu.haberoku.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import Model.NewsMO;
/**
 * Created by Burcu on 18.10.2017.
 */
public class ListAdapterNews extends ArrayAdapter<NewsMO> implements View.OnClickListener{
    private ArrayList<NewsMO> haberler;            //Modelden oluşturulan haberleri tutan listeyi oluşturduk
    Context context;

    private static class ViewHolder{               //CustomLayout'ta kullanılan nesneleri tanımladık
        ImageView haberResim;
        TextView haberBaslik;
        TextView haberIcerik;
    }

    public ListAdapterNews(ArrayList<NewsMO> haberler, Context context){    //Class'ımız ilk çalıştığında çalışacak
        super(context, R.layout.haberler_listview, haberler);            // olan metod.
        this.haberler = haberler;                                           //Burada metod içine gelen haberler
        this.context = context;                                             // listemizi clasın içinde kullanabilmek için
    }                                                                       // Classtaki değişkene atıyoruz.

    @Override
    public void onClick(View v) {                                           //İtem için Clik Event'i ekliyoruz.
        int position = (Integer) v.getTag();
        Object object = getItem(position);
        NewsMO haberler = (NewsMO)object;
        switch (v.getId()){
            case R.id.haberIcerik:
               // Snackbar.make(v, "Haber Baslıgı: "+ haberler.getTitle().toString(), Snackbar.LENGTH_LONG).setAction("No Action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        try {
            NewsMO haberler = getItem(position);                 //Gelen listemizin içindeki verilerden şuan oluşturacağımız
            ViewHolder viewHolder;                               // haberin verilerini haberler değişkenine atıyoruz.

            final View result;

            if (convertView == null) {             //Yeni itemi oluşturmak için kullandığımız kısım
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.haberler_listview, parent, false);
                viewHolder.haberResim = (ImageView) convertView.findViewById(R.id.haberResim);
                viewHolder.haberBaslik = (TextView) convertView.findViewById(R.id.haberBaslik);
                viewHolder.haberIcerik = (TextView) convertView.findViewById(R.id.haberIcerik);
                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            viewHolder.haberBaslik.setText(haberler.getTitle());          //Haberler değişkeni içinde bulunan başlığı ve açıklamayı
            viewHolder.haberIcerik.setText(haberler.getDescription());    // oluşturduğumuz item'in içindeki TextView'lere atıyoruz.
            if (haberler.getImgUrl()==""){
                Picasso.with(context).load("http://www.bos.com/wp-content/uploads/2015/09/BOS_Logo_Red_Large.jpg").into(viewHolder.haberResim);
            }    //Haberlerin resimleri boşsa linkteki resmi çekiyoruz.
            else {
                Log.d("resim url : ",haberler.getImgUrl());
                Picasso.with(context).load(haberler.getImgUrl()).into(viewHolder.haberResim);
            }   //Picasso kütüphanesinden yararlanarak haberlerin resimlerini çekiyoruz ve itemdeki ImageView'e atıyoruz.

            viewHolder.haberResim.setOnClickListener(this);
            viewHolder.haberResim.setTag(position);

            return convertView;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }
}
