package afdev.unal.edu.co.webservices;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class WifiZoneAdapter extends RecyclerView.Adapter<WifiZoneAdapter.WifiZoneViewHolder> {

    private List<WifiZone> zonesList;
    Activity activity;

    public WifiZoneAdapter(List<WifiZone> contactList, Activity activity) {
        this.zonesList = contactList;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return zonesList.size();
    }

    @Override
    public void onBindViewHolder(WifiZoneViewHolder contactViewHolder, final int i) {
        final WifiZone ci = zonesList.get(i);
        contactViewHolder.tv_nombre.setText(ci.getNombre());
        contactViewHolder.tv_municipio.setText(ci.getMunicipio() + ", " + ci.getRegion());
        contactViewHolder.tv_direccion.setText(ci.getDireccion());
        contactViewHolder.bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q="+ ci.getLatitud() +"," + ci.getLongitud() + " (" + ci.getNombre() + ")"));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public WifiZoneViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);

        return new WifiZoneViewHolder(itemView);
    }

    class WifiZoneViewHolder extends RecyclerView.ViewHolder {
        TextView tv_nombre;
        TextView tv_municipio;
        TextView tv_direccion;
        Button bt_map;

        WifiZoneViewHolder(View v) {
            super(v);
            tv_nombre = v.findViewById(R.id.tv_nombre);
            tv_municipio = v.findViewById(R.id.tv_municipio);
            tv_direccion = v.findViewById(R.id.tv_direccion);
            bt_map = v.findViewById(R.id.bt_map);
        }
    }
}

