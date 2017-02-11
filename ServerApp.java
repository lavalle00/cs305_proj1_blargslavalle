import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

//This class represents the server application
public class ServerApp
{

    public static void main(String[] args) throws Exception
    {
        //create a new transport layer for server (hence true) (wait for client)
        TransportLayer transportLayer = new TransportLayer(true);
        while( true )
        {
            //receive message from client, and send the "received" message back.
            byte[] byteArray = transportLayer.receive();
            //if client disconnected
            String input = new String (byteArray);
            String shortInput = input.substring(0,1);
            String[] arrStr_Output;
            String m_Output;
            switch(shortInput){
                case "C": arrStr_Output = input.split(" ");
                          m_Output= arrStr_Output[1];
                          System.out.println("COMMAND:\t" + m_Output);
                          break;
                case "I": arrStr_Output = input.split(" ");
                          m_Output= arrStr_Output[1];
                          System.out.println("IP ADDRESS:\t" + m_Output);
                          break;
                case "H": arrStr_Output = input.split(" ");
                          m_Output= arrStr_Output[1];
                          System.out.println("HTTP?:\t\t" + m_Output);
                          break;
                case "N": arrStr_Output = input.split(" ");
                          m_Output= arrStr_Output[1];
                          System.out.println("HTTP PROTOCOL:\t" + m_Output);
                          break;
                default: break;
            }
            if(byteArray==null){
                System.out.println("Null Input");
                break;
            }
            String str = new String ( byteArray );
            String line = "received";
            byteArray = line.getBytes();
            transportLayer.send( byteArray );

        }
    }
}
