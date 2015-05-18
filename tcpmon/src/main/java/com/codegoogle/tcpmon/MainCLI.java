/*
 * Copyright (c) 2004-2011 tcpmon authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codegoogle.tcpmon;

/**
 * Start tcpmon without UI interface 
 */
public class MainCLI  {
    
    public static void main(String[] args) {
        if (args.length==0) {
            System.out.println("Usage: java -classpath tcpmon.jar com.codegoogle.tcpmon.MainCLI -remotehost <host> -remoteport <port> -localport <port>  [-ssl] [-debuglevel <level 0..n>]");
            return;
        }
        
        // custom argument, Main.parseArgs does not recognize this
        boolean useSSL=false;
        for(int idx=0; idx<args.length; idx++) {
            if (args[idx].equals("-ssl")) {
                useSSL = true;
                break;
            }
        }
        
        Configuration configuration = new Main().parseArgs(args);
        configuration.setAutoStart(true); // always autostart
        Debug.level = configuration.getDebugLevel();
        TunnelConfig tc =  new TunnelConfig(
                configuration.getRemoteHost(),  // destination host
                configuration.getRemotePort(),  // destination port
                configuration.getLocalPort(),   // tcpmon server port where clients connect to
                configuration.isAutoStart(), 
                useSSL
        );

        // print original request(forward data) and response (reverse data),
        // tcpmon tunnel calls this after http connection was closed so it takes
        // 10-60 seconds before you see anything. 
        CallBack callback = new CallBack(){
            @Override
            public void connectionFinished(CallBackData data) {
                System.out.println("---------");
                System.out.println(data.getState()); 
                System.out.println(data.getForwardData());
                System.out.println("");
                System.out.println(data.getReverseData());
                System.out.println("---------");
            }
        }; 
        
        System.out.println("Starting tcpmon cli, press CTRL+C to stop application");
        new Tunnel(tc, callback);
        //Tunnel tunnel = new Tunnel(tc, callback);
        //tunnel.stopNow();
    }

}

