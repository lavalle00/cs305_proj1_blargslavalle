------------------------------------------------------------------------
This is the project README file. Here, you should describe your project.
Tell the reader (someone who does not know anything about this project)
all he/she needs to know. The comments should usually include at least:
------------------------------------------------------------------------

PROJECT TITLE: cs305_proj1_blargslavalle
PURPOSE OF PROJECT: Simulate network through all layers
VERSION or DATE: RC1.0
HOW TO START THIS PROJECT: 
	open 2 bash/cmd/powershell windows in cs305_proj1_blargslavalle directory
	1st window:
		java ServerApp <propDelay> <transDelay>
	2nd window:
		java ClientApp <httpVersion>
	from there, in the client window, use "IP: <testFile.txt" to initiate a testFile
	
	an example "flow" of this is as follows:
	
	
	Server: 
		java ServerApp 10 3   #<---- Where 10 and 3 are in ms
	
	Client:
		java ClientApp 1.0    #<---- Writing "1" works too. 
		IP: test.txt 
		
		#output will be the file requested, along with the RTT in ms, 
		#and a "hash break" (########) indicating the client is ready. 

AUTHORS: Spencer LaValle, Chris VanBlargen
USER INSTRUCTIONS: 
	See: "HOW TO START THIS PROJECT:" and note: we have a bug where RTT is incorrectly calculated
	after first run. Simply restart both server and client to ensure correct RTT calculation. 
