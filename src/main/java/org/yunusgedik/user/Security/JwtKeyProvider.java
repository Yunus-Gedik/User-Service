package org.yunusgedik.user.Security;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Getter
@Component
public class JwtKeyProvider {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public JwtKeyProvider() throws Exception {
        initPrivateKey();
        initPublicKey();
    }

    private void initPrivateKey() throws Exception {
        String key = Files.readString(Paths.get("src/main/resources/keys/jwt_private.pem"))
            .replaceAll("-----\\w+ PRIVATE KEY-----", "")
            .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        this.privateKey = kf.generatePrivate(spec);
    }

    private void initPublicKey() throws Exception {
        String key = Files.readString(Paths.get("src/main/resources/keys/jwt_public.pem"))
            .replaceAll("-----\\w+ PUBLIC KEY-----", "")
            .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        this.publicKey = kf.generatePublic(spec);
    }

}