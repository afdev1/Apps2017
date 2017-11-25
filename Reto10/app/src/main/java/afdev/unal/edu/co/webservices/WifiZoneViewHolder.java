package afdev.unal.edu.co.webservices;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class WifiZoneViewHolder extends RecyclerView.ViewHolder {
    private TextView tv_nombre;
    private TextView tv_municipio;

    public WifiZoneViewHolder(View v) {
        super(v);
        tv_nombre = (TextView) v.findViewById(R.id.tv_nombre);
        tv_municipio = (TextView) v.findViewById(R.id.tv_municipio);
    }
}
