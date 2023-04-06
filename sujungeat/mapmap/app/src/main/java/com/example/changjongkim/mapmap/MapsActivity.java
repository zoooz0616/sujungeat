package com.example.changjongkim.mapmap;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

//맵스액티비티
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_CODE_PERMISSIONS = 1000;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    public void onConnected(Bundle bundle){}

    @Override
    public void onConnectionSuspended(int i){}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps); //구글맵을 데려온다.

        //현재위치정보?
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //구글맵이 준비가 된다면 실행

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this); //얻음
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap; //구글맵의 객체가 들어와 mMAp에 저장한다.

        // 각 음식점 위에 마커를 표시하고 카메라를 움직인다.
        //1.제순식당
        LatLng jaesun = new LatLng(37.5917677, 127.01831909999999); //LatLng : 위경도를 표현하는 객체, v 위도 v1 경도
        mMap.addMarker(new MarkerOptions().position(jaesun).title("제순식당").snippet("한식"+"\n운영시간: 매일 10:00 - 22:00"+"\n번호: 02-927-2007"+ "\n오제볶음(오징어+제육) (2인분이상) 7,000원"+ "\n쭈삼볶음(쭈꾸미+제육) (2인분이상) 7,000원"+ "\n직화간장돼지불고기(2인이상) 6,000원"+ "\n간장오불(간장오징어+간장돼지불고기) (2인분이상) 7,000원"+ "\n직화제육볶음 6,000원")); //마커위치
        mMap.moveCamera(CameraUpdateFactory.newLatLng(jaesun)); //마커위치로 카메라를 옮김
        //마커위치로 확대한다.
        mMap.animateCamera(CameraUpdateFactory. newLatLngZoom(jaesun, 16.3f)); // 1~20 사이의 수를 입력하여 f(플록박스)를 주어 zoom이 된다.

        //infowindow 세팅해보겠음
        InfoWindow
                InfoWindow = new InfoWindow(getApplicationContext());
        mMap.setInfoWindowAdapter(InfoWindow);

        //클릭하면 마커를 보여줌
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng arg0) {
                mMap.clear();
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(arg0);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
                Marker marker = mMap.addMarker(markerOptions);
                marker.showInfoWindow();
            }
        });

        //2. 키다리 아저씨
        LatLng keydari = new LatLng(37.591529, 127.02006370000004);
        mMap.addMarker(new MarkerOptions().position(keydari).title("키다리아저씨")
                .snippet("한식"+"\n운영시간: 매일 11:30 - 21:30(일요일 휴무)"+ "\n번호: 02-922-4530"+ "\n생연어 회덮밥 8,000원"+ "\n치즈 돌솥밥 5,000원"+ "\n치킨마요 볶음밥 6,000원"));//마커위치

        //3. 놀부 부대찌개
        LatLng nolbu = new LatLng(37.5923125, 127.0172996);
        mMap.addMarker(new MarkerOptions().position(nolbu).title("놀부 부대찌개")
                .snippet("한식"+"\n운영시간: 매일 09:00 - 22:00"+"" + "\n번호: 02-928-3337"+ "\n우삼겹부대8,900원"+ "\n치즈부대 7,900원"+ "\n놀부부대 7,900원"+ "\n김치부대7,900원"+ "\n옛날부대 7,500원"));; //마커위치

        //4. 미향
        LatLng mi = new LatLng(37.5942746, 127.01714809999999);
        mMap.addMarker(new MarkerOptions().position(mi).title("미향")
                .snippet("한식"+"\n운영시간: 매일 10:00 - 22:00"+"\n번호:없음"+ "\n미향 진곰탕 8,000원"+ "\n사골 만두국 7,000원"+ "\n사골 칼국수+만두 7,000원"+ "\n사골 떡만두 7,000원")); //마커위치

        //5. 선창연가
        LatLng sunchang = new LatLng(37.5937662, 127.01756019999993);
        mMap.addMarker(new MarkerOptions().position(sunchang).title("선창연가")
                .snippet("한식"+"\n운영시간: 매일 10:00 - 23:00"+"\n번호: 02-929-4994"+ "\n대표여수게장정식 8,000원"+ "\n대표고등어구이정식 8,000원"+ "\n더덕구이정식 10,000원"+ "\n떡갈비정식 10,000원"+ "\n낙지볶음정식  10,000원"));

        //6. 신의주 찹쌀순대
        LatLng sineaju = new LatLng(37.5930311, 127.01800809999997);
        mMap.addMarker(new MarkerOptions().position(sineaju).title("신의주 찹쌀순대")
                .snippet("한식"+"\n운영시간: 매일 00:00 - 24:00"+"\n번호: 02-908-9409"+ "\n신의주순대국 7,000원"+ "\n얼큰순대국 7,000원"+ "\n김치순대국  7,000원"));

        //7. 태조감자국
        LatLng taejo = new LatLng(37.5921011, 127.01593789999993);
        mMap.addMarker(new MarkerOptions().position(taejo).title("태조감자국")
                .snippet("한식"+"\n운영시간: 매일 10:00 - 05:00"+"\n번호: 02-926-7008"+ "\n감자탕 11,000원"+ "\n사리면 1,000원"+ "\n수제비  1,500원"));

        //8. 동경산책
        LatLng dongkyeong = new LatLng(37.5908162, 127.01788770000007);
        mMap.addMarker(new MarkerOptions().position(dongkyeong).title("동경산책")
                .snippet("일식"+"\n운영시간:평일 점심 11:30 - 15:00 \n평일 저녁  17:00 – 22:00 (last order 21:00)\n주말 11:30 – 22:00"+ "\n번호:02-923-2666"+"\n[대표]사케롤 정식, 카니롤 정식, 스키야키 나베 정식"));

        //9. 가야가야
        LatLng gayagaya = new LatLng(37.5905776, 127.01909449999994);
        mMap.addMarker(new MarkerOptions().position(gayagaya).title("가야가야")
                .snippet("일식"+"\n운영시간:11:00~21:00+"+"\n번호:02-929-7877"+"\n돈코츠라멘:7,000원"+"\n돈코츠차슈멘:9,000원"+"\n돈코츠 미소 야사이라멘:9,500원"+"\n돈코츠 야사이 라멘:9,000원"+"\n돈코츠 미소라멘:7,500원"));

        //10. 상미규카츠
        LatLng sangmi = new LatLng(37.5908035, 127.01700690000007);
        mMap.addMarker(new MarkerOptions().position(sangmi).title("상미규카츠")
                .snippet("일식"+"\n운영시간:12:00~01:00"+"\n번호:02-928-8892"+ "\n[대표] 규카츠정식, 찹스테이크정식"));

        //11. 보배반점
        LatLng bobe = new LatLng(37.5907251, 127.01743940000006);
        mMap.addMarker(new MarkerOptions().position(bobe).title("보배반점")
                .snippet("중식"+"\n운영시간: 매일 11:00 - 22:00"+"\n번호: 02-923-3439"+ "\n보배짬뽕 6,000원"+ "\n보배 짜장면 4,500원"+ "\n볶음밥 6,000원"+ "\n해물짬뽕탕 13,000원"+ "\n깐풍기 13,000원"));

        //12. 놉파스타
        LatLng nob = new LatLng(37.5910747, 127.01960880000001);
        mMap.addMarker(new MarkerOptions().position(nob).title("놉파스타").title("놈파스타").snippet("양식"+"\n운영시간: 매일 11:30 - 22:00"+"\n번호 : 02-929-1354"+ "\n고르곤졸라 유자피자 13,500원"+ "\n 명란로지파스타 13,500원"));

        //13. 공푸
        LatLng gongpu = new LatLng(37.5887752, 127.01613799999996);
        mMap.addMarker(new MarkerOptions().position(gongpu).title("공푸")
                .snippet("중식"+"\n운영시간: 매일 11:30 - 21:00"+"\n번호: 070-7566-4683"+ "\n짜장 5,000원"+ "\n쟁반짜장 8,000원"+ "\n해물짬뽕 8,000원"+ "\n탕수육(소) 12,000원"+ "\n탕수육(중) 18,000원"+ "\n양장피 20,000원"+ "\n깐풍기 13,000원"));

        //14. 중화명가
        LatLng junghwa = new LatLng(37.5885526, 127.01677610000002);
        mMap.addMarker(new MarkerOptions().position(junghwa).title("중화명가 ")
                .snippet("중식"+"\n번호:02-924-8260")); //마커위치

        //15. 북경양꼬치
        LatLng bukkyung = new LatLng(37.5923085, 127.0182327);
        mMap.addMarker(new MarkerOptions().position(bukkyung).title("북경양꼬치")
                .snippet("중식"+"\n운영시간: 매일 14:00 - 04:00"+"\n번호: 02-926-3800"+ "\n생양고기꼬치 12,000원"+ "\n양갈비살 13,000원"+ "\n철판소고기  16,000원"));

        //16. 마늘과올리브
        LatLng olive = new LatLng(37.5887752, 127.01613799999996);
        mMap.addMarker(new MarkerOptions().position(olive).title("마늘과올리브").title("마늘과 올리브").snippet("양식"+"\n운영시간: 매일 매일 11:00 - 22:00"+"\n번호 : 02-929-6604"+ "\n마레 알리오 올리오 11,000원"));

        //17. 애정마라훠궈
        LatLng mara = new LatLng(37.5921344, 127.01722610000002);
        mMap.addMarker(new MarkerOptions().position(mara).title("애정 마라훠궈")
                .snippet("중식"+"\n운영시간: 매일 12:00 - 23:00"+"\n번호: 010-7375-5686"+ "\n꿔바로우 16,000원"+ "\n마라롱샤/마라가재  30,000원"));

        //18. 팬쿡
        LatLng pencook = new LatLng(37.591942, 127.0189011);
        mMap.addMarker(new MarkerOptions().position(pencook).title("팬쿡").snippet("양식"+"\n운영시간: 매일 11:30 - 22:00"+"\n번호: 02-6339-3323"+ "\n그릴 삼겹 파스타 13,900원"+ "\n 매운 게살 새우 파스타 14,900원"+ "\n 뽀모도로 8,900원"+ "\n 스테이크 파스타 12,900원"+ "\n 지글지글 부채살 스테이크 16,900원 "+ "\n 핫갈릭 새우 파스타 10,900원"));

        //19. 소테
        LatLng sote = new LatLng(37.590926, 127.01971379999998);
        mMap.addMarker(new MarkerOptions().position(sote).title("소테").title("소테").snippet("양식"+"\n운영시간: 매일 11:30 - 22:00"+"\n번호: 02-922-3738"+ "\n치킨소테 6,800원"+ "\n 탄두리 치킨 16,000원"));

        //20. 마미인더키친
        LatLng mommyinthe = new LatLng(37.59153420000001, 127.02057549999995);
        mMap.addMarker(new MarkerOptions().position(mommyinthe).title("마미인더키친(마미리틀 블랙팟)").snippet("양식"+"\n운영시간: 매일 12:00 - 22:00 월요일 휴무"+"\n번호: 02-929-1102"+ "\n캐비지 롤 "+ "\n 햄버거 스테이크"+ "\n 치즈 햄버거 스테이크"+ "\n 베이컨 햄버거 스테이크"+ "\n 로메인 샐러드"));

        //21. 히메
        LatLng himea = new LatLng(37.5913637, 127.01947139999993);
        mMap.addMarker(new MarkerOptions().position(himea).title("히메")
                .snippet("일식"+"\n운영시간:매일 11:00~22:00 라스트오더 21:30"+"\n번호:02-924-1222"+ "\n연어사시미 大 : 32,000원"+ "\n쭈연어 불초밥 : 14,000원"+ "\n연어 카르파초 : 17,000원"+ "\n연어 타다끼 + 뱃살 [대표] : 24,000원"+ "\n연어 타다끼 : 20,000원"));

        //22. 김태완초밥좋은날
        LatLng kimteawhan = new LatLng(37.59110039999999, 127.01662480000005);
        mMap.addMarker(new MarkerOptions().position(kimteawhan).title("김태완초밥좋은날")
                .snippet("일식"+"\n운영시간:매일 11:30~24:00"+"\n번호: 02-924-0933"+ "\n굿데이모둠초밥 :　10,000원"+ "\n스페셜모둠초밥 : 14,000원"+ "\n회 19,000~40,000원"+ "\n특선 13,000원"+ "\n참치초밥,광어초밥 각 17,000원"));

        //23. 스시진
        LatLng ssijin = new LatLng(37.5909184, 127.01663289999999);
        mMap.addMarker(new MarkerOptions().position(ssijin).title("스시진")
                .snippet("일식"+"\n운영시간:매일 11:00~22:00 브레이크타임 15:00~16:30"+"\n번호:02-924-3730"+ "\n사시미 정식 : 23,000원"+ "\n오늘의 초밥 : 10,000원"+ "\n스페셜 초밥 : 12,000원“"+ "\n초밥 세트: 10,000원"+ "\n특선초밥 : 13,000원"));

        //24. 스시미
        LatLng ssimi = new LatLng(37.59114160000001, 127.02006770000003);
        mMap.addMarker(new MarkerOptions().position(ssimi).title("스시미")
                .snippet("일식"+"\n운영시간:매일 11:00~22:30"+"\n번호: 02-923-2220"+ "\n친구세트[대표] : 30,000원"+ "\n쭈연어 불초밥 : 14,000원"+ "\n연어 카르파초 : 17,000원“"+ "\n연어 타다끼 + 뱃살 [대표] : 24,000원"+ "\n연어 타다끼 : 20,000원"));

        //25. 키작은아저씨초밥
        LatLng SmallKi = new LatLng(37.591942, 127.0189011);
        mMap.addMarker(new MarkerOptions().position(SmallKi).title("키작은아저씨초밥")
                .snippet("일식"+"\n운영시간:매일 11:00~23:30 브레이크타임 15:00~16:30"+"\n번호:02-925-8540"+ "\n광어초밥(10pcs)[대표] : 18,000원"+"\n광어 + 연어 초밥 [대표] : 24,000원"+ "\n모듬초밥(12pcs) : 17,000원"+ "\n특선초밥(15pcs) : 22,000원"+ "\n연어 타다끼 : 20,000원"));

        //26. 지구당
        LatLng EarthDang = new LatLng(37.58910600000001, 127.01700770000002);
        mMap.addMarker(new MarkerOptions().position(EarthDang).title("지구당")
                .snippet("일식"+"\n운영시간:매일 11:00~21:00 브레이크타임:14:30~17:00"+"\n번호: 02-928-6607"+ "\n규동 : 6,500원"+ "\n오야꼬동 7,000원"));

        //27. 겐텐
        LatLng gunten = new LatLng(37.5921317, 127.01788169999998);
        mMap.addMarker(new MarkerOptions().position(gunten).title("겐텐")
                .snippet("일식"+"\n운영시간:매일 11:00~21:30"+"\n번호 : 02-923-3271"+ "\n돈코츠라멘 : 6,000원"+ "\n카라미소라멘 : 6,500원"+ "\n나가사끼짬뽕 : 10,000원“"+ "\n차슈동 : 7,000원"+ "\n치킨동 : 7,000원"));

        //28. 호쿠호쿠
        LatLng hocoo = new LatLng(37.5914817, 127.01686210000003);
        mMap.addMarker(new MarkerOptions().position(hocoo).title("호쿠호쿠")
                .snippet("일식"+"\n운영시간"+"\n평일토요일 11:30 - 22:00 브레이크타임15:00~17:00"+"\n일요일, 공휴일 11:30 - 22:00Break time 16:00~17:00"+"\n번호: 02-923-0455"+ "\n오늘의 벤또 정식 : 5,900원"+ "\n간장새우밥 : 7,900원"+ "\n소스카츠 : 6,900원“"+ "\n큐브스테이크 : 9,900원"+ "\n차슈 : 6,900원"));

        //29. 나카노라멘
        LatLng Nacano = new LatLng(37.5923793, 127.01893949999999);
        mMap.addMarker(new MarkerOptions().position(Nacano).title("나카노라멘")
                .snippet("일식"+"\n운영시간“+”\n평일토요일 11:30 - 22:00 브레이크타임15:00~17:00"+"\n일요일, 공휴일 11:30 - 22:00Break time 16:00~17:00"+"\n번호: 02-923-0455"+ "\n오늘의 벤또 정식 : 5,900원"+ "\n간장새우밥 : 7,900원"+ "\n소스카츠 : 6,900원“"+ "\n큐브스테이크 : 9,900원"+ "\n차슈 : 6,900원"));

        //30. 윤휘식당
        LatLng Yoonhea = new LatLng(37.5910082, 127.01919299999997);
        mMap.addMarker(new MarkerOptions().position(Yoonhea).title("윤휘식당")
                .snippet("일식"+"\n운영시간:매일 11:30~21:30+"+"\n점심주문마감 15:30/저녁주문마감 20:45"+"\n번호: 02-922-0851"+"\n치킨스테이크 : 9,000원"+ "\n함박스테이크 : 10,000원"+ "\n치킨멘치까스 : 10,000원“"+ "\n차슈동 : 7,000원"+ "\n치즈함박스테이크 : 11,000원"));

        //31. 22번가키친
        LatLng twentytwo = new LatLng(37.5907376, 127.01915919999999);
        mMap.addMarker(new MarkerOptions().position(twentytwo).title("22번가키친").snippet("양식"+"\n운영시간: 매일 11:30 - 22:00 화요일 휴무명절 휴무"+"\n번호: 02-953-4689"+ "\n파스타 11,000원"+ "\n 이베리코 스테이크 20,000원"+ "\n 샐러드 9,000원"+ "\n 라이스 앤 리조또 11,000원"));

        //32. 문화식당
        LatLng culture = new LatLng(37.5910372, 127.02029440000001);
        mMap.addMarker(new MarkerOptions().position(culture).title("문화식당").snippet("양식"+"\n운영시간: 매일 11:30 - 24:00"+"\n번호: 02-6381-4644"+ "\n베이컨 크림 오무라이스 10,000원"+ "\n 까르보나라 수제비 15,000원"+ "\n 눈사람치즈 7,500원"+ "\n 문화식당 삼합 18,000원"+ "\n 베이컨 치즈 7,500원"+ "\n 샐러드 파스타 13,000원"));

        //33. 정키친
        LatLng jung = new LatLng(37.5917314, 127.01861280000003);
        mMap.addMarker(new MarkerOptions().position(jung).title("정키친")
                .snippet("양식"+"\n운영시간:매일 12:00~22:00"+"\n일요일 휴무 / 브레이크타임 : 15:30~17:00"+"\n번호:010-7209-2046"+"\n매콤제육 6,500원"+"\n간장닭 6,500원"+"\n매콤닭 7,000원"+"\n간장닭 7,000원"));

        //34.808superstore
        LatLng superstore = new LatLng(37.5923986, 127.01844779999999);
        mMap.addMarker(new MarkerOptions().position(superstore).title("808superstore")
                .snippet("양식"+"\n운영시간:평일 17:00~01:00"+"\n주말 17:00~03:00"+"\n번호: 070-8860-9171"+ "\n파스타 : 10,800원"+ "\n스튜 : 17,800원"+"\n칵테일 : 7,800원"+ "\n피자 : 18,800원"+ "\n스테이크 : 16,800원"+ "\n 나폴리탄 7,500원"));

        //35. 이름없는파스타
        LatLng noname = new LatLng(37.5921812, 127.01841779999995);
        mMap.addMarker(new MarkerOptions().position(noname).title("이름없는파스타")
                .snippet("양식"+"\n운영시간: 매일 11:00 - 20:30"+"\n번호: 070-8860-9171"+ "\n 멘타이코 알리오올리오 6,900원"+ "\n 멘타이코 까르보나라 7,900원"+ "\n 아라비아따 6,500원"+ "\n 까르보나라 (순한맛, 매운맛 택1) 6,900원"+ "\n 알리오올리오 (해산물, 베이컨 택1) 5,900원 "+ "\n 나폴리탄 7,500원"));

    }

    //버튼을 클릭했을때
    public void onLastLocationButtonClickd(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_PERMISSIONS);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    //온캔들리스너 호출
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mMap.addMarker(new MarkerOptions()
                                    .position(myLocation)
                                    .title("현재위치"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSIONS:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
        }
    }


}
