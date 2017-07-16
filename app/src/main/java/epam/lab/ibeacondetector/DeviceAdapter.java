package epam.lab.ibeacondetector;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import epam.lab.util.DateUtil;
import epam.lab.util.ScannedDevice;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class DeviceAdapter extends ArrayAdapter<ScannedDevice> {
    private static final String PREFIX_RSSI = "RSSI:";
    private static final String PREFIX_LASTUPDATED = "Last Udpated:";
    private List<ScannedDevice> mList;
    private LayoutInflater mInflater;
    private int mResId;

    public DeviceAdapter(Context context, int resId, List<ScannedDevice> objects) {
        super(context, resId, objects);
        mResId = resId;
        mList = objects;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScannedDevice item = (ScannedDevice) getItem(position);

        if (convertView == null) {
            convertView = mInflater.inflate(mResId, null);
        }
        TextView name = (TextView) convertView.findViewById(R.id.device_name);
        name.setText(item.getDisplayName());
        TextView address = (TextView) convertView.findViewById(R.id.device_address);
        address.setText(item.getDevice().getAddress());
        TextView rssi = (TextView) convertView.findViewById(R.id.device_rssi);
        rssi.setText(PREFIX_RSSI + Integer.toString(item.getRssi()));

        TextView proximity = (TextView) convertView.findViewById(R.id.device_proximity);
        proximity.setText("Estimated proximity: " + Double.toString(item.getProximity()));
        TextView lastupdated = (TextView) convertView.findViewById(R.id.device_lastupdated);
        lastupdated.setText(PREFIX_LASTUPDATED + DateUtil.get_yyyyMMddHHmmssSSS(item.getLastUpdatedMs()));

        TextView ibeaconInfo = (TextView) convertView.findViewById(R.id.device_ibeacon_info);
        Resources res = convertView.getContext().getResources();
        if (item.getIBeacon() != null) {
            ibeaconInfo.setText(res.getString(R.string.label_ibeacon) + "\n" + item.getIBeacon().toString());
        } else {
            ibeaconInfo.setText(res.getString(R.string.label_not_ibeacon));
        }
        TextView scanRecord = (TextView) convertView.findViewById(R.id.device_scanrecord);
        scanRecord.setText("Scan Record, i.e. advertisment: " + item.getScanRecordHexString());

        return convertView;
    }

    public void update(BluetoothDevice newDevice, int rssi, byte[] scanRecord) {
        if ((newDevice == null) || (newDevice.getAddress() == null)) {
            return;
        }
        long now = System.currentTimeMillis();

        boolean contains = false;
        for (ScannedDevice device : mList) {
            if (newDevice.getAddress().equals(device.getDevice().getAddress())) {
                contains = true;
                // update
                device.setRssi(rssi);
                device.setLastUpdatedMs(now);
                device.setScanRecord(scanRecord);
                break;
            }
        }
        if (!contains) {
            // add new BluetoothDevice
            mList.add(new ScannedDevice(newDevice, rssi, scanRecord, now));
        }

        // sort by RSSI
        Collections.sort(mList, new Comparator<ScannedDevice>() {
            @Override
            public int compare(ScannedDevice lhs, ScannedDevice rhs) {
                if (lhs.getRssi() == 0) {
                    return 1;
                } else if (rhs.getRssi() == 0) {
                    return -1;
                }
                if (lhs.getRssi() > rhs.getRssi()) {
                    return -1;
                } else if (lhs.getRssi() < rhs.getRssi()) {
                    return 1;
                }
                return 0;
            }
        });

        notifyDataSetChanged();
    }
    
    public List<ScannedDevice> getList() {
        return mList;
    }
}
