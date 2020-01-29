package com.rohan.potholesfinder.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceImageLabelerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.rohan.potholesfinder.R;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.ResultReceiver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rohan.potholesfinder.LocationService.AdressIntentService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rohan.potholesfinder.adapter.NewsAdapter;
import com.rohan.potholesfinder.data_model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {


    public static final int LOCATION_INTERVAL = 10000;
    public static final int FASTEST_LOCATION_INTERVAL = 5000;
    public static final int IMAGE_CAPTURE_CODE = 2;
    private static final int FILE_SELECT_CODE = 0;
    private static final String TAG = "MainActivity";
    List<News> newsDataList =new ArrayList<>();
    String jsonData = "{\"status\":\"ok\",\"totalResults\":10,\"articles\":[{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":null,\"title\":\"Caught Prompting \\\"Goli Maaro\\\" Chant, Union Minister Anurag Thakur Says \\\"Gauge People's Mood\\\"\",\"description\":\"The BJP's Anurag Thakur, Union Minister of State for Finance, urges a crowd at an election rally in Delhi to say \\\"goli maaro\\\" - or shoot down traitors - in a widely-shared video that is being examined by the Election Commission.\",\"url\":\"https://www.ndtv.com/india-news/anurag-thakur-rally-after-goli-maaro-slogans-at-union-ministers-election-rally-delhi-poll-officer-se-2170633\",\"urlToImage\":\"https://c.ndtvimg.com/2020-01/75jfh68_anuragthakur_625x300_27_January_20.jpg\",\"publishedAt\":\"2020-01-28T04:06:00+00:00\",\"content\":\"Anurag Thakur was heard chanting the slogan while campaigning for BJP candidate in North West Delhi.\\r\\nNew Delhi: The BJP's Anurag Thakur, Union Minister of State for Finance, urges a crowd at an election rally in Delhi to say \\\"goli maaro\\\" - or shoot down trai… [+2638 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"News18.com\",\"title\":\"'Will Clear Shaheen Bagh in An Hour': After Anurag Thakur, West Delhi BJP MP Seeks Votes With Fiery Speech\",\"description\":\"BJP MP Parvesh Verma was addressing a crowd at a community center in Ranhoula village which falls under the Vikaspuri Assembly constituency in West Delhi.\",\"url\":\"https://www.news18.com/news/india/will-clear-shaheen-bagh-in-an-hour-after-anurag-thakur-west-delhi-bjp-mp-seeks-votes-with-fiery-speech-2475887.html\",\"urlToImage\":\"https://images.news18.com/ibnlive/uploads/2020/01/Capture5.jpg\",\"publishedAt\":\"2020-01-28T03:50:00+00:00\",\"content\":\"New Delhi: After Minister of State for Finance Anurag Thakur, West Delhi BJP MP Parvesh Verma on Monday openly threatened Shaheen Bagh protesters saying that it will take the BJP only an hour to clear off the protests in the area. \\r\\n\\\"If the BJP comes to power… [+1261 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"Reuters\",\"title\":\"Coronavirus: Death toll crosses 100 in China, global stocks tumble, US issues travel advisory\",\"description\":\"Global stocks fell, oil prices hit three-month lows, and China's yuan dipped to its weakest level in 2020.The health commission of China's Hubei province said on Tuesday that 100 people had died from the virus\",\"url\":\"https://www.livemint.com/news/world/coronavirus-update-death-toll-rises-to-106-in-china-us-issues-travel-advisory-11580180624402.html\",\"urlToImage\":\"https://images.livemint.com/img/2020/01/28/600x338/2020-01-27T090426Z_1866851590_RC29OE90ZTZI_RTRMADP_3_CHINA-HEALTH_1580180990261_1580181109086.JPG\",\"publishedAt\":\"2020-01-28T03:16:14+00:00\",\"content\":\"China said on Tuesday that 106 people had died from a new coronavirus that is spreading across the country, up from the previous toll of 81.The number of total confirmed cases in China rose to 4,515 as of Jan. 27, the National Health Commission said in a stat… [+5944 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"Press Trust of India\",\"title\":\"Indian-origin student found dead in college campus lake in US\",\"description\":\"Jerry, who has relatives in Kerala, topped her class at Blaine High School. She was majoring in science and business.\",\"url\":\"https://www.hindustantimes.com/indians-abroad/indian-origin-student-found-dead-in-college-campus-lake-in-us/story-sYLlx5bjZXGYsAMHzYSZGO.html\",\"urlToImage\":\"https://www.hindustantimes.com/rf/image_size_960x540/HT/p2/2020/01/28/Pictures/_665fbe08-4169-11ea-b306-d18e31211930.png\",\"publishedAt\":\"2020-01-28T00:59:58+00:00\",\"content\":\"A 21-year-old Indian-origin female student’s body has been recovered from a lake on the campus of a premier university in the US state of Indiana.\\r\\n Annrose Jerry, an accomplished musician, was a student at the University of Notre Dame. She was missing since … [+1428 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"Times Of India\",\"title\":\"Nirbhaya case: SC to hear Mukesh’s plea against rejection of mercy plea today\",\"description\":\"India News: When the petition by Nirbhaya case convict Mukesh, was mentioned on Monday morning for urgent hearing by his counsel Vrinda Grover, a bench headed by\",\"url\":\"https://timesofindia.indiatimes.com/india/nirbhaya-case-sc-to-hear-mukeshs-plea-against-rejection-of-mercy-plea-today/articleshow/73678367.cms\",\"urlToImage\":\"https://static.toiimg.com/thumb/msid-73678357,width-1070,height-580,imgsize-573272,resizemode-75,overlay-toi_sw,pt-32,y_pad-40/photo.jpg\",\"publishedAt\":\"2020-01-27T20:27:00+00:00\",\"content\":\"India News: When the petition by Nirbhaya case convict Mukesh, was mentioned on Monday morning for urgent hearing by his counsel Vrinda Grover, a bench headed by\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"PV Ramana Kumar\",\"title\":\"Delhi Parties Doing Petty Politics in Streets of Telangana: After Emphatic Civic Poll Win, KTR Slams Cong-BJP\",\"description\":\"The TRS working president said the Delhi-based parties are indulging in petty politics in Telangana by supporting each other in elections.\",\"url\":\"https://www.news18.com/news/politics/after-emphatic-win-in-telangana-municipal-polls-ktr-hits-out-at-cong-bjp-deal-2475715.html\",\"urlToImage\":\"https://images.news18.com/ibnlive/uploads/2019/06/Untitled-design-571.png\",\"publishedAt\":\"2020-01-27T17:44:00+00:00\",\"content\":\"Hyderabad: Telangana Rastra Samiti (TRS) working president KT Rama Rao said the Congress and Bharatiya Janata Party (BJP) are indulging in petty politics to grab power in the state through the municipal bodies.\\r\\nThe minister for information technology and urb… [+2666 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":null,\"title\":\"Ariana Airlines plane with 83 on board crashes in Afghanistan\",\"description\":\"The state-owned airplane crashed 15 km from the centre of Ghazni's Deh Yak district\",\"url\":\"https://www.moneycontrol.com/news/trends/ariana-airlines-plane-with-83-on-board-crashes-in-afghanistan-4862481.html\",\"urlToImage\":\"https://static-news.moneycontrol.com/static-mcnews/2019/11/8-770x435.jpg\",\"publishedAt\":\"2020-01-27T15:30:00+00:00\",\"content\":\"An Afghan passenger plane has crashed in Ghazni province on January 27. The Boeing plane belonging to Ariana Airlines and was reportedly carrying 83 passengers on board.\\r\\nArif Noori, the spokesperson for the provincial governor, told The National, A Boeing pl… [+1499 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"Press Trust of India\",\"title\":\"2G scam case: CBI appeal infructuous in view of new anti-graft law, A Raja tells HC\",\"description\":\"CBI contended that the applications were not sustainable as this specific issue had been raised in the replies filed in response to the agency’s appeal.\",\"url\":\"https://www.hindustantimes.com/india-news/2g-scam-case-cbi-appeal-infructuous-in-view-of-new-anti-graft-law-a-raja-tells-hc/story-Ss7mWb6C3KL81z6UgK5hxK.html\",\"urlToImage\":\"https://www.hindustantimes.com/rf/image_size_960x540/HT/p2/2020/01/27/Pictures/_05a32560-40f0-11ea-bfa0-35d85fc987f6.jpg\",\"publishedAt\":\"2020-01-27T10:32:19+00:00\",\"content\":\"Former Union telecom minister A Raja on Monday told the Delhi High Court that the CBI’s appeal against his and others’ acquittal in the 2G spectrum scam case has become infructuous with the coming of the new anti-corruption law.\\r\\nSenior advocate Abhishek M Si… [+3431 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"PTI\",\"title\":\"36-year-old man under observation in Mumbai for suspected coronavirus\",\"description\":\"It is the fourth such incident in Mumbai.All the them were hospitalised as a precautionary measure\",\"url\":\"https://www.livemint.com/news/india/36-year-old-man-under-observation-in-mumbai-for-suspected-coronavirus-11580112604888.html\",\"urlToImage\":\"https://images.livemint.com/img/2020/01/27/600x338/339961778_0-14_1563198063780_1580112724461.jpg\",\"publishedAt\":\"2020-01-27T08:15:07+00:00\",\"content\":\"In the fourth such incident in Mumbai, a 36-year-old man has been admitted in the isolation ward of a civic-run hospital on suspicion of possible exposure to the novel coronavirus, officials said. The man, a resident of Tardeo in south Mumbai, is currently ke… [+1055 chars]\"},{\"source\":{\"id\":\"google-news-in\",\"name\":\"Google News (India)\"},\"author\":\"Special Correspondent\",\"title\":\"Six arrested for attempt to hurl bombs at ‘Thughlak’ editor’s home in Chennai\",\"description\":\"The suspects belong to a Periyarist outfit, and the attempted attack is seen as a response to actor Rajinikanth’s recent controversial speech about Periyar at a Tughlak magazine function\",\"url\":\"https://www.thehindu.com/news/cities/chennai/two-arrested-for-attempt-to-hurl-bombs-at-thughlak-editors-home-in-chennai/article30663278.ece\",\"urlToImage\":\"https://www.thehindu.com/news/cities/chennai/k5ojvt/article30663366.ece/ALTERNATES/LANDSCAPE_615/27JANTH-Tughlakjpg\",\"publishedAt\":\"2020-01-27T05:39:11+00:00\",\"content\":\"Six suspects have been apprehended by the Mylapore police for allegedly attempting to hurl bombs at the residence of Thughlak magazine editor, S. Gurumurthy, in Mylapore in the early hours of Sunday.\\r\\nAt 3.15 a.m., a group of unidentified persons arrived in m… [+906 chars]\"}]}";

    GoogleApiClient mLocationClient;
    LocationRequest mLocationRequest = new LocationRequest();


    public static final String EXTRA_LATITUDE = "extra_latitude";
    public static final String EXTRA_LONGITUDE = "extra_longitude";
    //
    protected Location currentLocation;
    private AddressResultReceiver resultReceiver;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 1;

    private TextView textView;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    FloatingActionButton fab;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);
        recyclerView = findViewById(R.id.newsRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (!hasPermissions()) {
            requestPermissions();
        }
        getNewsData(jsonData);
        newsAdapter=new NewsAdapter(newsDataList,this);

        //---------------------------
        mLocationClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest.setInterval(LOCATION_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_LOCATION_INTERVAL);


        int priority = LocationRequest.PRIORITY_HIGH_ACCURACY; //by default
        //PRIORITY_BALANCED_POWER_ACCURACY, PRIORITY_LOW_POWER, PRIORITY_NO_POWER are the other priority modes


        mLocationRequest.setPriority(priority);
        mLocationClient.connect();

        //-----------------------------


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        resultReceiver = new AddressResultReceiver(new Handler());

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, IMAGE_CAPTURE_CODE);
            }
        });
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select an Image to racognize"),
                            FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(MainActivity.this, "Please install a File Manager.",
                            Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchAddress();
            }
        });
        recyclerView.setAdapter(newsAdapter);
    }


    private boolean hasPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, FINE_LOCATION_PERMISSION_REQUEST_CODE);
    }

    public void fetchAddress() {

        if (currentLocation != null) {
            startIntentService();
        }


    }

    protected void startIntentService() {
        Intent intent = new Intent(this, AdressIntentService.class);
        intent.putExtra("location", currentLocation);
        intent.putExtra("receiver", resultReceiver);
        startService(intent);
    }

    private void setAddress(String address) {
        textView.setText(address);
    }


    //-----GoogleMapsApi Methods-----//
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "== Error On onConnected() Permission not granted");
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this);

        Log.d(TAG, "Connected to Google API");
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "Connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "Failed to connect to Google API");
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case IMAGE_CAPTURE_CODE:

                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = (Bitmap) bundle.get("data");

                        try {
                            //Convert Bitmap to Uri => Process => Verify Label => Upload
                            processImgUri(getImageUri(bitmap));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "null bundle", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case FILE_SELECT_CODE:
                    Uri uri = data.getData();
                    if (uri == null) {
                        Toast.makeText(this, "null uri", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        //Process => Verify Label => Upload
                        processImgUri(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    private boolean imageLabelsVerified(List<FirebaseVisionImageLabel> labels) {

        //this is a temporary solution to verify our pothole image
        //we will update the logic if we found a better one


        ArrayList<String> requiredLabels = new ArrayList<>();
        //these keywords are found by testing image of a road
        //in different scenarios

        requiredLabels.add("soil");
        requiredLabels.add("asphalt");
        requiredLabels.add("road");
        requiredLabels.add("rock");
        requiredLabels.add("stone");
        requiredLabels.add("concrete");
        requiredLabels.add("sand");
        //can add more related fields

        for (FirebaseVisionImageLabel label : labels) {

            if (requiredLabels.contains(label.getText().toLowerCase())) {
                return true;
            }
        }

        return false;
    }


    private void processImgUri(final Uri uri) throws IOException {
        FirebaseVisionImage image = FirebaseVisionImage.fromFilePath(this, uri);

        FirebaseVisionOnDeviceImageLabelerOptions options = new FirebaseVisionOnDeviceImageLabelerOptions.Builder()
                .setConfidenceThreshold(0.8f)
                .build();

        FirebaseVisionImageLabeler detector = FirebaseVision.getInstance().getOnDeviceImageLabeler(options);


        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
            @Override
            public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {

                if (imageLabelsVerified(firebaseVisionImageLabels)) {
                    Toast.makeText(MainActivity.this, "Verified ok", Toast.LENGTH_SHORT).show();
                    //Ready To Upload
                    uploadImageUri(uri);


                } else {
                    Toast.makeText(MainActivity.this, "The pic doesn't seems to be of a pothole", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("rohan :", "--------FAILED--------------------");
            }
        });

    }

    private void uploadImageUri(final Uri uri) {

        final String currUid = mAuth.getCurrentUser().getUid().toString();
        StorageReference storageRef =
                FirebaseStorage.getInstance().getReference().child("UserReportedPotholes").child(mAuth.getCurrentUser().getEmail() + "_" + System.currentTimeMillis());
        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String photoStringLink = uri.toString();
                        String email = mAuth.getCurrentUser().getEmail();

                        HashMap<String, String> map
                                = new HashMap<>();
                        map.put("emai", email);
                        map.put("uid", currUid);
                        map.put("image", photoStringLink);
                        mFirestore.collection("potholeImages").document().set(map);
                        Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed To Upload", Toast.LENGTH_SHORT).show();
            }
        });


    }


    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    class AddressResultReceiver extends ResultReceiver {

        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if (resultData == null) {
                setAddress("result data is null");
                return;
            }

            String address = resultData.getString("data_key");
            if (address == null) {
                address = "null";
            }
            setAddress(address);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_signout:
                userSignOut();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void userSignOut() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }

    private void getNewsData(String jsonData) {
//        String URL="https://newsapi.org/v2/top-headlines?sources=google-news-in&apiKey=f53e199c8ee2447d80bce832b34ffc30";
// //       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,URL,new Response.Listener<JSONObject>(){
//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                    URL, null, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.d(TAG, response.toString());
//
//                    Toast.makeText(MainActivity.this, "Response Success!", Toast.LENGTH_SHORT).show();
//
//
//                }
//            }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            });


        try {
                Log.d("MainActivity",jsonData);
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONArray articlesArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < articlesArray.length()-1; i++) {
                News currNewsObj = new News();
                JSONObject currNews = articlesArray.getJSONObject(i);
                String title = currNews.getString("title");
                String descrip = currNews.getString("publishedAt");
                String imgUrl = currNews.getString("urlToImage");
                if(title==null){
                    title="No Title";
                }
                if(descrip==null){
                    descrip="No Descrip";
                }
                if(imgUrl==null){
                    imgUrl="https://images.news18.com/ibnlive/uploads/2020/01/Capture5.jpg";
                }


                currNewsObj.setTitle(title);
                currNewsObj.setDescription(descrip);
                currNewsObj.setImgUrl(imgUrl);
                newsDataList.add(currNewsObj);
                Log.d("NEWS",currNewsObj.getTitle().toString());

                Log.d("NEWS",currNewsObj.getDescription().toString());

                Log.d("NEWS",currNewsObj.getImgUrl().toString());
            }


        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        newsAdapter = new NewsAdapter(newsDataList, this);
        recyclerView.setAdapter(newsAdapter);
    }


}


//    private void processImgBitmap(final Bitmap bitmap) {
//
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

//        FirebaseVisionOnDeviceImageLabelerOptions options = new FirebaseVisionOnDeviceImageLabelerOptions.Builder()
//                .setConfidenceThreshold(0.8f)
//                .build();

//
//        FirebaseVisionImageLabeler detector = FirebaseVision.getInstance().getOnDeviceImageLabeler(options);
//        detector.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
//            @Override
//            public void onSuccess(List<FirebaseVisionImageLabel> firebaseVisionImageLabels) {
//                Log.d("rohan :", "--------onSuccess--------------------");
//                if (imageLabelsVerified(firebaseVisionImageLabels)) {
//                    Toast.makeText(MainActivity.this, "Verified ok", Toast.LENGTH_SHORT).show();
//                    // UPLOAD BITMAP HERE
////                    Uri bitmapUri = getImageUri(MainActivity.this, bitmap);
////                    uploadImageUri(bitmapUri);
//                } else {
//                    Toast.makeText(MainActivity.this, "The pic doesn't seems to be of a pothole", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d("rohan :", "--------FAILED--------------------");
//            }
//        });
//    }