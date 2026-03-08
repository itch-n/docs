package com.study.systems.security;

import java.util.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * JWT Authentication: Stateless token-based auth
 *
 * Token structure: header.payload.signature
 * - Header: algorithm and token type
 * - Payload: claims (user data, expiration)
 * - Signature: HMAC of header+payload with secret
 */
public class JWTAuthenticator {

    private final String secret;
    private final long expirationMs;

    public JWTAuthenticator(String secret, long expirationMs) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    /**
     * Generate JWT token for user
     * Time: O(1), Space: O(1)
     *
     * TODO: Implement JWT generation
     * 1. Create header: {"alg": "HS256", "typ": "JWT"}
     * 2. Create payload: {"sub": userId, "exp": expiration, "iat": issuedAt}
     * 3. Base64 encode header and payload
     * 4. Sign with HMAC-SHA256
     * 5. Return header.payload.signature
     */
    public String generateToken(String userId) {
        // TODO: Build the header JSON string: {"alg":"HS256","typ":"JWT"} and base64UrlEncode it

        // TODO: Build the payload JSON string with "sub", "iat" (System.currentTimeMillis()), and "exp" (iat + expirationMs), then base64UrlEncode it

        // TODO: Compute the signature: hmacSha256(encodedHeader + "." + encodedPayload, secret)

        // TODO: Return encodedHeader + "." + encodedPayload + "." + signature

        return null; // Replace
    }

    /**
     * Validate and extract user from JWT
     * Time: O(1), Space: O(1)
     *
     * TODO: Implement JWT validation
     * 1. Split token into parts
     * 2. Verify signature
     * 3. Check expiration
     * 4. Extract and return user ID
     */
    public String validateToken(String token) {
        // TODO: Split token on "." — expect exactly 3 parts (header, payload, signature); return null if malformed

        // TODO: Recompute expectedSig = hmacSha256(parts[0] + "." + parts[1], secret); return null if it doesn't match parts[2]

        // TODO: Base64-decode parts[1], parse the JSON to extract "exp"; return null if System.currentTimeMillis() > exp

        // TODO: Parse the decoded payload JSON to extract "sub" (the userId) and return it
        return null; // Replace
    }

    /**
     * Helper: Base64 URL-safe encoding
     *
     * TODO: Implement base64 URL encoding
     */
    private String base64UrlEncode(String input) {
        // TODO: Use Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8)),
        //       then replace '+' with '-', '/' with '_', and strip trailing '=' padding
        return null; // Replace
    }

    /**
     * Helper: HMAC-SHA256 signature
     *
     * TODO: Implement HMAC signing
     */
    private String hmacSha256(String data, String key) {
        // TODO: Get a Mac instance for "HmacSHA256", init it with new SecretKeySpec(key.getBytes(), "HmacSHA256"),
        //       call mac.doFinal(data.getBytes()), then base64UrlEncode the resulting byte array
        return null; // Replace
    }
}
