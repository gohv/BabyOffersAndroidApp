package gohv.github.com.babyoffers.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import gohv.github.com.babyoffers.R;
import gohv.github.com.babyoffers.model.Offer;


public class OfferAdapter extends BaseAdapter {
    private Context context;
    public List<Offer> offers;
    private TextView nameTextView;
    private TextView oldPriceTextView;
    private TextView newPriceTextView;
    private ImageView productImageView;
    private TextView discountTextVIew;

    public OfferAdapter(Context context, List<Offer> offers) {
        this.context = context;
        this.offers = offers;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.offer, null);
        } else {
            gridView = convertView;
        }

        Offer offer = offers.get(position);

        nameTextView = (TextView) gridView.findViewById(R.id.nameTextView);
        nameTextView.setText(offer.getProductName());

        oldPriceTextView = (TextView) gridView.findViewById(R.id.oldPriceTextView);
        oldPriceTextView.setText(String.valueOf(offer.getOldPrice()));

        newPriceTextView = (TextView) gridView.findViewById(R.id.newPriceTextView);
        newPriceTextView.setText(String.valueOf(offer.getNewPrice()));

        discountTextVIew = (TextView) gridView.findViewById(R.id.discountTextView);
        discountTextVIew.setText(String.valueOf("-" + offer.getDiscount()) + "%");
        discountTextVIew.setBackgroundColor(Color.parseColor(offer.getDiscountColor(offer.getDiscount())));

        productImageView = (ImageView) gridView.findViewById(R.id.productImageView);

        new ImageDownloader(gridView).execute(offer);

        return gridView;
    }

    @Override
    public int getCount() {
        return offers.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ImageDownloader extends AsyncTask<Offer, Void, Offer> {
        ImageView image;
        ProgressBar progressBar;

        public ImageDownloader(View container)
        {
            this.image = (ImageView)container.findViewById(R.id.productImageView);
            this.progressBar = (ProgressBar)container.findViewById(R.id.progressBar1);
        }

        protected Offer doInBackground(Offer... urls) {
            Offer offer = urls[0];

            if (offer.getProductImage() == null) {
                String url = urls[0].getProductPhoto();

                Bitmap mIcon = null;
                try {
                    InputStream in = new java.net.URL(url).openStream();
                    mIcon = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                }

                offer.setProductImage(mIcon);
            }

            return offer;
        }

        protected void onPostExecute(Offer result)
        {
            image.setImageBitmap(result.getProductImage());
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}

