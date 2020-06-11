package com.tianyu.weizixun.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.tianyu.weizixun.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initView();
        initLocation();
    }

    @OnClick({R.id.btn_location, R.id.btn_pio, R.id.btn_marker, R.id.btn_guide, R.id.btn_path, R.id.btn_map_normal,
            R.id.btn_map_satellite, R.id.btn_open_heat_map, R.id.btn_close_heat_map})
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
        }
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
        LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
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
}