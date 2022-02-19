package com.mercadolibre.mercadolibrecountry.utils;

public class UtilsService
{
    public static String getOperatingSystem()
    {
        String os = System.getProperty("os.name");
        // System.out.println("Using System Property: " + os);
        return os;
    }
}
