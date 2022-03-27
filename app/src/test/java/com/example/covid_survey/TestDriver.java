package com.example.covid_survey;

public class TestDriver {
    public static void main(String[] args){

        FirstTest ft = new FirstTest();
        ft.setUp();

        ft.checkForInvalidInput();
        ft.checkForValidInput();

        ft.End();

    }

}
