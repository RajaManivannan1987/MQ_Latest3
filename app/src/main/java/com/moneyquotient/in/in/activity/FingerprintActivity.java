package com.moneyquotient.in.in.activity;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.moneyquotient.in.in.R;
import com.moneyquotient.in.in.common.CommonBackActivity;
import com.moneyquotient.in.in.common.CommonMethod;
import com.moneyquotient.in.in.utlity.sharedPreferance.PassocodePreferance;
import com.moneyquotient.in.in.utlity.sharedPreferance.Session;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by karth on 8/22/2017.
 */

public class FingerprintActivity extends CommonBackActivity {


    @BindView(R.id.passcode_finger_icon)
    ImageView passcodeFingerIcon;
    @BindView(R.id.passcode_or)
    TextView passcodeOr;
    @BindView(R.id.passcode_code)
    EditText passcodeCode;
    @BindView(R.id.passcode_confirm_code)
    EditText passcodeConfirmCode;
    @BindView(R.id.passcode)
    LinearLayout passcode;
    @BindView(R.id.passcode_layout)
    LinearLayout passcodeLayout;
    @BindView(R.id.passcode_cancel)
    Button passcodeCancel;
    @BindView(R.id.passcode_submit)
    Button passcodeSubmit;


    private KeyStore keyStore;
    // Variable used for storing the key in the Android Keystore container
    private static final String KEY_NAME = "MQ";
    private Cipher cipher;

    private static final String TAG = FingerprintActivity.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setView(R.layout.activity_fingerprint, "Money Quotient");
        ButterKnife.bind(this);
        if (!getIntent().getStringExtra("text").equalsIgnoreCase("")) {
            passcodeFingerIcon.setVisibility(View.GONE);
        } else {
            passcodeCancel.setVisibility(View.GONE);
            passcodeConfirmCode.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                passcodeFingerIcon.setVisibility(View.VISIBLE);
                passcodeOr.setVisibility(View.VISIBLE);
                checkFingerPrint();
            } else {
                passcodeFingerIcon.setVisibility(View.GONE);
                passcodeOr.setVisibility(View.GONE);
            }
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFingerPrint() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        if (!fingerprintManager.isHardwareDetected()) {
//            CommonMethod.showSnackbar(passcode, "Your Device does not have a Fingerprint Sensor");
            passcodeFingerIcon.setVisibility(View.GONE);
            passcodeOr.setVisibility(View.GONE);

        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
//                CommonMethod.showSnackbar(passcode, "Fingerprint authentication permission not enabled");
                passcodeFingerIcon.setVisibility(View.GONE);
                passcodeOr.setVisibility(View.GONE);
            } else {
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    passcodeFingerIcon.setVisibility(View.VISIBLE);
                    passcodeOr.setVisibility(View.VISIBLE);
                    CommonMethod.showSnackbar(passcode, "Register at least one fingerprint in Settings");
                } else {
                    if (!keyguardManager.isKeyguardSecure()) {
                        passcodeFingerIcon.setVisibility(View.VISIBLE);
                        passcodeOr.setVisibility(View.VISIBLE);
                        CommonMethod.showSnackbar(passcode, "Lock screen security not enabled in Settings");
                    } else {
                        generateKey();
                        if (cipherInit()) {
                            FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
                            FingerprintHandler helper = new FingerprintHandler(FingerprintActivity.this, passcode);
                            helper.startAuth(fingerprintManager, cryptoObject);
                        }
                    }
                }
            }
        }

    }


    @TargetApi(Build.VERSION_CODES.M)
    protected void generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (Exception e) {
            e.printStackTrace();
        }


        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }


        try {
            keyStore.load(null);
            keyGenerator.init(new
                    KeyGenParameterSpec.Builder(KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException |
                InvalidAlgorithmParameterException
                | CertificateException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public boolean cipherInit() {
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }


        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @OnClick({R.id.passcode_cancel, R.id.passcode_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.passcode_cancel:
                finish();
                break;
            case R.id.passcode_submit:
                if (PassocodePreferance.getInstance(getApplicationContext(), TAG).getPasscodeValue().equalsIgnoreCase("")) {
                    if (CommonMethod.checkEqual(passcodeCode, passcodeConfirmCode, "Enter Confirm Passcode Correctly")) {
                        PassocodePreferance.getInstance(getApplicationContext(), TAG).createPasscode("Passcode", passcodeCode.getText().toString());
                        finish();
                    }
                } else if (getIntent().getStringExtra("text").equalsIgnoreCase("Change Password")) {
                    PassocodePreferance.getInstance(getApplicationContext(), TAG).createPasscode("Passcode", passcodeCode.getText().toString());
                    finish();
                } else {
                    if (PassocodePreferance.getInstance(getApplicationContext(), TAG).getPasscodeValue().equalsIgnoreCase(passcodeCode.getText().toString())) {
                        passcodeCode.setError(null);
                        if (Session.getInstance(getApplicationContext(), TAG).getIsLogin()) {
                            CommonMethod.changeActivity(FingerprintActivity.this, WealthSummaryActivity.class);
                            finish();
                        } else {
                            CommonMethod.changeActivity(FingerprintActivity.this, DashBoardActivity.class);
                            finish();
                        }
                    } else {
                        passcodeCode.setText("Invalid Passcode");
                    }
                }
                break;
        }
    }
}
