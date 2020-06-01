package com.lab.common.user.hashGenerator;

import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SHA1Generator extends HashGenerator {
    @Override
    protected String generateHash(String string) throws HashGeneratorException {
        String sha384;

        try {
            MessageDigest msdDigest = MessageDigest.getInstance("SHA-1");
            msdDigest.update(string.getBytes(StandardCharsets.UTF_8), 0, string.length());
            sha384 = DatatypeConverter.printHexBinary(msdDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new HashGeneratorException(e);
        }

        return sha384;
    }
}
