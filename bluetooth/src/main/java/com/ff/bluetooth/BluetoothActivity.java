package com.ff.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.ff.common.permisson.IPermissionsListener;
import com.ff.common.permisson.ZPermissionUtil;
import com.ff.common.utils.LogUtil;
import com.ff.common.utils.LogUtils;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BluetoothActivity extends AppCompatActivity {


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        permisson();


    }

    public void startScan() {
        if (!isSupportBle()) {
            LogUtils.log("该设备不支持蓝牙ble");
            return;
        }
        LogUtils.log("开始扫描");
//        scan1();
        scan2();
        scan3();
    }

    private void scan2() {
        LogUtils.log("开始扫描2");
//        BluetoothAdapter.getDefaultAdapter().stopLeScan(leScanCallback);
        BluetoothAdapter.getDefaultAdapter().startLeScan(leScanCallback);
    }

    private void scan3() {
        // 注册用以接收到已搜索到的蓝牙设备的receiver
        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBluetoothReceiver, mFilter);
        // 注册搜索完时的receiver
        mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mBluetoothReceiver, mFilter);

        LogUtils.log("开始扫描3");
//        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        BluetoothAdapter.getDefaultAdapter().startDiscovery();
    }

    // 广播接收发现蓝牙设备
    private BroadcastReceiver mBluetoothReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //发现蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                LogUtils.log("扫描结果-经典蓝牙:" + device.getName() + "==" + device.getAddress());
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //蓝牙搜索完成
                // 蓝牙搜索是非常消耗系统资源开销的过程，搜索完毕后应该及时取消搜索
//                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                BluetoothAdapter.getDefaultAdapter().startDiscovery();
            }
        }
    };


    BluetoothAdapter.LeScanCallback leScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            LogUtils.log("扫描结果-BLE:" + device.getName() + "==" + device.getAddress());
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scan1() {
        BluetoothLeScanner bluetoothLeScanner = BluetoothAdapter.getDefaultAdapter().getBluetoothLeScanner();
        LogUtils.log("开始扫描1 ");
        bluetoothLeScanner.startScan(new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                super.onScanResult(callbackType, result);
                LogUtils.log("扫描结果-BLE:" + result.getDevice().getName() + "==" + result.getDevice().getAddress());
            }

            @Override
            public void onBatchScanResults(List<ScanResult> results) {
                super.onBatchScanResults(results);
                LogUtils.log("扫描结果111111");
            }

            @Override
            public void onScanFailed(int errorCode) {
                super.onScanFailed(errorCode);
                LogUtils.log("onScanFailed==" + errorCode);
            }
        });
    }

    public boolean isSupportBle() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
                && getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    private void permisson() {
        LogUtils.log("申请权限-");
        if (!ZPermissionUtil.getInstance().isPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                || !ZPermissionUtil.getInstance().isPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
                || !ZPermissionUtil.getInstance().isPermissions(this, Manifest.permission.BLUETOOTH_ADMIN)) {
            ZPermissionUtil.getInstance().requestPermissions(this, new IPermissionsListener() {
                @Override
                public void onPermissionsSuccess() {
                    LogUtils.log("权限成功");
                    startScan();
                }

                @Override
                public void onPermissionsFail() {
                    finish();
                }
            }, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.BLUETOOTH_ADMIN);
        } else {
            LogUtils.log("权限已具备");
            startScan();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
        if (leScanCallback != null)
            BluetoothAdapter.getDefaultAdapter().stopLeScan(leScanCallback);
    }
}
