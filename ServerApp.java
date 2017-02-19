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
    
    String HTTPtype;
    String IPaddress;
    String Command;
    
    String str_200 = "CODE: 200";
    String str_304 = "CODE: 304";
    String str_404 = "CODE: 404";
    
    int Pdelay;
    int Tdelay;
    public static void main(String[] args) throws Exception
    {
        ServerApp s = new ServerApp();
        s.init();
    }
    public void init()  throws Exception
    {
        //create a new transport layer for server (hence true) (wait for client)
        this.transportLayer = new TransportLayer(true);
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
            System.out.println("Into switch");
            System.out.println("Input: " + input);
            System.out.println("First Char: " + inputFirstChar);
            switch(inputFirstChar){
                
                case "C": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Command = strParsedOutput;
                          System.out.println("COMMAND:\t" + strParsedOutput);
                          break;
                case "I": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          IPaddress = strParsedOutput;
                          System.out.println("IPADDRESS:\t" + strParsedOutput);
                          IPcomm = true;
                          break;
                case "P": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Pdelay = Integer.parseInt(strParsedOutput);
                          System.out.println("PROPDELAY:\t" + strParsedOutput);
                          sendTransport("Propagation Delay Set:\t" + Pdelay, codeThrow(200));
                          break;
                case "T": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Tdelay = Integer.parseInt(strParsedOutput);
                          sendTransport("Transmission Delay Set:\t" + Tdelay, codeThrow(200));
                          System.out.println("TRANSDELAY:\t" + strParsedOutput);
                          break;
                case "H": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          HTTPtype = strParsedOutput;
                          sendTransport("HTTP Version Selected:\t" + HTTPtype, codeThrow(200));
                          System.out.println("HTTP:\t\t" + strParsedOutput);
                          break;

                default: System.out.println("Please re-enter your command with prefix... \nCOMMAND:\nIP:\nHTTP:\nNUMBER:"); 
                         transportLayer.send(stringEncode("Please re-enter your command with prefix... \nCOMMAND:\nIP:\nHTTP:\nNUMBER:"));
                         break;
            }
            if(byteArray==null){
                System.out.println("Null Input");
                break;
            }
            
            //check if requested item can be found
            if(checkFound(IPaddress)){
            }else{
             codeThrow(404);
            }
            //check if requested item is modified
            if(checkModified(IPaddress)){
                
            }else{
             codeThrow(304);
            }
            //if not either of those, ok
            
            System.out.println("\tSending...");
            if(IPcomm){
                //FIX CODE THROWS
                //codeThrow(200);
                System.out.println("\tReading File");
                addressRead(IPaddress);
                IPcomm = false;
            }

        }
    }
    public void addressRead(String address){
        String toSend = "";
        try {
            System.out.println("Address:\t\t\t\t" + address);
            //fileReader = new FileReader(address);
            //buffReader = new BufferedReader(fileReader);

            String sCurrentLine;

            buffReader = new BufferedReader(new FileReader(address));
            while ((sCurrentLine = buffReader.readLine()) != null) {
                System.out.println("\tLine: " + sCurrentLine);
                toSend += sCurrentLine;
                toSend += "\n";
            }
            //transportLayer.send(stringEncode(toSend));
            sendTransport(toSend, codeThrow(200));
        } 
        catch (FileNotFoundException e) {
            //address/ page specified doesn't exist
            //transportLayer.send(stringEncode(codeThrow(404)));
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
        System.out.println(stringEncode(code + "//" + payload));
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
