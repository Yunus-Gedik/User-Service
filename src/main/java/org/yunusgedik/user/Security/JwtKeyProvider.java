package org.yunusgedik.user.Security;

import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Component
public class JwtKeyProvider {

    private final PrivateKey privateKey;

    public JwtKeyProvider() throws Exception {
        String key = Files.readString(Paths.get("src/main/resources/keys/jwt_private.pem"))
            .replaceAll("-----\\w+ PRIVATE KEY-----", "")
            .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        this.privateKey = kf.generatePrivate(spec);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}