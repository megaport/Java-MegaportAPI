package com.megaport.api.client;

import com.megaport.api.dto.Environment;

/**
 * Created by adam.wells on 21/07/2016.
 */
public class SpeedChanger {

    public static void main(String[] args) throws Exception{

        SpeedChanger speedChanger = new SpeedChanger();

        if (args.length != 7){
            System.out.println("Usage is 'java -jar client-1.1.0.jar <environment> <apiKey> <productUid> <hours to check usage> <margin> <min speed> <max speed>'");
        }

        String apiKey = args[0];
        String productUid = args[1];
        Environment environment = Environment.valueOf(args[2]);
        Integer hours = Integer.valueOf(args[3]);
        Integer margin = Integer.valueOf(args[4]);
        Integer min = Integer.valueOf(args[5]);
        Integer max = Integer.valueOf(args[6]);

        speedChanger.updateSpeed(apiKey, productUid, environment, hours, margin, min, max);

    }

    public void updateSpeed(String apiKey, String productUid, Environment environment, Integer hours, Integer margin, Integer min, Integer max) throws Exception{

        MegaportApiSession session = new MegaportApiSession(environment, apiKey);
        session.autoSpeedUpdate(productUid, hours, margin, min, max);

    }

}
