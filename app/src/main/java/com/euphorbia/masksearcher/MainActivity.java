package com.euphorbia.masksearcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private GoogleMap mMap;
    private Geocoder geocoder;

    private Button guideBtn;
    private Button search_button;
    private EditText adressEdit;

    private Stores[] stores;

    private LatLng point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guideBtn = findViewById(R.id.guideBtn);
        search_button = findViewById(R.id.search_button);
        adressEdit = findViewById(R.id.adressEdit);

        geocoder = new Geocoder(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitExService.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final RetrofitExService retrofitExService = retrofit.create(RetrofitExService.class);

        guideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                startActivity(intent);
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMap != null) {
                    mMap.clear();
                }

                String str = adressEdit.getText().toString();
                List<Address> addressList = null;

                try {
                    addressList = geocoder.getFromLocationName(str, 5);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (addressList == null) {
                    Toast.makeText(getApplicationContext(), "검색결과가 옳지 않습니다. 정확하게 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }

                if (addressList.size() == 0) {
                    Toast.makeText(getApplicationContext(), "검색결과가 옳지 않습니다. 정확하게 입력해주세요!", Toast.LENGTH_LONG).show();
                    return;
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(adressEdit.getWindowToken(), 0);

                String[] splitStr = addressList.get(0).toString().split(",");
                String address = splitStr[0].substring(splitStr[0].indexOf(" ") + 1, splitStr[0].length() - 2); // 주소
                String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도
                point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                retrofitExService.get_Store_retrofit(address).enqueue(new Callback<Data>() {
                    @Override
                    public void onResponse(@NonNull Call<Data> call, @NonNull Response<Data> response) {
                        if (response.isSuccessful()) {
                            Data body = response.body();
                            if (body != null) {

                                stores = body.getStores();

                                if (stores.length == 0) {
                                    Toast.makeText(getApplicationContext(), "검색한 지역에 약국이 없습니다!", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                        .findFragmentById(R.id.map);
                                mapFragment.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap googleMap) {

                                        mMap = googleMap;

                                        for (int i = 0; i < stores.length; i++) {

                                            LatLng SEOUL = new LatLng(Double.parseDouble(stores[i].getLat()), Double.parseDouble(stores[i].getLng()));

                                            MarkerOptions markerOptions = new MarkerOptions();
                                            markerOptions.position(SEOUL);
                                            if (stores[i].getCreated_at() == null) {
                                                stores[i].setCreated_at("정보없음");
                                            }

                                            if (stores[i].getRemain_stat() != null) {

                                                if (stores[i].getRemain_stat().equals("plenty")) {
                                                    stores[i].setRemain_stat("재고 상태 : 100개 이상");
                                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                                } else if (stores[i].getRemain_stat().equals("some")) {
                                                    stores[i].setRemain_stat("재고 상태 : 30개 ~ 100개");
                                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                                                } else if (stores[i].getRemain_stat().equals("few")) {
                                                    stores[i].setRemain_stat("재고 상태 : 2개 ~ 30개");
                                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                                                } else if (stores[i].getRemain_stat().equals("empty")) {
                                                    stores[i].setRemain_stat("재고 상태 : 1개 이하");
                                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                                } else if (stores[i].getRemain_stat().equals("break")) {
                                                    stores[i].setRemain_stat("재고 상태 : 품절");
                                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                                }

                                            } else {
                                                stores[i].setRemain_stat("재고 상태 : 정보없음");
                                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));

                                            }
                                            markerOptions.snippet(stores[i].getName() + "," + stores[i].getAddr() + "," + stores[i].getCreated_at() + "," + stores[i].getRemain_stat());
                                            mMap.addMarker(markerOptions);

                                        }

                                        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                            @Override
                                            public View getInfoWindow(Marker arg0) {
                                                return null;
                                            }

                                            @Override
                                            public View getInfoContents(Marker marker) {

                                                View v = getLayoutInflater().inflate(R.layout.marker, null);

                                                TextView info = v.findViewById(R.id.info);

                                                String snippet = marker.getSnippet();
                                                snippet = snippet.replaceAll(",", "\n");

                                                info.setText(snippet);

                                                return v;
                                            }
                                        });


                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 15));
                                    }
                                });

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "현재 서버가 정상적이지 않습니다. 잠시후에 시도해주세요!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Data> call, @NonNull Throwable t) {

                    }
                });
            }
        });


    }
}
