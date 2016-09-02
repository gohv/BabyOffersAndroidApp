package gohv.github.com.babyoffers.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gohv.github.com.babyoffers.AlertDialogs.AlertDialogNoNetwork;
import gohv.github.com.babyoffers.AlertDialogs.AlertDialogNoServer;
import gohv.github.com.babyoffers.R;
import gohv.github.com.babyoffers.model.Downloader;
import gohv.github.com.babyoffers.model.Offer;
import gohv.github.com.babyoffers.adapter.OfferAdapter;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private OfferAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            setContentView(R.layout.activity_main);

            gridView = (GridView) findViewById(R.id.gridview);

            initializeGridView();

            new DownloadTask().execute(new Range(0, 10));
        } else {
            AlertDialogNoNetwork noNetwork = new AlertDialogNoNetwork();
            noNetwork.show(getFragmentManager(), "get_message");

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private class Range {

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public int start;
        public int end;
    }

    private class DownloadTask extends AsyncTask<Range, List<Offer>, List<Offer>> {
        protected List<Offer> doInBackground(Range... ranges) {
            int pageSize = 30;
            int currentIndexStart = 0;
            int currentIndexEnd = pageSize;
            int lastIndex = 0;
            try {
                while (true) {

                    Downloader.Result result = new Downloader().getOffers(currentIndexStart, currentIndexEnd);

                    publishProgress(result.offers);

                    lastIndex = result.size - 1;
                    currentIndexStart += pageSize;
                    currentIndexEnd += pageSize;

                    if (currentIndexEnd > lastIndex) break;

                }
            } catch (Exception e) {

                displayMessage();
            }

            return null;

        }

        protected void onProgressUpdate(List<Offer>... progress) {
            adapter.offers.addAll(progress[0]);
            adapter.notifyDataSetChanged();
        }

        protected void onPostExecute(List<Offer> offers) {
        }
    }

    private void displayMessage() {
        AlertDialogNoServer dialog = new AlertDialogNoServer();
        dialog.show(getFragmentManager(), "display_message");


    }

    private void updateGridview(final List<Offer> offers) {

    }

    private void initializeGridView() {
        adapter = new OfferAdapter(this, new ArrayList<Offer>());

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                showDetails(adapter.offers.get(position));


            }
        });


    }

    private void showDetails(Offer offer) {
        View offerView = View.inflate(this, R.layout.offer_details, null);
        ImageView productImageView = (ImageView) offerView.findViewById(R.id.productImageView);
        TextView productNameTextView = (TextView) offerView.findViewById(R.id.nameTextView);
        TextView oldPriceTextView = (TextView) offerView.findViewById(R.id.oldPriceTextView);
        TextView newPriceTextView = (TextView) offerView.findViewById(R.id.newPriceTextView);
        TextView discountTextView = (TextView) offerView.findViewById(R.id.discountTextView);
        TextView shopNameTextView = (TextView) offerView.findViewById(R.id.storeNameTextView);
        TextView linkToItemTextView = (TextView) offerView.findViewById(R.id.linkToItemTextView);

        oldPriceTextView.setText(String.valueOf(offer.getOldPrice()));
        newPriceTextView.setText(String.valueOf(offer.getNewPrice()));
        discountTextView.setText(String.valueOf("-" + offer.getDiscount()) + "%");
        discountTextView.setBackgroundColor(Color.parseColor(offer.getDiscountColor(offer.getDiscount())));
        shopNameTextView.setText(String.valueOf("Store Name: " + offer.getShopName()));
        productImageView.setImageBitmap(offer.getProductImage());
        productNameTextView.setText(offer.getProductName());
        linkToItemTextView.setText(offer.getLinkToItem());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Offer Details:")
                .setView(offerView)
                .setCancelable(true).show();


    }


}
