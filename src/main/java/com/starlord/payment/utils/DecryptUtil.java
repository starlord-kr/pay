package com.starlord.payment.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;

public final class DecryptUtil {
    private static final String SECRET_KEY = "1234567890123456";
    private static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

    public static String decrypt(String target) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(DECRYPT_MODE, SECRET_KEY_SPEC);
        return new String(
            cipher.doFinal(Base64.getDecoder().decode(target)));
    }

}
