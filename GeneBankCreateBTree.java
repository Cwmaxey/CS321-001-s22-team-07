import java.io.*;
import java.util.Scanner;

public class GeneBankCreateBTree
{
	private static BTree tree;
	//private static BTreeCache cache;
	private static boolean cacheEnabled = false;
	public static boolean debug = false;
	private static int degree, kLength;
	private static File outputFile, debugFile;

	public static void main(String[] args)
	{
		try
		{
			//checking if there are the right amount of args
			if(args.length < 4 || args.length > 6)
			{
				printArgs();
				return;
			}
			
			//enable/disable cache argument
			if(Integer.parseInt(args[0]) == 0)
			{
				cacheEnabled = false;
				
			}

			else if(Integer.parseInt(args[0]) == 1)
			{
				cacheEnabled = true;
				//cache = new BTreeCache(0);
			}

			else
			{
				System.out.println("You need to input a 1 to enable cache and 0 to disable cache.");
				printArgs();
				return;
			}

			//checks the optional arguments
			if(args.length == 5)
			{
				if(cacheEnabled)
				{
					//cache = new BTreeCache(Integer.parseInt(args[4]));
				}
				
				//if cache is disabled, use 5th arg as debug level
				else
				{
					if(Integer.parseInt(args[4]) == 0)
					{
						debug = false;
					}
					
					else if(Integer.parseInt(args[4]) == 1)
					{
						debug = true;
					}
					
					else
					{
						System.out.println("You need to input a 1 to enable debugging and 0 to disable debugging.");
						printArgs();
						return;
					}
				}
			}
			
			else if(args.length == 6)
			{
				//if cache is enabled, use 5th arg for cache level. if not, ignore cache level arg
				if(cacheEnabled)
				{
					//cache = new BTreeCache(Integer.parseInt(args[4]));
				}
				
				if(Integer.parseInt(args[5]) == 0)
				{
					debug = false;
				}
				
				else if(Integer.parseInt(args[5]) == 1)
				{
					debug = true;
				}
				
				else
				{
					System.out.println("You need to input a 1 to enable debugging and 0 to disable debugging.");
					printArgs();
					return;
				}
			}
			
			//degree argument
			if(Integer.parseInt(args[1]) == 0)
			{
				//disk block >= metadata size + (2t-1)size of obj + (2t-1)pointer size
				//4096 >= 13 + (2t-1)12 + (2t-1)4
				//4096 >= 13 + 24t - 12 + 8t - 4
				//4096 >= 32t - 3
				//4099 >= 32t
				//128 >= t
				
				degree = 128;
			}
			
			//gbk file argument
			else
			{
				degree = Integer.parseInt(args[1]);
			}
			
			File gbkFile = new File(args[2]);

			//sequence length argument
			if(Integer.parseInt(args[3]) >= 1 || Integer.parseInt(args[3]) <= 31)
			{
				kLength = Integer.parseInt(args[3]);
			}
			
			else
			{
				System.out.println("The sequence length must be between 1 and 31 (inclusive).");
				return;
			}
			
			//start creating the file (also debug if debug is enabled)
			outputFile = new File(String.format("%s.btree.data.%s.%s", args[2], kLength, degree));
			
			if(debug)
			{
				debugFile = new File(String.format("%s.btree.dump.%s", args[2], kLength));
			}
			
			System.out.println("Generating BTree. Please wait!");
			System.out.println(String.format("Cache: %s | Debug: %s" ,
					cacheEnabled ? "TRUE" : "FALSE", debug ? "TRUE" : "FALSE"));
			
			long start = System.currentTimeMillis();
			tree = new BTree(degree);
			Scanner scanner = new Scanner(gbkFile);
			String line, sequence = "";
			boolean startFound = false, endFound = false;
			
			while(scanner.hasNext())
			{
				line = scanner.nextLine();
				if(line.equals("//"))
				{
					startFound = false;
					endFound = true;
				}

				else if(line.substring(0,6).equals("ORIGIN") && !startFound && !endFound)
				{
					startFound = true;
					endFound = false;
				}
				
				
				if(startFound && !endFound)
				{
					for(int i = 0; i < line.length(); i++)
					{
						char token = line.charAt(i);
						
						if(token == 'n' || token == 'N')
						{
							sequence = "";
						}
						
						else if(token == 'a' || token == 't' || token == 'c' || token == 'g' ||
								token == 'a' || token == 'T' || token == 'C' || token == 'G')
						{
							sequence += Character.toLowerCase(token);
						}
						
						if(sequence.length() == kLength)
						{
							long longObj = SequenceUtils.DNAStringToLong(sequence);
							tree.BTree_Insert(longObj);
							
							sequence = "";
						}
					}
				}
			}
			
			scanner.close();
			
			int timeElapsed = (int) ((System.currentTimeMillis() - start) / 1000);
			int seconds = timeElapsed % 60;
			int minutes = timeElapsed / 60;
			System.out.println(String.format("Finished generating BTree. Time Taken: %dm%ds", minutes, seconds));
			
			if(debug)
			{
				//Traverse the tree
			}
		} 

		catch(NumberFormatException e)
		{
			printArgs();
		}
		
		catch(FileNotFoundException e)
		{
			System.out.println("That .gbk file does not exist.");
			e.printStackTrace();
		}

	}

	/**
	 * Method used to print usage whenever an invalid argument or a caught exception occurs.
	 */
	private static void printArgs()
	{
		System.out.println("Usage: java GeneBankCreateBTree <disable/enable cache (0/1)> <degree> <gbk file> <sequence length> [<cache size>] [<disable/enable debug (0/1)>]");
		System.out.println("[] = optional");
	}
}