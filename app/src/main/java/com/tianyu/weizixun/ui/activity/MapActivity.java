package com.tianyu.weizixun.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.tianyu.weizixun.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {

    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.btn_location)
    Button btnLocation;
    @BindView(R.id.btn_pio)
    Button btnPio;
    @BindView(R.id.btn_marker)
    Button btnMarker;
    @BindView(R.id.btn_guide)
    Button btnGuide;
    @BindView(R.id.btn_path)
    Button btnPath;
    private BaiduMap baiduMap;
    private LocationClient mLocationClient;
    private BDLocation mLocation;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initLocation();
        initView();
    }

    @OnClick({R.id.btn_location, R.id.btn_pio, R.id.btn_marker, R.id.btn_guide, R.id.btn_path, R.id.btn_map_normal,
            R.id.btn_map_satellite, R.id.btn_open_heat_map, R.id.btn_close_heat_map, R.id.btn_road, R.id.btn_close_road,
            R.id.btn_marker_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_location:
                //定位到当前位置
                locationToMyPosition();
                break;
            case R.id.btn_pio:
                //pio检索
                break;
            case R.id.btn_marker:
                //覆盖物
                addMarker();
                break;
            case R.id.btn_guide:
                //导航
                break;
            case R.id.btn_path:
                //路径规划
                break;
            case R.id.btn_map_normal:
                //普通地图
                baiduMap.setMapType(baiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.btn_map_satellite:
                //卫星地图
                baiduMap.setMapType(baiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.btn_open_heat_map:
                //开启热力图
                baiduMap.setBaiduHeatMapEnabled(true);
                break;
            case R.id.btn_close_heat_map:
                //关闭热力图
                baiduMap.setBaiduHeatMapEnabled(false);
                break;
            case R.id.btn_road:
                //实时路况
                //开启交通图
                baiduMap.setTrafficEnabled(true);
                break;
            case R.id.btn_close_road:
                //关闭交通图
                baiduMap.setTrafficEnabled(false);
                break;
            case R.id.btn_marker_anim:
                //动画
                mapAnim();
                break;
        }
    }


    private void mapAnim() {
        //构造Icon列表
        // 初始化bitmap 信息，不用时及时 recycle
        BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker);
        BitmapDescriptor bdB = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_one);
        BitmapDescriptor bdC = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker_two);

        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(bdA);
        giflist.add(bdB);
        giflist.add(bdC);
        //Marker位置坐标
        LatLng llD = new LatLng(39.906965, 116.401394);
        //构造MarkerOptions对象
        MarkerOptions ooD = new MarkerOptions()
                .position(llD)
                .icons(giflist)
                .zIndex(0)
                .period(20);//定义刷新的帧间隔

        //在地图上展示包含帧动画的Marker
        Overlay mMarkerD = (Marker) (baiduMap.addOverlay(ooD));
    }

    /**
     * 覆盖物
     */
    private void addMarker() {
        //获取当前地图屏幕中心点的坐标
        LatLng target = baiduMap.getMapStatus().target;
        //定义marker覆盖物坐标  经纬度
        LatLng latLng = new LatLng(target.latitude, target.longitude);
        //构建marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marker);
        //构建 MarkerOption , 用于在地图上显示 marker
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        //地图上添加options并显示
        baiduMap.addOverlay(options);
    }

    private void locationToMyPosition() {
        //如果已经定位了，只需要将地图界面移动到用户所在位置即可
        //改变地图的手势中心点（地图的中心点）
        //mLocation 是定位获取到的用户位置信息对象  MyLocationListener
        //用户的经纬度
        LatLng latLng = null;
        //如果没有得到用户的GPS服务
        if (mLocation == null) {
            //打开GPS
            openGPS();
        } else {
            latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
        }
        //改变地图手势的中心点到用户位置
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
    }

    private void initView() {
        //得到baidumap
        baiduMap = mapview.getMap();
        //开启地图的定位图层
        baiduMap.setMyLocationEnabled(true);
    }

    /**
     * 初始化定位
     * 通过LocationClient发起定位
     * 在ondestroy 关闭 mLocationClient.stop
     */
    private void initLocation() {
        //定位初始化
        mLocationClient = new LocationClient(this);

        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);

        //设置locationClientOption
        mLocationClient.setLocOption(option);

        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
    }


    /**
     * 构造地图数据
     * 我们通过继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，并将其传给MapView。
     */
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapview == null) {
                return;
            }
            Log.e("TAG", "onReceiveLocation: " + location);
            //用户位置信息
            mLocation = location;

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapview.onDestroy();
    }

    /**
     * 打开GPS服务
     */
    private void openGPS() {
        new AlertDialog.Builder(MapActivity.this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(R.string.information)
                .setMessage("没有开启定位")
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.open, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, 887);
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    /**
     * 打开GPS回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 887:
                //开启GPS，重新添加地理监听
                initLocation();
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}