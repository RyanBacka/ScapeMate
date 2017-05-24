package com.scapemate.fullsail.backaryan.scapemate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.scapemate.fullsail.backaryan.scapemate.objects.Bid;

import java.util.ArrayList;



public class BidAdapter extends BaseAdapter {

    private static final int ID_CONSTANT = 0x01000000;
    private ArrayList<Bid> bids;
    private Context context;
    private static final String TAG = "BidAdapter";

    public BidAdapter(Context _context, ArrayList<Bid> _bids){
        context = _context;
        bids = _bids;
    }

    @Override
    public int getCount() {
        if (bids != null) {
            return bids.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (bids!=null && position>=0 && position<bids.size()){
            return bids.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if (bids != null && position >= 0 && position < bids.size()) {
            return ID_CONSTANT + position;
        } else {
            return 0;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layout,parent,false);
            holder.customerName = ((TextView)convertView.findViewById(R.id.customerName));
            holder.bidNum = ((TextView)convertView.findViewById(R.id.bidNum));
            holder.jobType = ((TextView)convertView.findViewById(R.id.jobType));
            holder.bidStatus = ((ImageView)convertView.findViewById(R.id.bidStatus));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bid bid = (Bid) getItem(position);
        holder.customerName.setText(bid.getCustomerName());
        holder.bidNum.setText(bid.getBidNum());
        holder.jobType.setText(bid.getJobType());
        if(bid.getBidStatus()==2){
            holder.bidStatus.setImageResource(R.drawable.denied);
        } else if(bid.getBidStatus()==1){
            holder.bidStatus.setImageResource(R.drawable.accepted);
        } else {
            holder.bidStatus.setImageResource(R.drawable.pending);
        }
        return convertView;
    }

    private static class ViewHolder{
        TextView customerName;
        TextView bidNum;
        TextView jobType;
        ImageView bidStatus;
    }
}
