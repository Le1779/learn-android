package kevin.le.learnandroid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import kevin.le.learnandroid.R;

public class NFCActivity extends AppCompatActivity {

    private TextView textView;
    private static final int flags = NfcAdapter.FLAG_READER_NFC_A;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        findViewById(R.id.backButton).setOnClickListener(view -> finish());
        textView = findViewById(R.id.textView_nfc_id);
    }

    @Override
    protected void onPause() {
        super.onPause();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null) {
            nfcAdapter.disableReaderMode(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                Log.d(this.getClass().getName(), "Please open NFC!");
                openAppSettingsIntent();
                return;
            }

            nfcAdapter.enableReaderMode(this, new NfcAdapter.ReaderCallback() {
                @Override
                public void onTagDiscovered(Tag tag) {
                    byte[] id = tag.getId();
                    Log.d(this.getClass().getName(), "Tag length: " + id.length);
                    Log.d(this.getClass().getName(), "Tag: " + toHex(id));
                    runOnUiThread(() -> textView.setText(toHex(id)));
                }
            }, flags, null);
        } else {
            Log.d(this.getClass().getName(), "Not support NFC!");
        }
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
        }

        return sb.toString();
    }

    private void openAppSettingsIntent() {
        startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
    }
}