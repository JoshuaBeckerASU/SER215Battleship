/********************** 
Name: main 
Author: Joshua Becker
Create On: 9/9/15
Updated On: 9/17/15
Contributors:
***********************/

public class Main
{
    public static void main(String[] args)
    {
		//need loading screen for this
		LoadAssets assets = new LoadAssets();
		// end loading screen
		MenuWindow menu = new MenuWindow(assets);
    }
}