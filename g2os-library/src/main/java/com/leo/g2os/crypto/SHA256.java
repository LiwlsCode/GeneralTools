package com.leo.g2os.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright 2011 Google Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class SHA256 {

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);  // Can't happen.
        }
    }

    private static final MessageDigest digest;

    /**
     * See com.google.SHA256.core.Utils#doubleDigest(byte[], int, int).
     *
     * @param input input
     * @return doubleDigest result
     */
    public static byte[] doubleDigest(byte[] input) {
        return doubleDigest(input, 0, input.length);
    }

    /**
     * Calculates the SHA-256 hash of the given byte range, and then hashes the resulting hash again. This is
     * standard procedure in Bitcoin. The resulting hash is in big endian form.
     *
     * @param input  input
     * @param offset offset
     * @param length length
     * @return doubleDigest result
     */
    public static byte[] doubleDigest(byte[] input, int offset, int length) {
        synchronized (digest) {
            digest.reset();
            digest.update(input, offset, length);
            byte[] first = digest.digest();
            return digest.digest(first);
        }
    }

    /**
     * digest sha256
     *
     * @param input input
     * @return digest result
     */
    public static byte[] digest(byte[] input) {
        return digest(input, 0, input.length);
    }

    /**
     * digest sha256
     *
     * @param input  input
     * @param offset offset
     * @param length length
     * @return digest sha256 result
     */
    public static byte[] digest(byte[] input, int offset, int length) {
        synchronized (digest) {
            digest.reset();
            digest.update(input, offset, length);
            return digest.digest();
        }
    }
}
