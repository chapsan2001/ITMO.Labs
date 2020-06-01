package com.lab.common.exchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Response class contains response value and correctness value
 */
public final class Response implements Serializable {
    private boolean correct;
    private List<String> response;

    /**
     * Response constructor
     *
     * @param correct  correctness of response
     * @param response response in form of string list
     */
    public Response(boolean correct, List<String> response) {
        this.correct = correct;
        this.response = response;
    }

    /**
     * Response constructor
     *
     * @param correct correctness of response
     */
    public Response(boolean correct) {
        this.correct = correct;
        this.response = new ArrayList<>();
        this.response.add("");
    }

    /**
     * Response constructor
     *
     * @param correct  correctness of response
     * @param response response in form of string
     */
    public Response(boolean correct, String response) {
        this.correct = correct;
        this.response = new ArrayList<>();
        this.response.add(response);
    }

    /**
     * Returns response value
     *
     * @return response value
     */
    public List<String> getResponse() {
        return response;
    }

    /**
     * Returns string list as string
     *
     * @return String list as string
     */
    public String getString() {
        return String.join("\n", response);
    }

    /**
     * Returns correctness value
     *
     * @return Correctness value
     */
    public boolean isCorrect() {
        return correct;
    }
}
