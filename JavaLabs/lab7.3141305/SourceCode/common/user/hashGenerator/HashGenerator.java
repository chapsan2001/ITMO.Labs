package com.lab.common.user.hashGenerator;

import com.lab.common.user.hashGenerator.exceptions.HashGeneratorException;

public abstract class HashGenerator {
    protected final String salt;
    protected final String pepper;

    protected HashGenerator() {
        salt = "!@-)2903##";
        pepper = "a.;(D02@@";
    }

    /**
     * Generates hash.
     *
     * @param string concrete string to hash.
     * @return Hashed string.
     * @throws HashGeneratorException if algorithm not found.
     */
    protected abstract String generateHash(String string) throws HashGeneratorException;

    /**
     * Generates hash with salt.
     *
     * @param string concrete string with salt to hash.
     * @return Hashed string.
     * @throws HashGeneratorException if algorithm not found.
     */
    public final String generateHashWithSalt(String string) throws HashGeneratorException {
        String stringWithSalt = string + salt;
        return generateHash(stringWithSalt);
    }

    /**
     * Generates hash with pepper and salt.
     *
     * @param string concrete string with pepper and salt to hash.
     * @return Hashed string.
     * @throws HashGeneratorException if algorithm not found.
     */
    public final String generateHashWithPepperAndSalt(String string) throws HashGeneratorException {
        String stringWithPepperAndSalt = pepper + string + salt;
        return generateHash(stringWithPepperAndSalt);
    }
}
