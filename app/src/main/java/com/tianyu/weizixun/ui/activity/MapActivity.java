package com.tianyu.weizixun.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.tianyu.weizixun.R;
import com.tianyu.weizixun.maputils.PoiOverlay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {

    @BindView(R.id.mapview)
    MapView mapview;
    @BindView(R.id.btn_location)
    Button btnLocation;
    @BindView(R.id.btn_poi)
    Button btnPio;
    @BindView(R.id.btn_marker)
    Button btnMarker;
    @BindView(R.id.btn_guide)
    Button btnGuide;
    @BindView(R.id.btn_path)
    Button btnPath;
    @BindView(R.id.btn_map_normal)
    Button btnMapNormal;
    @BindView(R.id.btn_map_satellite)
    Button btnMapSatellite;
    @BindView(R.id.btn_open_heat_map)
    Button btnOpenHeatMap;
    @BindView(R.id.btn_close_heat_map)
    Button btnCloseHeatMap;
    @BindView(R.id.btn_road)
    Button btnRoad;
    @BindView(R.id.btn_close_road)
    Button btnCloseRoad;
    @BindView(R.id.btn_marker_anim)
    Button btnMarkerAnim;
    private BaiduMap baiduMap;
    private LocationClient mLocationClient;
    private BDLocation mLocation;
    private PoiSearch mPoiSearch;
    private LatLng startPt;
    private LatLng endPt;
    private WalkNaviLaunchParam mParam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        initLocation();
        initView();
    }

    @OnClick({R.id.btn_location, R.id.btn_poi, R.id.btn_marker, R.id.btn_guide, R.id.btn_path, R.id.btn_map_normal,
            R.id.btn_map_satellite, R.id.btn_open_heat_map, R.id.btn_close_heat_map, R.id.btn_road, R.id.btn_close_road,
            R.id.btn_marker_anim})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_location:
                //定位到当前位置
                locationToMyPosition();
                break;
            case R.id.btn_poi:
                //POI检索
                poi();
                break;
            case R.id.btn_marker:
                //覆盖物
                addMarker();
                break;
            case R.id.btn_guide:
                //导航
                guide();
                break;
            case R.id.btn_path:
                //路径规划
                // TODO: 2020/6/13
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

    /**********************************************************************************************************
     * 步行导航                                                                                                *
     * 1. 步骑行导航的开发包与普通地图服务的开发包下载时区别。                                                     *
     * 2. 将解压后的开发包中 assets 目录下的 png 文件拷贝到您的项目的 assets 目录下。                              *
     * 注意：                                                                                                  *
     * 一：引擎初始化失败：原因是assets没有copy完全  必须有： BaiduBikeNavi_Resource_v6_3_0.png没有在官网拿        *
     * 二：WNaviGuideActivity得自己创建不能直接拿！！并且必须继承activity                                         *
     **********************************************************************************************************/
    private void guide() {
        // 获取导航控制类
        // 引擎初始化
        WalkNavigateHelper.getInstance().initNaviEngine(this, new IWEngineInitListener() {
            @Override
            public void engineInitSuccess() {
                //初始化引擎成功的回调
                routeWalkPlanWithParam();
            }

            @Override
            public void engineInitFail() {
                //引擎初始化失败回调
                Toast.makeText(MapActivity.this, "引擎初始化失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 引擎初始化成功的回调
     */
    private void routeWalkPlanWithParam() {
        //起终点位置
        startPt = new LatLng(40.047416, 116.312143);
        endPt = new LatLng(40.048424, 116.313513);
        //构造WalkNaviLaunchParam走路导航开启参数
        mParam = new WalkNaviLaunchParam().stPt(startPt).endPt(endPt);
        //发起算路
        WalkNavigateHelper.getInstance().routePlanWithParams(mParam, new IWRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                //开始算路的回调
            }

            @Override
            public void onRoutePlanSuccess() {
                //算路成功
                //跳转至诱导页面
                //WNaviGuideActivity走路导航，官方demo中有
                Intent intent = new Intent(MapActivity.this, WNaviGuideActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(WalkRoutePlanError walkRoutePlanError) {
                //算路失败的回调
            }
        });

    }


    private void poi() {
        //创建POI检索实例
        mPoiSearch = PoiSearch.newInstance();
        //创建POI检索监听器
        OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                //得到POI
                //检索结果覆盖物
                if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                    baiduMap.clear();

                    //创建PoiOverlay对象
                    PoiOverlay poiOverlay = new PoiOverlay(baiduMap);

                    //设置Poi检索数据
                    poiOverlay.setData(poiResult);

                    //将poiOverlay添加至地图并缩放至合适级别
                    poiOverlay.addToMap();
                    poiOverlay.zoomToSpan();
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }

            //废弃
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        };
        //设置检索监听器
        mPoiSearch.setOnGetPoiSearchResultListener(listener);
        /**
         *  PoiCiySearchOption 设置检索属性
         *  city 检索城市
         *  keyword 检索内容关键字
         *  pageNum 分页页码
         */
        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city("北京") //必填
                .keyword("美食") //必填
                .pageNum(10));
        /**
         * POI周边检索
         * 周边检索是在一个圆形范围内的POI检索，适用于以某个位置为中心点，自定义搜索半径，搜索某个位置附近的POI。
         * 设置SearchOption，发起周边检索请求示例如下：
         * 以天安门为中心，搜索半径100米以内的餐厅
         */
        //mPoiSearch.searchNearby(new PoiNearbySearchOption()
        //        .location(new LatLng(39.915446, 116.403869))
        //        .radius(10000)
        //        .keyword("餐厅")
        //        .pageNum(10));

        /*************************************************************************/

        //POI区域检索（矩形区域检索）
        //POI区域检索，即“在由开发者指定的西南角和东北角组成的矩形区域内的POI检索”。
        //设置PoiBoundsSearchOptions，发起检索请求示例如下：
        /**
         * 设置矩形检索区域
         */
        //LatLngBounds searchBounds = new LatLngBounds.Builder()
        //        .include(new LatLng(39.92235, 116.380338))
        //        .include(new LatLng(39.947246, 116.414977))
        //        .build();

        /**
         * 在searchBounds区域内检索餐厅
         */
        //mPoiSearch.searchInBound(new PoiBoundSearchOption()
        //        .bound(searchBounds)
        //        .keyword("餐厅"));
        Toast.makeText(this, "找到北京附近美食相关地区", Toast.LENGTH_SHORT).show();
    }

    OnGetPoiSearchResultListener listener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {
                baiduMap.clear();

                //创建PoiOverlay对象
                PoiOverlay poiOverlay = new PoiOverlay(baiduMap);

                //设置Poi检索数据
                poiOverlay.setData(poiResult);

                //将poiOverlay添加至地图并缩放至合适级别
                poiOverlay.addToMap();
                poiOverlay.zoomToSpan();
            }
        }

        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

        }

        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

        }
    };


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
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mapview.onDestroy();
        mapview = null;
        if (mPoiSearch != null) {
            mPoiSearch.destroy();
            mPoiSearch = null;
        }
        super.onDestroy();
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
     *
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
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}