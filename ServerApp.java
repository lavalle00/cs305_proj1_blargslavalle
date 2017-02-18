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
            //if client disconnected
            String input = new String (byteArray);
            String inputFirstChar = input.substring(0,1);
            String[] arrStr_Output;
            String strParsedOutput;
            switch(inputFirstChar){
                case "C": arrStr_Output = input.split(" ");
                          strParsedOutput= arrStr_Output[1];
                          System.out.println("\tCOMMAND:\t" + strParsedOutput);
                          break;
                case "I": arrStr_Output = input.split(" ");
                          strParsedOutput= arrStr_Output[1];
                          System.out.println("\tIP ADDRESS:\t" + strParsedOutput);
                          break;
                case "H": arrStr_Output = input.split(" ");
                          strParsedOutput= arrStr_Output[1];
                          System.out.println("\tHTTP PROTOCOL:\t\t" + strParsedOutput);
                          break;

                default: System.out.println("Please re-enter your command with prefix... \nCOMMAND:\nIP:\nHTTP:\nNUMBER:"); 
                         break;
            }
            if(byteArray==null){
                System.out.println("Null Input");
                break;
            }
            String str = new String ( byteArray );
            String line = "received";
            byteArray = line.getBytes();
            //check if requested item can be found
            //check if requested item is modified
            //if not eithger of those, ok
            
            transportLayer.send( byteArray );

        }
    }
    public void addressRead(String address){
        try {

			fileReader = new FileReader(address);
			buffReader = new BufferedReader(fileReader);

			String sCurrentLine;

			buffReader = new BufferedReader(new FileReader(address));
			while ((sCurrentLine = buffReader.readLine()) != null) {
				System.out.println(sCurrentLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (buffReader != null)
					buffReader.close();

				if (fileReader != null)
					fileReader.close();
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
    private byte[] stringEncode(String string){
        return string.getBytes();
    }
}
