package edu.handong.csee.java.examples;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Runner {
	
	String path;
	boolean fullpath;
	boolean verbose;
	boolean help;

	public static void main(String[] args) {

		Runner myRunner = new Runner();
		myRunner.run(args);

	}

	private void run(String[] args) {
		Options options = createOptions();
		
		if(parseOptions(options, args)){
			if (help){
				printHelp(options);
				return;
			}
			
			// path is required (necessary) data so no need to have a branch.
			System.out.println("You provided \"" + path + "\" as the value of the option p");
			
			
			// TODO show the number of files in the path
			File file = new File(path);
			File parentFile = new File(file.getAbsoluteFile().getParent());
			System.out.println(file.getAbsolutePath());
			System.out.println(file.getName());
			
			System.out.println(parentFile.listFiles().length);
			
			if(verbose) {
				
				// TODO list all files in the path
				File[] files = parentFile.listFiles();
				if(fullpath) {
					for(File k : files) {
						System.out.println(k);
					}
					System.out.println("Your program is terminated. (This message is shown because you turned on -v option!");
				}
				else {
					for(File k : files) {
						System.out.println(k.getName());
					}
				}
			}
		}
	}


	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {

			CommandLine cmd = parser.parse(options, args);

			path = cmd.getOptionValue("p");  // p 뒤의 argument 가져오기
			fullpath = cmd.hasOption("f");
			verbose = cmd.hasOption("v"); //boolean 있으면 true
			help = cmd.hasOption("h"); //boolean
			

		} catch (Exception e) {
			printHelp(options);
			return false;
		}

		return true;
	}

	// Definition Stage
	private Options createOptions() {
		Options options = new Options();

		// add options by using OptionBuilder
		options.addOption(Option.builder("p").longOpt("path")
				.desc("Set a path of a directory or a file to display") //설명
				.hasArg() //이 옵션은 arg가 필요
				.argName("Path name to display")
				.required() //필수 요소& 없으면 error
				.build());
		// add options by using OptionBuilder
		options.addOption(Option.builder("f").longOpt("fullpath")
				.desc("Display fullpath of all files")
				.argName("FullPath name of files in directory to display")
				.build());

		// add options by using OptionBuilder
		options.addOption(Option.builder("v").longOpt("verbose")
				.desc("Display detailed messages!")
				//.hasArg()     // this option is intended not to have an option value but just an option
				.argName("verbose option")
				//.required() // this is an optional option. So disabled required().
				.build());
		
		// add options by using OptionBuilder
		options.addOption(Option.builder("h").longOpt("help")
		        .desc("Help")
		        .build());
		

				
		
		
		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "CLI test program";
		String footer ="\nPlease report issues at https://github.com/lifove/CLIExample/issues";
		formatter.printHelp("CLIExample", header, options, footer, true);
	}

}
