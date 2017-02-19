import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
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
            System.out.println(byteArray);
            //if client disconnected
            String input = new String (byteArray);
            String inputFirstChar = input.substring(0,1);
            String[] arrStr_Output;
            String strParsedOutput;
            System.out.println("Into switch");
            System.out.println("Input: " + input);
            System.out.println("First Char: " + inputFirstChar);
            switch(inputFirstChar){
                
                case "C": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Command = strParsedOutput;
                          System.out.println("\tCOMMAND:\t" + strParsedOutput);
                          break;
                case "I": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          IPaddress = strParsedOutput;
                          System.out.println("\tIPADDRESS:\t" + strParsedOutput);
                          break;
                case "P": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Pdelay = Integer.parseInt(strParsedOutput);
                          System.out.println("\tPropagationDelay:\t" + strParsedOutput);
                          break;
                case "T": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          Tdelay = Integer.parseInt(strParsedOutput);
                          System.out.println("\tTransmissionDelay:\t" + strParsedOutput);
                          break;
                case "H": arrStr_Output = input.split(" ");
                          strParsedOutput = arrStr_Output[1];
                          HTTPtype = strParsedOutput;
                          System.out.println("\tHTTP:\t\t" + strParsedOutput);
                          break;

                default: System.out.println("Please re-enter your command with prefix... \nCOMMAND:\nIP:\nHTTP:\nNUMBER:"); 
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
            codeThrow(200);
            System.out.println("\tSending...");
            //transportLayer.send(stringEncode(IPaddress));

        }
    }
    public void addressRead(String address){
        try {

            fileReader = new FileReader(address);
            buffReader = new BufferedReader(fileReader);

            String sCurrentLine;

            buffReader = new BufferedReader(new FileReader(address));
            while ((sCurrentLine = buffReader.readLine()) != null) {
                transportLayer.send(stringEncode(sCurrentLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
    public void codeThrow(int code){
        switch(code) {
            case 200:
                System.out.println("\n\tCode:\t200\t-Ok");
                transportLayer.send(stringEncode("\n\tCode:\t200\t-Ok"));
                break;
            case 404:
                System.out.println("\n\tCode:\t404\t-Not Found");
                transportLayer.send(stringEncode("\n\tCode:\t404\t-Not Found"));
                break;
            case 304:
                System.out.println("\n\tCode:\t304\t-Not Modified");
                transportLayer.send(stringEncode("\n\tCode:\t304\t-Not Modified"));
                break;
            default:
                break;
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
