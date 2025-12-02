package com.flightapp.common.dto;



public record ApiResponse(int status, String message, Object data) {}
