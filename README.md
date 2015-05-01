# rename-season-episodes
It renames Tv series episodes and subtitle names in a formatted style.
For e.g.,
Tv Series : How I Met Your Mother
    episode name : HIMYM.S01E02.Purple.Giraffe.avi
    subtitle name : HIMYM.S01E02.Purple.Giraffe.srt
    *As I chose to use HIMYM as shortname for "How I Met Your Mother"
Tv Series : Friends
    episode name : Friends.S01E01.The.One.Where.Monica.Gets.A.Roommate.avi
    subtitle name : Friends.S01E01.The.One.Where.Monica.Gets.A.Roommate.srt
    *No short name chosen here.
    
This project does not support command line yet. All the code is in FileRenaming.java. 
You have to set all paths and anmes in variables and run the java file.

It uses regular expression to deduce that filename is what episode and what sesason. 
It requires a file named episodes.txt inside season folder.
E.g.
   A:\TV-Series\Battlestar Galactica\season 1\episodes.txt
   A:\TV-Series\Battlestar Galactica\season 2\episodes.txt
   
  With content like:
  Battlestar Galactica - 01x01 - 33
	Battlestar Galactica - 01x02 - Water
	Battlestar Galactica - 01x03 - Bastille day
	Battlestar Galactica - 01x04 - Act of contrition
