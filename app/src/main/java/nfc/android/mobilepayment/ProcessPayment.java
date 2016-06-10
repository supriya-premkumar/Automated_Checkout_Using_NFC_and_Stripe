package nfc.android.mobilepayment;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
public class ProcessPayment extends AppCompatActivity {
    public static float itemPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_payment);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MobilePayment.EXTRA_MESSAGE);
        Log.v("SyncDisplay", message);
        TextView textView = (TextView) findViewById(R.id.sync_message);
        textView.setText(message);
        Button confirmButton = (Button)  findViewById(R.id.confirm_payment);
        Button backButton = (Button)  findViewById(R.id.decline_payment);
        String [] parts = message.split("\\$");
        itemPrice = Float.valueOf(parts[1]);


    }

    public void onClick(View v) {
        if(v.getId() == R.id.confirm_payment) {
            Log.v("SyncDisplay:", String.valueOf(itemPrice));
            ChargeCard(itemPrice);
        }

        if(v.getId()== R.id.decline_payment) {
            Intent intent;
            intent = new Intent(this, MobilePayment.class);
            startActivity(intent);

        }
    }

    public boolean ChargeCard(final float itemPrice) {
        int expMonth = 06;
        int expYear = 2016;
        String cvc = "493";
        boolean result = false;
        Card card = new Card("4242424242424242",
                expMonth,
                expYear,
                cvc
        );

        Log.v("CARD:", String.valueOf(card.validateCard()));

        try {
            Stripe stripe = new Stripe("<INSERT_PRIMARY_KEY_HERE>");
            stripe.createToken(
                    card,
                    new TokenCallback() {
                        public void onSuccess(Token token) {
                            // Send token to your server
                            //Log.v("TEMP", String.valueOf(token));
                            ProcessCharge(token, itemPrice);
                        }

                        public void onError(Exception error) {
                            // Show localized error message
                            Toast.makeText(getApplicationContext(),
                                    "Something Broke",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
            );
        }
        catch (final com.stripe.exception.AuthenticationException e) {
            System.out.println("Invalid API Key, Check Stripe Dashboard");

        }



        return result;


    }


    private class DownloadWebpageTask extends AsyncTask<MyTaskParams, Void, String> {
        @Override
        protected String doInBackground(MyTaskParams... params) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(params[0].stringUrl, params[0].token, params[0].itemPriceInCents);
            } catch (java.io.IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.v("RESULT:", result);
        }
    }

    private String downloadUrl(String myurl, Token token, long itemPriceInCents) throws java.io.IOException {
        String result = "FAIL";

        try {
            URL url = new URL(myurl);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://<Your Secure Payment processing endpoint>"));
            startActivity(browserIntent);
        } finally {
            return result;

        }
    }

    public String readIt(InputStream stream, int len) throws java.io.IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    private static class MyTaskParams {
        Token token;
        String stringUrl;
        long itemPriceInCents;

        MyTaskParams(Token token, String stringUrl, long itemPriceInCents) {
            this.token = token;
            this.stringUrl = stringUrl;
            this.itemPriceInCents = itemPriceInCents;
        }
    }


    void ProcessCharge(Token token, Float itemPrice){
        long itemPriceInCents = itemPrice.longValue() * 100;
        String stringUrl = "https://supriyapremkumar.me/charge.php";
        MyTaskParams params = new MyTaskParams(token, stringUrl, itemPriceInCents);


        new DownloadWebpageTask().execute(params);
        

    }



}
