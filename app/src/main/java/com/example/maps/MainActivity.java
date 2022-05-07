package com.example.maps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {

    MapView mapView;
    UserLocationLayer userLocationLayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("073c5a97-0003-4715-916f-216640bdd70e");
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(55.751474, 37.573856), 11.0f,0.0f,0.0f),
                new Animation(Animation.Type.SMOOTH,0),
                null);
        mapView.getMap().getMapObjects().addPlacemark(new Point(57.999086, 55.951253), ImageProvider.fromResource(MainActivity.this, R.drawable.yandex_logo_ru));
        onLocation();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStop();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    protected void onStart(){
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    public void onLocation(){


        userLocationLayer = mapView.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setAutoZoomEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);



    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth()*0.5), (float)(mapView.getHeight()*0.5)),
                new PointF((float) (mapView.getWidth()*0.5), (float)(mapView.getHeight()*0.5)));

        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.yandex_logo_ru));
        userLocationView.getPin().setIcon(ImageProvider.fromResource(
                this, R.drawable.yandex_logo_ru));
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);

    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {

    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }
}
