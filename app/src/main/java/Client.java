import android.annotation.TargetApi;
import android.os.Build;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import mp3App.*;

public class Client {

	@TargetApi(Build.VERSION_CODES.KITKAT)
    public static void main(String[] args)
	{
		try(Communicator communicator = Util.initialize(args))
		{
			FunctionPrx function = FunctionPrx.checkedCast(communicator.stringToProxy("server:default -h localhost -p 10000"));

			// Music creation from various informations provided, for example, by the os
			Song bp = new Song("Broken People", "Logic", "Rap", "3:33", "C:/Music/Playlist/bp.mp3");
			Song hm = new Song("Home", "Machine Gun Kelly", "Rap", "3:52", "C:/Music/Playlist/hm.mp3");

			Song[] playList = {bp, hm};

			// Sending a playlist
			function.sendPlayList(playList);
			function.printPlayList();


			Song cf = new Song("California Love", "Dr.Dre", "Rap", "2:53", "C:/Music/Playlist/cf.mp3");
			Song bm = new Song("24K Magic", "Bruno Mars", "Funk", "3:47", "C:/Music/Playlist/bm.mp3");
			Song dk = new Song("Darkside", "Dr.Dre", "Rap", "3:33", "C:/Music/Playlist/dk.mp3");

			// Add music to the server playList
			function.add(cf);
			function.add(bm);
			function.add(dk);

			// Display the playlist on the server
			function.printPlayList();

			// Search musics from various criteria
			function.searchByName("24K Magic");
			function.searchByGenre("Rap");
			function.searchByArtist("Dr.Dre");

			// Remove a music from server's playlist
			function.remove("California Love");

			function.printPlayList();

			// Receive playlist from server and print it to Client side
			playList = function.receivePlaylist();
			for(int i = 0; i < playList.length; i++) {
				System.out.println(playList[i].name);
			}

			function.playMusic();


		}
	}
}
