package com.example.e610.capstoneproject.Utils;


public interface NetworkResponse {


    void OnSuccess(String JsonData);

    void OnFailure(boolean Failure);
}
