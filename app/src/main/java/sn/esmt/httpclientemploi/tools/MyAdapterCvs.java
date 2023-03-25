package sn.esmt.httpclientemploi.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sn.esmt.httpclientemploi.R;
import sn.esmt.httpclientemploi.httpconfig.CvsResponses;

public class MyAdapterCvs extends BaseAdapter {


    private ArrayList<CvsResponses> cvs;
    private LayoutInflater myInflater;

    public MyAdapterCvs(Context context, ArrayList<CvsResponses> cvs)
    {
        this.myInflater = LayoutInflater.from(context);
        this.cvs = cvs;
    }

    @Override
    public int getCount() {
        return this.cvs.size();
    }

    @Override
    public Object getItem(int arg0) {
        return this.cvs.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView textId;
        TextView textNom;
        TextView textSpe;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = myInflater.inflate(R.layout.listitem_cvs, null);
            holder = new ViewHolder();
            holder.textId = (TextView) convertView.findViewById(R.id.txtId);
            holder.textNom = (TextView) convertView.findViewById(R.id.txtNom);
            holder.textSpe = (TextView) convertView.findViewById(R.id.txtSpecialite);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textId.setText("Id : " + cvs.get(position).getId()+"");
        holder.textNom.setText("Nom : " + cvs.get(position).getNom());
        holder.textSpe.setText("Specialité : " + cvs.get(position).getSpecialite());
        return convertView;

    }

}
