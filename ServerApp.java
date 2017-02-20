import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.*;
import java.io.IOException;
import java.util.Arrays;
//This class represents the server application
public class ServerApp
{
    TransportLayer transportLayer;
    BufferedReader buffReader;
    FileReader     fileReader;
    
    String ipAddress;
    String command;
    
    String str_200 = "CODE: 200";
    String str_304 = "CODE: 304";
    String str_404 = "CODE: 404";
    boolean handshake = false;
    int pDelay = 10;
    int tDelay = 2;
    //expected arguments pDelay, tDelay, httpProt.
    public static void main(String[] args) throws Exception
    {
        ServerApp s = new ServerApp();
        if(args != null){
            s.init(args);
        }
        else{
            s.init(null);
        }
    }
    public void init(String[] args)  throws Exception
    {
        
        if(args != null && args.length == 2){
            switch(args.length){
            //only delays
            case 2:
                pDelay = Integer.parseInt(args[0]);
                tDelay = Integer.parseInt(args[1]);
                break;
            }
            System.out.println("Propagation Delay:\t" + pDelay + "\nTransmission Delay:\t" + tDelay);
        }
        else{
            System.out.println("Please enter arguments in the form '<num>', '<num>'\nProgram will now exit...");
            System.exit(1);
        }
        //create a new transport layer for server (hence true) (wait for client)
        this.transportLayer = new TransportLayer(true, pDelay, tDelay);
        if(!handshake){
            transportLayer.receive();
            handshake = true;
            transportLayer.send(stringEncode("ack"));
        }
        while( true )
        {
            //receive message from client, and send the "received" message back.
            byte[] byteArray = transportLayer.receive();
            //System.out.println(byteArray);
            //if client disconnected
            String input = new String (byteArray);
            String inputFirstChar = input.substring(0,1);
            String[] arrStr_Output;
            String strParsedOutput;
            boolean IPcomm = false;
            //System.out.println("Input: " + input);
            //System.out.println("First Char: " + inputFirstChar);
            switch(inputFirstChar){
                // command: GET
                case "C": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          command = strParsedOutput;
                          System.out.println("command:\t" + strParsedOutput);
                          break;
                //IP: <page>
                case "I": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          ipAddress = strParsedOutput;
                          System.out.println("ipAddress:\t" + strParsedOutput);
                          IPcomm = true;
                          break;
                //PROP: <str>
                case "P": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          pDelay = Integer.parseInt(strParsedOutput);
                          System.out.println("PROpDelay:\t" + strParsedOutput);
                          sendTransport("Propagation Delay Set:\t" + pDelay, codeThrow(200));
                          break;
                //TRANS: <str>
                case "T": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          tDelay = Integer.parseInt(strParsedOutput);
                          sendTransport("Transmission Delay Set:\t" + tDelay, codeThrow(200));
                          System.out.println("TRANSDELAY:\t" + strParsedOutput);
                          break;
                //Handshake
                case "S": //System.out.println("Syn recieved");
                          transportLayer.send(stringEncode("ack"));
                          //System.out.println("ack sent");
                          IPcomm = false;
                          break;
                default: System.out.println("Please re-enter your command with prefix... \ncommand:\nIP:\nHTTP:\nNUMBER:"); 
                         transportLayer.send(stringEncode("Please re-enter your command with prefix... \ncommand:\nIP:\nHTTP:\nNUMBER:"));
                         break;
            }
            if(byteArray==null){
                System.out.println("Null Input");
                break;
            }
            
            //check if requested item can be found
            if(checkFound(ipAddress)){
            }else{
                System.out.println("IP not found"); 
             codeThrow(404);
            }
            //check if requested item is modified
            if(checkModified(ipAddress)){
                
            }else{
             codeThrow(304);
            }
            //if not either of those, ok
            
            //System.out.println("\tSending...");
            if(IPcomm){
                //FIX CODE THROWS
                //codeThrow(200);
                //System.out.println("\tReading File");
                addressRead(ipAddress);
                IPcomm = false;
            }
            System.out.println("#################");
        }
       }
    
    public void addressRead(String address){
        String toSend = "";
        try {
            //System.out.println("Address:\t\t\t\t" + address);
            //fileReader = new FileReader(address);
            //buffReader = new BufferedReader(fileReader);

            String sCurrentLine;

            buffReader = new BufferedReader(new FileReader(address));
            while ((sCurrentLine = buffReader.readLine()) != null) {
                //System.out.println("\tLine: " + sCurrentLine);
                toSend += sCurrentLine;
                toSend += "\n";
            }
            //transportLayer.send(stringEncode(toSend));
            sendTransport(toSend, codeThrow(200));
        } 
        catch (FileNotFoundException e) {
            //address/ page specified doesn't exist
            //transportLayer.send(stringEncode(codeThrow(404)));
            System.out.println("Exception found"); 
            sendTransport(codeThrow(404));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (buffReader != null){
                    buffReader.close();
                }

                if (fileReader != null){
                    fileReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    //for sending pure codes
    private void sendTransport(String code){
        transportLayer.send(stringEncode(code));
    }
    //for sending string-built code + payload
    private void sendTransport(String payload, String code){
        //System.out.println(stringEncode(code + "//" + payload));
        transportLayer.send(stringEncode(code + "//" + payload));
    }
    public String codeThrow(int code){
        switch(code) {
            case 200:
                System.out.println(str_200);
                return str_200 ;
            case 404:
                System.out.println(str_404);
                return str_404;
            case 304:
                System.out.println(str_304);
                return str_304;
            default:
                return str_404;
        }
    }
    public boolean checkFound(String file){
        return true;
    }
    public boolean checkModified(String file){
        return true;
    }
    private byte[] stringEncode(String string){
        return string.getBytes();
    }
}
